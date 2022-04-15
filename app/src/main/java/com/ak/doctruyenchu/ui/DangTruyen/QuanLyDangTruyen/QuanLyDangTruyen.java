package com.ak.doctruyenchu.ui.DangTruyen.QuanLyDangTruyen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.ak.doctruyenchu.Constans.Constans;
import com.ak.doctruyenchu.R;
import com.ak.doctruyenchu.databinding.ActivityQuanLyDangTruyenBinding;
import com.ak.doctruyenchu.models.TRUYEN;
import com.ak.doctruyenchu.ui.DangTruyen.QuanLyDangTruyen.adapter.QLDTadapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class QuanLyDangTruyen extends AppCompatActivity {


    private QLDTadapter qldTadapter;
    private RecyclerView recyclerView;
    private ArrayList<TRUYEN> truyenPublic;
    private ArrayList<TRUYEN> truyenDraw;
    private ActivityQuanLyDangTruyenBinding binding;
    private boolean fabClick=false;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuanLyDangTruyenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        truyenDraw = new ArrayList<>();
        qldTadapter = new QLDTadapter(truyenDraw);

        recyclerView = binding.rcvQlt;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        binding.fabChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!fabClick){
                    if (!binding.fabChange.isExtended()){
                        Toast.makeText(QuanLyDangTruyen.this, "a", Toast.LENGTH_SHORT).show();
                        binding.fabChange.extend();
                    }
                    fabClick = true;
                }else {
                    if (binding.fabChange.isExtended()) {
                        binding.fabChange.shrink();
                        Toast.makeText(QuanLyDangTruyen.this, "b", Toast.LENGTH_SHORT).show();
                    }
                    fabClick = false;
                }
            }
        });

        binding.btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        run();
    }

    private void run() {
        recyclerView.setAdapter(qldTadapter);
        Constans.DATABASE.getReference("NHAP").child("TRUYEN_NHAP").child(Constans.AUTH.getCurrentUser().getUid())
                .get()
                .addOnCompleteListener( new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()){
                            DataSnapshot data = task.getResult();
                            for (DataSnapshot x : data.getChildren()){
                                truyenDraw.add(x.getValue(TRUYEN.class));
                            }
                        }
                        qldTadapter.notifyDataSetChanged();
                    }
                });
    }
}