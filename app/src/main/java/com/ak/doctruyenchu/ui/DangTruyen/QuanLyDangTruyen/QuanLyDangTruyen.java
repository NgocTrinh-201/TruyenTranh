package com.ak.doctruyenchu.ui.DangTruyen.QuanLyDangTruyen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ak.doctruyenchu.R;
import com.ak.doctruyenchu.databinding.ActivityQuanLyDangTruyenBinding;

public class QuanLyDangTruyen extends AppCompatActivity {


    private ActivityQuanLyDangTruyenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuanLyDangTruyenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }
}