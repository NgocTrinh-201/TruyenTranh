package com.ak.doctruyenchu.ui.ThongTinTruyen;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ak.doctruyenchu.Constans.Comco;
import com.ak.doctruyenchu.Constans.Constans;
import com.ak.doctruyenchu.R;
import com.ak.doctruyenchu.models.BinhLuan;
import com.ak.doctruyenchu.models.DanhGia;
import com.ak.doctruyenchu.models.USER;
import com.ak.doctruyenchu.ui.ThongTinTruyen.adapter.BinhLuanAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_tool_comment_rate#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_tool_comment_rate extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View view;
    private EditText editText;
    private TextView button, sentRate;
    private String ten_truyen;
    private RecyclerView recyclerView;
    private BinhLuanAdapter adapter;
    private ArrayList<BinhLuan> binhLuanArrayList;
    private CountDownTimer countDownTimer;
    private RatingBar ratingBar;
    private boolean rated;

    public Fragment_tool_comment_rate() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_tool_comment_rate.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_tool_comment_rate newInstance(String param1, String param2) {
        Fragment_tool_comment_rate fragment = new Fragment_tool_comment_rate();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        ten_truyen = getArguments().getString("ten_truyen");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tool_comment_rate, container, false);

        editText = view.findViewById(R.id.edit_comment);
        sentRate = view.findViewById(R.id.bt_sent_rate);
        button = view.findViewById(R.id.bt_gui_comment);
        ratingBar = view.findViewById(R.id.ratingBar_com_rate);

        run();

        return view;
    }

    private void run(){
        if (Constans.AUTH.getCurrentUser()!=null){
            sendComment();
            Constans.DATABASE.getReference(Constans.DANH_GIA).child(ten_truyen).child(Constans.AUTH.getCurrentUser().getUid())
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                        @Override
                        public void onSuccess(DataSnapshot dataSnapshot) {
                            DanhGia danhGia = dataSnapshot.getValue(DanhGia.class);
                            if (danhGia!=null){
                                rated = true;
                                ratingBar.setRating(danhGia.getRate());
                                sentRate.setClickable(false);
                                sentRate.setText("Đã đánh giá");
                                sentRate.setBackground(getContext().getDrawable(R.color.gray_600));
                            }
                        }
                    });

            sentRate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    float rate = ratingBar.getRating();
                    if (rated){
                        Comco.capNhatRate(ten_truyen,rate);
                    }else {
                        Comco.capNhatRateVaLuotRate(ten_truyen,rate);
                        rated = true;
                    }
                    Constans.DATABASE.getReference(Constans.DANH_GIA).child(ten_truyen).child(Constans.AUTH.getCurrentUser().getUid())
                            .setValue(new DanhGia(rate));
                    sentRate.setText("Đã gửi");
                    sentRate.setClickable(false);
                    sentRate.setBackground(getContext().getDrawable(R.color.gray_600));
                }
            });

            ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    sentRate.setText("Đánh giá lại");
                    sentRate.setClickable(true);
                    sentRate.setBackground(getContext().getDrawable(R.color.teal_200));
                }
            });
        }
        else {

        }
    }

    private void sendComment(){

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                button.setClickable(false);
                button.setBackground(getContext().getDrawable(R.color.gray_600));

                String x = editText.getText().toString().trim();
                if (x!=null || !x.equals("") || !x.isEmpty()){
                    Constans.DATABASE.getReference(Constans.USER).child(Constans.AUTH.getCurrentUser().getUid())
                            .get()
                            .addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                                @Override
                                public void onSuccess(DataSnapshot dataSnapshot) {
                                    USER user = dataSnapshot.getValue(USER.class);
                                    if (user!=null){
                                        Constans.DATABASE.getReference(Constans.BINH_LUAN).child(ten_truyen)
                                                .push()
                                                .setValue(new BinhLuan(user.getTen_nguoi_dung(),user.getUid(),user.getUrl_anh_dai_dien(),x));
                                        editText.setText("");
                                    }
                                }
                            });
                }

                countDownTimer = new CountDownTimer(60000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        Log.d("Cooldown buttun sent comment", "seccons: "+millisUntilFinished/1000);
                        button.setText("Đợi ("+millisUntilFinished/1000+")");
                    }
                    @Override
                    public void onFinish() {
                        button.setText(getString(R.string.sent_a_comment));
                        button.setBackground(getContext().getDrawable(R.color.teal_200));
                        button.setClickable(true);
                    }
                }.start();
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        if (countDownTimer!=null){
            countDownTimer.cancel();
        }
        getParentFragmentManager().beginTransaction().remove(this).commit();
    }

}