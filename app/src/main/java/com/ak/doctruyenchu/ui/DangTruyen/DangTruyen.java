package com.ak.doctruyenchu.ui.DangTruyen;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.ak.doctruyenchu.Constans.Constans;
import com.ak.doctruyenchu.R;
import com.ak.doctruyenchu.databinding.ActivityDangTruyenBinding;
import com.ak.doctruyenchu.models.TRUYEN;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;


public class DangTruyen extends AppCompatActivity {

    private ImageView anh_bia;
    private ImageButton bt_back;
    private TextView bt_them;
    private TextInputEditText ten_truyen, mo_ta, tac_gia;
    private Uri uri;
    private LinearLayout prog;
    private byte[] imagedata;
    private ActivityDangTruyenBinding binding;
    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDangTruyenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        anh_bia=binding.imgBiaTruyen;
        bt_back=binding.btBack;
        bt_them=binding.btThem;
        prog = binding.layoutProg;
        ten_truyen=binding.themTruyenTenTruyen;
        tac_gia = binding.themTruyenTenTacGia;
        mo_ta=binding.themTruyenMoTa;
        uri= Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.book_background_default);
        anh_bia.setDrawingCacheEnabled(true);
        anh_bia.buildDrawingCache();
        //
        System.out.println(getIntent().getStringExtra("path"));
        //
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
        if (getIntent().getStringExtra("path")==null){
            bt_them.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String tenTruyen = ten_truyen.getText().toString().replace(".","。").trim();
                    String moTa = mo_ta.getText().toString().trim();
                    String tacGia = tac_gia.getText().toString().trim();
                    if (checkError()){
                        Toast.makeText(DangTruyen.this, "Chưa đủ thông tin hoặc tên truyện có chứa dấu chấm (.).", Toast.LENGTH_SHORT).show();
                    }else {
                        clickable(false);
                        prog.setVisibility(View.VISIBLE);
                        TRUYEN truyen = new TRUYEN();
                        Constans.STORAGE.getReference(Constans.PICTURE).child(Constans.ANH_BIA_TRUYEN).child(Constans.AUTH.getCurrentUser().getUid())
                                .child(tenTruyen)
                                .putFile(uri)
                                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        Constans.STORAGE.getReference(Constans.PICTURE).child(Constans.ANH_BIA_TRUYEN).child(Constans.AUTH.getCurrentUser().getUid())
                                                .child(tenTruyen)
                                                .getDownloadUrl()
                                                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri uri) {
                                                        truyen.themTruyen(tenTruyen,moTa,uri.toString());
                                                        truyen.setTac_gia(tacGia);
                                                        Constans.DATABASE.getReference(Constans.QUAN_LY_DANG_TRUYEN).child(Constans.AUTH.getCurrentUser().getUid()).child(Constans.CHUA_PUBLIC)
                                                                .child(Constans.TRUYEN)
                                                                .child(tenTruyen).setValue(truyen)
                                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(Void unused) {
                                                                        Toast.makeText(DangTruyen.this, "Them thanh cong", Toast.LENGTH_SHORT).show();
                                                                        finish();
                                                                    }
                                                                });
                                                    }
                                                });
                                    }
                                });


                    }
                }
            });
        }else {
            binding.btThem.setText("Lưu");
            binding.textView.setText("Sửa thông tin truyện");
            Constans.DATABASE.getReference(getIntent().getStringExtra("path"))
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                        @Override
                        public void onSuccess(DataSnapshot dataSnapshot) {
                            TRUYEN truyen = dataSnapshot.getValue(TRUYEN.class);
                            if (truyen!=null){
                                ten_truyen.setText(truyen.getTen_truyen());
                                mo_ta.setText(truyen.getMo_ta());
                                a(truyen);
                            }
                        }
                    });
        }
    }

    private void a(TRUYEN truyen){
       uri = Uri.parse(truyen.getUrl_anh_nen_truyen());
        Picasso.get()
                .load(uri)
                .into(anh_bia);
        ten_truyen.setText(truyen.getTen_truyen());
        mo_ta.setText(truyen.getMo_ta());
//        getActionBar().setTitle("Chỉnh sủa thông tin");
        bt_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bitmap bitmap = ((BitmapDrawable) anh_bia.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                imagedata = baos.toByteArray();

                String tenTruyen = ten_truyen.getText().toString().replace(".","。").trim();
                String moTa = mo_ta.getText().toString().trim();
                String tacGia = tac_gia.getText().toString().trim();
                if (checkError()){
                    Toast.makeText(DangTruyen.this, "Chua du thong tin", Toast.LENGTH_SHORT).show();
                }else {
                    clickable(false);
                    prog.setVisibility(View.VISIBLE);

                    if (!tenTruyen.equals(truyen.getTen_truyen())){
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Constans.STORAGE.getReference(Constans.PICTURE).child(Constans.ANH_BIA_TRUYEN).child(Constans.AUTH.getCurrentUser().getUid())
                                        .child(truyen.getTen_truyen()).delete();
                                Constans.DATABASE.getReference(Constans.QUAN_LY_DANG_TRUYEN).child(Constans.AUTH.getCurrentUser().getUid()).child(Constans.CHUA_PUBLIC)
                                        .child(Constans.TRUYEN)
                                        .child(truyen.getTen_truyen()).removeValue();

                            }
                        }).start(); // xoa truyen cu
                    }

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                                Constans.STORAGE.getReference(Constans.PICTURE).child(Constans.ANH_BIA_TRUYEN).child(Constans.AUTH.getCurrentUser().getUid())
                                        .child(tenTruyen)
                                        .putBytes(imagedata)
                                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                b(tenTruyen,moTa,tacGia,truyen);
                                            }
                                        });


                        }
                    }).start();


                }
            }
        });
    }

    private void b(String tenTruyen1, String moTa1,String tacGia1, TRUYEN truyen1){
        Constans.STORAGE.getReference(Constans.PICTURE).child(Constans.ANH_BIA_TRUYEN).child(Constans.AUTH.getCurrentUser().getUid())
                .child(tenTruyen1)
                .getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        TRUYEN x = truyen1;
                        x.setTen_truyen(tenTruyen1);
                        x.setMo_ta(moTa1);
                        x.setTac_gia(tacGia1);
                        x.setUrl_anh_nen_truyen(uri.toString());

                        Constans.DATABASE.getReference(Constans.QUAN_LY_DANG_TRUYEN).child(Constans.AUTH.getCurrentUser().getUid()).child(Constans.CHUA_PUBLIC)
                                .child(Constans.TRUYEN)
                                .child(tenTruyen1).setValue(x)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(DangTruyen.this, "Cap nhat thanh cong", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                });
                    }
                });
    }

    private void clickable(boolean b){
        anh_bia.setClickable(b);
        ten_truyen.setEnabled(b);
        mo_ta.setEnabled(b);
    }

    private boolean checkError(){
        boolean errorCheck = false;
        if (ten_truyen.getText().toString().trim().isEmpty()||mo_ta.getText().toString().trim().isEmpty() || tac_gia.getText().toString().trim().isEmpty())
        {
            errorCheck =true;
        }
        else errorCheck = false;

        return errorCheck;
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
        }else super.onBackPressed();
    }
}