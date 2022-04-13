package com.ak.doctruyenchu.ui.DangTruyen;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.ak.doctruyenchu.Constans.Constans;
import com.ak.doctruyenchu.R;
import com.ak.doctruyenchu.databinding.ActivityDangTruyenBinding;
import com.ak.doctruyenchu.models.TRUYEN;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;



public class DangTruyen extends AppCompatActivity {

    private ImageView anh_bia;
    private ImageButton bt_back;
    private CardView bt_them;
    private TextInputEditText ten_truyen, mo_ta;
    private Uri uri;

    private ActivityDangTruyenBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDangTruyenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        anh_bia=binding.imgBiaTruyen;
        bt_back=binding.btBack;
        bt_them=binding.btThem;
        ten_truyen=binding.themTruyenTenTruyen;
        mo_ta=binding.themTruyenMoTa;
        uri= Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.book_background_default);

        event();

    }

    private void event() {
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
            }
        });
        anh_bia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityIfNeeded(Intent.createChooser(intent,getString(R.string.select_image)), Constans.SELECT_IMAGE);
            }
        });
        bt_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenTruyen = ten_truyen.getText().toString().trim();
                String moTa = mo_ta.getText().toString().trim();
                if (tenTruyen.isEmpty()||moTa.isEmpty()){
                    Toast.makeText(DangTruyen.this, "Chua du thong tin", Toast.LENGTH_SHORT).show();
                }else {
                    TRUYEN truyen = new TRUYEN();
                    truyen.themTruyen(tenTruyen,moTa,"none");
                    Constans.DATABASE.getReference("NHAP").child("TRUYEN_NHAP").child(Constans.AUTH.getCurrentUser().getUid())
                            .child(tenTruyen).setValue(truyen);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Constans.SELECT_IMAGE && resultCode == RESULT_OK && data!=null){
            uri = data.getData();
            Picasso.get().load(uri)
                    .into(anh_bia);
        }
    }

    @Override
    public void onBackPressed() {
        Log.e("error","pressed");
        if (!ten_truyen.getText().toString().isEmpty() || !mo_ta.getText().toString().isEmpty()){
            AlertDialog.Builder builder = new AlertDialog.Builder(DangTruyen.this);
            builder.setTitle("Thông báo !");
            builder.setMessage("Bạn có chắc muốn thoát ?");
            builder.setNeutralButton("Hủy", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }
}