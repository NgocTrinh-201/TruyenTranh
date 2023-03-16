package com.ak.doctruyenchu.ui.DangTruyen.QuanLyDangTruyen.QuanLyChuong;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ak.doctruyenchu.Constans.Constans;
import com.ak.doctruyenchu.databinding.ActivityThemSuaChuongBinding;
import com.ak.doctruyenchu.models.CHUONG;
import com.ak.doctruyenchu.models.TRUYEN;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.squareup.picasso.Picasso;

public class Them_Sua_Chuong extends AppCompatActivity {

    private ActivityThemSuaChuongBinding binding;
    private TRUYEN truyen;
    private int soChuong=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityThemSuaChuongBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        run();
        event();

    }

    private void event() {
        binding.btThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkError()){
                    binding.tenChuong.setEnabled(false);
                    binding.noiDung.setEnabled(false);
                    soChuong ++;
                    truyen.setChap_moi_nhat(soChuong+"。 "+binding.tenChuong.getText().toString().trim());
                    Constans.DATABASE.getReference(Constans.TRUYEN).child(truyen.getTen_truyen()).setValue(truyen)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    CHUONG chuong = new CHUONG(soChuong+"。 "+binding.tenChuong.getText().toString().replace(".","。").trim(),binding.noiDung.getText().toString().trim());
                                    Constans.DATABASE.getReference(Constans.CHUONG).child(truyen.getTen_truyen()).child(chuong.getTen_chuoong()).setValue(chuong);
                                    if (getIntent().getStringExtra("path")!=null)
                                    {
                                        Constans.DATABASE.getReference(Constans.QUAN_LY_DANG_TRUYEN).child(Constans.AUTH.getCurrentUser().getUid()).child(Constans.DA_PUBLIC).push().setValue(truyen.getTen_truyen());
                                        Constans.DATABASE.getReference(getIntent().getStringExtra("path")).removeValue();
                                    }

                                    Toast.makeText(Them_Sua_Chuong.this, "Thành công !", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });
                }
            }
        });


    }

    private boolean checkError() {
        boolean error = false;
        if (binding.tenChuong.getText().toString().trim().isEmpty()||binding.tenChuong.getText().toString().trim()==null){
            error = true;
            binding.tenChuong.setError("Không được để trống");
        }else error = false;
        if (binding.noiDung.getText().toString().trim().isEmpty()||binding.noiDung.getText().toString().trim()==null){
            error = true;
            binding.tenChuong.setError("Không được để trống");
        }else error = false;
        return error;
    }

    private void run() {
        if (getIntent().getStringExtra("path")!=null){
            soChuong = 0;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Thông báo !");
            builder.setMessage("Phải thêm 1 chương để public ");
            builder.setNeutralButton("Hủy", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();

            Constans.DATABASE.getReference(getIntent().getStringExtra("path"))
                    .get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                @Override
                public void onSuccess(DataSnapshot dataSnapshot) {
                    truyen = dataSnapshot.getValue(TRUYEN.class);
                    if (truyen!=null){
                        binding.tenTruyen.setText(truyen.getTen_truyen());
                        binding.chuongMoiNhat.setText(truyen.getChap_moi_nhat());
                        Picasso.get().load(truyen.getUrl_anh_nen_truyen()).into(binding.imgBiaTruyen);
                    }
                }
            });
        }
        else {
            Constans.DATABASE.getReference(Constans.TRUYEN).child(getIntent().getStringExtra("ten_truyen"))
                    .get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                @Override
                public void onSuccess(DataSnapshot dataSnapshot) {
                    truyen = dataSnapshot.getValue(TRUYEN.class);
                    if (truyen!=null){
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Constans.DATABASE.getReference(Constans.CHUONG).child(truyen.getTen_truyen())
                                        .addChildEventListener(new ChildEventListener() {
                                            @Override
                                            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                                soChuong++;
                                                Log.e("So chung",""+soChuong);
                                            }

                                            @Override
                                            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                                            }

                                            @Override
                                            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                                            }

                                            @Override
                                            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                            }
                        }).start();
                        binding.tenTruyen.setText(truyen.getTen_truyen());
                        binding.chuongMoiNhat.setText(truyen.getChap_moi_nhat());
                        Picasso.get().load(truyen.getUrl_anh_nen_truyen()).into(binding.imgBiaTruyen);
                    }
                }
            });
        }
    }
}