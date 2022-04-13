package com.ak.doctruyenchu.ui.CaNhan.ChinhSuaThongTin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.ak.doctruyenchu.Constans.Constans;
import com.ak.doctruyenchu.R;
import com.ak.doctruyenchu.databinding.ActivityChinhSuaThongTinBinding;
import com.ak.doctruyenchu.models.USER;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class ChinhSuaThongTin extends AppCompatActivity {

    private ActivityChinhSuaThongTinBinding binding;
    private boolean checkError = true;
    private Uri uri = null;
    private final String uid = Constans.AUTH.getCurrentUser().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChinhSuaThongTinBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        unitUI();
        event();

    }

    private void unitUI() {

        Constans.DATABASE.getReference("USER").child(Constans.AUTH.getCurrentUser().getUid())
                .get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    USER user = task.getResult().getValue(USER.class);
                    Picasso.get()
                            .load(user.getUrl_anh_dai_dien())
                            .error(R.drawable.ic_baseline_person_outline_24)
                            .into(binding.imgAnhChinhSua);
                    binding.tvEditLoaiTaiKhoan.setText(user.getLoai_tai_khoan());
                    binding.tvEditUrlAnhDaiDien.setText(user.getUrl_anh_dai_dien());
                    binding.editTenNguoiDung.setText(user.getTen_nguoi_dung());
                    binding.editEmail.setText(user.getEmail());
                }
            }
        });

    }

    private void event() {
        //kiem tra loi
        binding.editEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString()==null || s.toString().equals("")){
                    binding.editEmail.setError(getString(R.string.is_blank));
                    checkError=true;
                }else checkError=false;
                if (!s.toString().matches("(.*)@(.*).(.*)") ){
                    binding.editEmail.setError(getString(R.string.not_correct_format));
                    checkError=true;
                }
                else checkError=false;
            }
        });

        binding.editTenNguoiDung.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString()==null || s.toString().equals("")){
                    binding.editTenNguoiDung.setError(getString(R.string.is_blank));
                    checkError=true;
                }else checkError=false;
                if (s.toString().length()<8){
                    binding.editTenNguoiDung.setError(getString(R.string.string_lenght_is_less_than_8));
                    checkError=true;
                }
                else checkError=false;
            }
        });

        binding.imgAnhChinhSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityIfNeeded(Intent.createChooser(intent,getString(R.string.select_image)),Constans.SELECT_IMAGE);
            }
        });

        binding.btLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uri == null){
//                    Constans.DATABASE.getReference("USER").child(uid).child("url_anh_dai_dien")
//                            .get()
//                            .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//                                @Override
//                                public void onComplete(@NonNull Task<DataSnapshot> task) {
//                                    if (task.isSuccessful()){
//                                        String url = task.getResult().getValue(String.class);
//                                        Constans.DATABASE.getReference("USER").child(uid)
//                                                .setValue(new USER(uid,binding.editTenNguoiDung.getText().toString().trim(),url,binding.editEmail.getText().toString().trim()))
//                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                    @Override ////Cap nhat thong tin user tren database thanh cong
//                                                    public void onSuccess(Void unused) {
//                                                        endProgressBar();
//                                                        Toast.makeText(ChinhSuaThongTin.this, getString(R.string.update_success), Toast.LENGTH_SHORT).show();
//                                                    }
//                                                })
//                                                .addOnFailureListener(new OnFailureListener() {
//                                                    @Override ////Cap nhat thong tin user tren database that bai
//                                                    public void onFailure(@NonNull Exception e) {
//                                                        endProgressBar();
//                                                        Toast.makeText(ChinhSuaThongTin.this, getString(R.string.update_failure) + "\n" +e.getMessage(), Toast.LENGTH_SHORT).show();
//                                                    }
//                                                });
//                                    }
//                                }
//                            });
                    Constans.DATABASE.getReference("USER").child(uid)
                            .setValue(new USER(uid,binding.editTenNguoiDung.getText().toString().trim(),binding.tvEditUrlAnhDaiDien.getText().toString(),binding.editEmail.getText().toString().trim(),binding.tvEditLoaiTaiKhoan.getText().toString()))
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override ////Cap nhat thong tin user tren database thanh cong
                                public void onSuccess(Void unused) {
                                    endProgressBar();
                                    Toast.makeText(ChinhSuaThongTin.this, getString(R.string.update_success), Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override ////Cap nhat thong tin user tren database that bai
                                public void onFailure(@NonNull Exception e) {
                                    endProgressBar();
                                    Toast.makeText(ChinhSuaThongTin.this, getString(R.string.update_failure) + "\n" +e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }else {
                    startProgressBar();
                    capNhatThongTin();
                }
            }
        });

        binding.btHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void startProgressBar(){
        binding.editTenNguoiDung.setEnabled(false);
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.btLuu.setClickable(false);
        binding.btLuu.setBackgroundColor(getColor(R.color.gray_400));
        binding.btHuy.setClickable(false);
        binding.btHuy.setBackgroundColor(getColor(R.color.gray_400));
    }

    private void endProgressBar(){
        binding.progressBar.setVisibility(View.INVISIBLE);
        binding.btLuu.setClickable(true);
        binding.btLuu.setBackgroundColor(getColor(R.color.teal_200));
        binding.btHuy.setClickable(true);
        binding.btHuy.setText(getString(R.string.exit));
        binding.btHuy.setBackgroundColor(getColor(R.color.gray_600));
    }

    private void capNhatThongTin() {
        //luu len Storage
        Constans.STORAGE.getReference("USER").child(uid).child("AVATAR").child("avatar_img")
                .putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override //luu len Storage thanh cong
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        //Cap nhat thong tin user tren database
                        Constans.STORAGE.getReference("USER").child(uid).child("AVATAR").child("avatar_img")
                                .getDownloadUrl()
                                .addOnCompleteListener(new OnCompleteListener<Uri>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Uri> task) {
                                        if (task.isSuccessful()){
                                            String url = task.getResult().toString();
                                            Constans.DATABASE.getReference("USER").child(uid)
                                                    .setValue(new USER(uid,binding.editTenNguoiDung.getText().toString().trim(),url,binding.editEmail.getText().toString().trim(),binding.tvEditLoaiTaiKhoan.getText().toString()))
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override ////Cap nhat thong tin user tren database thanh cong
                                                        public void onSuccess(Void unused) {
                                                            endProgressBar();
                                                            Toast.makeText(ChinhSuaThongTin.this, getString(R.string.update_success), Toast.LENGTH_SHORT).show();
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override ////Cap nhat thong tin user tren database that bai
                                                        public void onFailure(@NonNull Exception e) {
                                                            endProgressBar();
                                                            Toast.makeText(ChinhSuaThongTin.this, getString(R.string.update_failure) + "\n" +e.getMessage(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                        }
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override //luu len Storage that bai
                    public void onFailure(@NonNull Exception e) {
                        endProgressBar();
                        Toast.makeText(ChinhSuaThongTin.this, getString(R.string.update_failure) + "\n" +e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Constans.SELECT_IMAGE && resultCode == RESULT_OK && data!=null){
            uri = data.getData();
            Picasso.get().load(uri)
                    .into(binding.imgAnhChinhSua);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}