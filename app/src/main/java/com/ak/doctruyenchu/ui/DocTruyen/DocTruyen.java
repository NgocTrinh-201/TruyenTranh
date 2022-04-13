package com.ak.doctruyenchu.ui.DocTruyen;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.ak.doctruyenchu.Constans.Constans;
import com.ak.doctruyenchu.databinding.ActivityDocTruyenBinding;
import com.ak.doctruyenchu.models.CHUONG;
import com.ak.doctruyenchu.models.LICHSU;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.concurrent.Executor;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class DocTruyen extends AppCompatActivity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */

    private String ten_truyen;
    private String ten_chuong;
    private  int i = 0;

    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar
            if (Build.VERSION.SDK_INT >= 30) {
                mContentView.getWindowInsetsController().hide(
                        WindowInsets.Type.statusBars() | WindowInsets.Type.navigationBars());
            } else {
                // Note that some of these constants are new as of API 16 (Jelly Bean)
                // and API 19 (KitKat). It is safe to use them, as they are inlined
                // at compile-time and do nothing on earlier devices.
                mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
            }
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (AUTO_HIDE) {
                        delayedHide(AUTO_HIDE_DELAY_MILLIS);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    view.performClick();
                    break;
                default:
                    break;
            }
            return false;
        }
    };
    private ActivityDocTruyenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDocTruyenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        ten_truyen = intent.getStringExtra("ten_truyen");
        ten_chuong = intent.getStringExtra("ten_chuong");
        capNhatLichSu(ten_truyen,ten_chuong);
        mVisible = true;
        mControlsView = binding.fullscreenContentControls;
        mContentView = binding.tvNoiDungDocTruyen;
//
        binding.tvTenTruyenDocTruyen.setText(ten_truyen);
        binding.tvTenChuongDocTruyen.setText(ten_chuong);

        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
//        binding.dummyButton.setOnTouchListener(mDelayHideTouchListener);

        //
        run();

    }

    private void run(){
        Constans.DATABASE.getReference("CHUONG").child(ten_truyen).get()
                .addOnCompleteListener( new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        ArrayList<CHUONG> chuong = new ArrayList<>();
                        if (task.isSuccessful()) {
                            for (DataSnapshot data : task.getResult().getChildren())
                                chuong.add(data.getValue(CHUONG.class));
                        }

                        for(i =0; i <chuong.size(); i++){
                            if (chuong.get(i).getTen_chuoong().equals(ten_chuong)){
                                break;
                            }
                        }
                        setNoiDung(i,chuong);
                        binding.btChapSau.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                i+=1;
                                setNoiDung(i,chuong);
                                capNhatLichSu(ten_truyen,chuong.get(i).getTen_chuoong());
                            }
                        });
                        binding.btChapTruoc.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                i-=1;
                                setNoiDung(i,chuong);
                                capNhatLichSu(ten_truyen,chuong.get(i).getTen_chuoong());
                            }
                        });

                    }
                });
    }

    private void setNoiDung(int i, ArrayList<CHUONG> chuongs){
        if (i==0)
            binding.btChapTruoc.setVisibility(View.INVISIBLE);
        else binding.btChapTruoc.setVisibility(View.VISIBLE);
        if (i==chuongs.size()-1)
            binding.btChapSau.setVisibility(View.INVISIBLE);
        else binding.btChapSau.setVisibility(View.VISIBLE);
        binding.tvNoiDungDocTruyen.setText(chuongs.get(i).getNoi_dung());
        binding.tvTenChuongDocTruyen.setText(chuongs.get(i).getTen_chuoong());
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    private void show() {
        // Show the system bar
        if (Build.VERSION.SDK_INT >= 30) {
            mContentView.getWindowInsetsController().show(
                    WindowInsets.Type.statusBars());
        } else {
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        }
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in delay milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    private void capNhatLichSu(String tenTruyen, String tenChuong){
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (Constans.AUTH.getCurrentUser()!=null){
                    Constans.DATABASE.getReference(Constans.TU_TRUYEN).child(Constans.AUTH.getCurrentUser().getUid()).child(tenTruyen)
                            .get()
                            .addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                                @Override
                                public void onSuccess(DataSnapshot dataSnapshot) {
                                    LICHSU tu = dataSnapshot.getValue(LICHSU.class);
                                    if (tu!=null)
                                        Constans.DATABASE.getReference(Constans.TU_TRUYEN).child(Constans.AUTH.getCurrentUser().getUid()).child(tenTruyen).setValue(new LICHSU(tenTruyen,tenChuong));
                                    else Constans.DATABASE.getReference(Constans.LICH_SU).child(Constans.AUTH.getCurrentUser().getUid()).child(tenTruyen).setValue(new LICHSU(tenTruyen,tenChuong));
                                }
                            });
                }
            }
        }).start();
    }
}