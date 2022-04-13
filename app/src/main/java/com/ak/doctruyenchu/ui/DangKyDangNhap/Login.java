package com.ak.doctruyenchu.ui.DangKyDangNhap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.ak.doctruyenchu.Constans.Constans;
import com.ak.doctruyenchu.R;
import com.ak.doctruyenchu.models.USER;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;

public class Login extends AppCompatActivity {

    private TextInputEditText email;
    private TextInputEditText passWord;
    private CardView bt_dangKy, bt_dangNhap;
    private boolean checkError = true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        unitUI();
        event();

    }

    private void unitUI() {
        email = findViewById(R.id.edit_email);
        passWord = findViewById(R.id.eidt_password);
        bt_dangKy = findViewById(R.id.bt_dang_ky);
        bt_dangNhap = findViewById(R.id.bt_dang_nhap);
    }

    private void event() {
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString()==null || s.toString().equals("")){
                    Toast.makeText(Login.this, "Rong", Toast.LENGTH_SHORT).show();
                    email.setError(getString(R.string.is_blank));
                    checkError=true;
                }else checkError=false;
                if (!s.toString().matches("(.*)@(.*).(.*)") ){
                    email.setError(getString(R.string.not_correct_format));
                    checkError=true;
                }
                else checkError=false;

            }
        });

        passWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString()==null || s.toString().equals("")){
                    passWord.setError(getString(R.string.is_blank));
                    checkError=true;
                }else checkError=false;
                if (s.toString().length()<8){
                    passWord.setError(getString(R.string.string_lenght_is_less_than_8));
                    checkError=true;
                }
                else checkError=false;
            }
        });

        bt_dangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkError){
                    Constans.AUTH.signInWithEmailAndPassword(email.getText().toString().trim(),passWord.getText().toString().trim())
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    Toast.makeText(Login.this, getText(R.string.succes_to_login), Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Login.this, getText(R.string.faile_to_login) +"\n"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }else {
                    Toast.makeText(Login.this, getString(R.string.infor_error), Toast.LENGTH_SHORT).show();
                }
            }
        });

        bt_dangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkError){
                    Constans.AUTH.createUserWithEmailAndPassword(email.getText().toString().trim(),passWord.getText().toString().trim())
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    Toast.makeText(Login.this, getText(R.string.signup_success), Toast.LENGTH_SHORT).show();
                                    USER user = new USER(Constans.AUTH.getCurrentUser().getUid(),"no_name","none",email.getText().toString().trim());
                                    Constans.DATABASE.getReference("USER").child(user.getUid()).setValue(user);
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Login.this, getText(R.string.signup_failure) + "\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }else {
                    Toast.makeText(Login.this, getString(R.string.infor_error), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



}