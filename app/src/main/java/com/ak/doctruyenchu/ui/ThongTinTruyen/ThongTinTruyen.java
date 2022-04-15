package com.ak.doctruyenchu.ui.ThongTinTruyen;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavAction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ak.doctruyenchu.Constans.Comco;
import com.ak.doctruyenchu.Constans.Constans;
import com.ak.doctruyenchu.R;
import com.ak.doctruyenchu.models.CHUONG;
import com.ak.doctruyenchu.models.LICHSU;
import com.ak.doctruyenchu.models.TRUYEN;
import com.ak.doctruyenchu.ui.DocTruyen.DocTruyen;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import jp.wasabeef.picasso.transformations.BlurTransformation;

public class ThongTinTruyen extends AppCompatActivity {

    private Intent intent;
    private String tentruyen;
    private ImageView image_ttt;
    private TextView ten_truyen, tac_gia,bt_de_cu_truyen,bt_them_vao_tu;
    private ImageView background;
    private Bundle bundle;
    private CardView bt_doc;
    private AppBarLayout appBarLayout;
    private LinearLayout linearLayout;
    private boolean decu,tutruyen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_truyen);
        intent = getIntent();
        tentruyen = intent.getStringExtra("ten_truyen");

        //Unit UI
        image_ttt = findViewById(R.id.image_ttt);
        ten_truyen = findViewById(R.id.tv_ten_truyen_ttt);
        tac_gia = findViewById(R.id.tv_tac_gia_ttt);
//        mo_ta = findViewById(R.id.tv_mo_ta_ttt);
        background = findViewById(R.id.backgroung_ttt);
        bt_doc = findViewById(R.id.bt_doc_truyen_ttt);
        bt_them_vao_tu = findViewById(R.id.bt_them_vao_tu_truyen_ttt);
        appBarLayout = findViewById(R.id.appbar_ttt);
        bt_them_vao_tu = findViewById(R.id.bt_them_vao_tu_truyen_ttt);
        bt_de_cu_truyen = findViewById(R.id.bt_de_cu_truyen);
        linearLayout = findViewById(R.id.linearLayout_decu_danhdau);



        bundle = new Bundle();
        bundle.putString("ten_truyen",tentruyen);

        navControl();

//        NavController navController = Navigation.findNavController(ThongTinTruyen.this, R.id.nav_host_fragment_ttt);
//        NavigationUI.setupWithNavController(bottomNavigationView, navController);


        //Set content UI
        Query query = Constans.DATABASE.getReference().child("TRUYEN").child(tentruyen);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("NewApi")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TRUYEN truyen = snapshot.getValue(TRUYEN.class);
                Picasso.get()
                        .load(truyen.getUrl_anh_nen_truyen())
                        .transform(new BlurTransformation(ThongTinTruyen.this,10,1))
                        .into(background);

                Picasso.get()
                        .load(truyen.getUrl_anh_nen_truyen())
                        .fit()
                        .into(image_ttt);

                ten_truyen.setText(truyen.getTen_truyen());
                tac_gia.setText(truyen.getTac_gia());
//                mo_ta.setText(truyen.getMo_ta());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        event();


    }

    private void event() {


        if (Constans.AUTH.getCurrentUser()!=null){
            linearLayout.setVisibility(View.VISIBLE);

            Constans.DATABASE.getReference(Constans.DE_CU).child(Constans.AUTH.getCurrentUser().getUid()).child(tentruyen).child("ten_truyen")
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                        @Override
                        public void onSuccess(DataSnapshot dataSnapshot) {
                            String x = dataSnapshot.getValue(String.class);
                            if (x!=null){
                                if (x.equals(tentruyen)){
                                    decu = true;
                                    Log.e("error",x);
                                    bt_de_cu_truyen.setText("Hủy đề cử");
                                    bt_de_cu_truyen.setBackground(getDrawable(R.color.gray_600));
                                }else {
                                    decu = false;
                                }
                            }
                            else {
                                decu = false;
                            }
                        }
                    });

            Constans.DATABASE.getReference(Constans.TU_TRUYEN).child(Constans.AUTH.getCurrentUser().getUid()).child(tentruyen).child("ten_truyen")
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                        @Override
                        public void onSuccess(DataSnapshot dataSnapshot) {
                            String x = dataSnapshot.getValue(String.class);
                            if (x!=null){
                                tutruyen = true;
                                Log.e("error",x);
                                bt_them_vao_tu.setText("-");
                                bt_them_vao_tu.setBackground(getDrawable(R.color.gray_600));
                            }
                            else {
                                tutruyen = false;
                            }
                        }
                    });

            bt_them_vao_tu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (tutruyen){
                        tutruyen=false;
                        bt_them_vao_tu.setText("+");
                        bt_them_vao_tu.setBackground(getDrawable(R.color.teal_200));
                        Constans.DATABASE.getReference(Constans.TU_TRUYEN).child(Constans.AUTH.getCurrentUser().getUid()).child(tentruyen)
                                .get()
                                .addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                                    @Override
                                    public void onSuccess(DataSnapshot dataSnapshot) {
                                        LICHSU lichsu = dataSnapshot.getValue(LICHSU.class);
                                        if (lichsu!=null){
                                            Constans.DATABASE.getReference(Constans.TU_TRUYEN).child(Constans.AUTH.getCurrentUser().getUid()).child(tentruyen)
                                                    .removeValue();
                                            if (!lichsu.getTen_chuong().equals("none")){
                                                Constans.DATABASE.getReference(Constans.LICH_SU).child(Constans.AUTH.getCurrentUser().getUid()).child(tentruyen)
                                                        .setValue(lichsu);
                                            }
                                        }
                                    }
                                });
                    }else {
                        tutruyen=true;
                        bt_them_vao_tu.setText("-");
                        bt_them_vao_tu.setBackground(getDrawable(R.color.gray_600));
                        Constans.DATABASE.getReference(Constans.LICH_SU).child(Constans.AUTH.getCurrentUser().getUid()).child(tentruyen)
                                .get()
                                .addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                                    @Override
                                    public void onSuccess(DataSnapshot dataSnapshot) {
                                        LICHSU lichsu = dataSnapshot.getValue(LICHSU.class);
                                        if (lichsu!=null){
                                            Constans.DATABASE.getReference(Constans.TU_TRUYEN).child(Constans.AUTH.getCurrentUser().getUid()).child(tentruyen)
                                                    .setValue(lichsu);
                                            Constans.DATABASE.getReference(Constans.LICH_SU).child(Constans.AUTH.getCurrentUser().getUid()).child(tentruyen).removeValue();
                                        }else {
                                            Constans.DATABASE.getReference(Constans.TU_TRUYEN).child(Constans.AUTH.getCurrentUser().getUid()).child(tentruyen)
                                                    .setValue(new LICHSU(tentruyen,"none"));
                                        }
                                    }
                                });



                    }
                }
            });

            bt_de_cu_truyen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ThongTinTruyen.this);

                    builder.setTitle("THÔNG BÁO !");

                    if (decu){
                        builder.setMessage("Xác nhận hủy đề cử: "+tentruyen);
                    }else builder.setMessage("Xác nhận đề cử: "+tentruyen);

                    builder.setNeutralButton("Hủy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (decu){
                                decu =false;
                                Comco.capNhatDeCu(tentruyen,false);
                                bt_de_cu_truyen.setText("Đề cử");
                                bt_de_cu_truyen.setBackground(getDrawable(R.color.teal_200));
                                Constans.DATABASE.getReference(Constans.DE_CU).child(Constans.AUTH.getCurrentUser().getUid()).child(tentruyen).child("ten_truyen").removeValue();
                            }else {
                                decu = true;
                                Comco.capNhatDeCu(tentruyen,true    );
                                bt_de_cu_truyen.setText("Hủy đề cử");
                                bt_de_cu_truyen.setBackground(getDrawable(R.color.gray_600));
                                Constans.DATABASE.getReference(Constans.DE_CU).child(Constans.AUTH.getCurrentUser().getUid()).child(tentruyen).child("ten_truyen").setValue(tentruyen);
                            }
                        }
                    });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });

            bt_doc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (tutruyen){

                        Constans.DATABASE.getReference(Constans.TU_TRUYEN).child(Constans.AUTH.getCurrentUser().getUid()).child(tentruyen).child("ten_chuong")
                                .get()
                                .addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                                    @Override
                                    public void onSuccess(DataSnapshot dataSnapshot) {
                                        String tenChuong = dataSnapshot.getValue(String.class);
                                        if (!tenChuong.equals("none")){
                                            Intent intent = new Intent(ThongTinTruyen.this, DocTruyen.class);
                                            intent.putExtra("ten_truyen",tentruyen);
                                            intent.putExtra("ten_chuong",tenChuong);
                                            startActivity(intent);
                                        }else {
                                            Constans.DATABASE.getReference(Constans.CHUONG).child(tentruyen).limitToFirst(1)
                                                    .get()
                                                    .addOnCompleteListener(new OnCompleteListener<DataSnapshot  >() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                                                            if (task.isSuccessful()){
                                                                DataSnapshot dataSnapshot1 = task.getResult();
                                                                for (DataSnapshot data : dataSnapshot1.getChildren()){
                                                                    System.out.println("test "+data.getValue(CHUONG.class).getTen_chuoong());
                                                                    //
                                                                    Intent intent = new Intent(ThongTinTruyen.this, DocTruyen.class);
                                                                    intent.putExtra("ten_truyen",tentruyen);
                                                                    intent.putExtra("ten_chuong",data.getValue(CHUONG.class).getTen_chuoong());
                                                                    startActivity(intent);
                                                                    //
                                                                    Comco.capNhatView(tentruyen);
                                                                }
                                                            }
                                                        }
                                                    });
                                        }
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        System.out.println("test "+e.getMessage());
                                    }
                                });

                    }else {
                        Constans.DATABASE.getReference(Constans.LICH_SU).child(Constans.AUTH.getCurrentUser().getUid()).child(tentruyen).child("ten_chuong")
                                .get()
                                .addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                                    @Override
                                    public void onSuccess(DataSnapshot dataSnapshot) {
                                        String tenChuong = dataSnapshot.getValue(String.class);
                                        if (tenChuong!=null){
                                            Intent intent = new Intent(ThongTinTruyen.this, DocTruyen.class);
                                            intent.putExtra("ten_truyen",tentruyen);
                                            intent.putExtra("ten_chuong",tenChuong);
                                            startActivity(intent);
                                        }else {
                                            Constans.DATABASE.getReference(Constans.CHUONG).child(tentruyen).limitToFirst(1)
                                                    .get()
                                                    .addOnCompleteListener(new OnCompleteListener<DataSnapshot  >() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                                                            if (task.isSuccessful()){
                                                                DataSnapshot dataSnapshot1 = task.getResult();
                                                                for (DataSnapshot data : dataSnapshot1.getChildren()){
                                                                    System.out.println("test "+data.getValue(CHUONG.class).getTen_chuoong());
                                                                    //
                                                                    Intent intent = new Intent(ThongTinTruyen.this, DocTruyen.class);
                                                                    intent.putExtra("ten_truyen",tentruyen);
                                                                    intent.putExtra("ten_chuong",data.getValue(CHUONG.class).getTen_chuoong());
                                                                    startActivity(intent);
                                                                    //
                                                                    Comco.capNhatView(tentruyen);
                                                                }
                                                            }
                                                        }
                                                    });
                                        }
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        System.out.println("test "+e.getMessage());
                                    }
                                });
                    }
                }
            });

        }else{
            bt_doc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Constans.DATABASE.getReference(Constans.CHUONG).child(tentruyen).limitToFirst(1)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<DataSnapshot  >() {
                                @Override
                                public void onComplete(@NonNull Task<DataSnapshot> task) {
                                    if (task.isSuccessful()){
                                        DataSnapshot dataSnapshot1 = task.getResult();
                                        for (DataSnapshot data : dataSnapshot1.getChildren()){
                                            //
                                            Intent intent = new Intent(ThongTinTruyen.this, DocTruyen.class);
                                            intent.putExtra("ten_truyen",tentruyen);
                                            intent.putExtra("ten_chuong",data.getValue(CHUONG.class).getTen_chuoong());
                                            try {

                                                startActivity(intent);

                                            }catch (Exception e){
                                                Log.e("Error",e.toString());
                                            }
//                                            //
                                            Comco.capNhatView(tentruyen);
                                        }
                                    }
                                }
                            });
                }
            });

            linearLayout.setVisibility(View.GONE);
        }

    }

    private void navControl(){
        BottomNavigationView bottomNavigationView = findViewById(R.id.thong_tin_truyen_nav_view);
        Fragment fragment = new GioiThieuTruyen();
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment_ttt,fragment)
                .commit();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment1;
                switch (item.getItemId()){
                    case R.id.gioiThieuTruyen:
                        if (appBarLayout.getTop() < 0)
                            appBarLayout.setExpanded(true);
                        fragment1 = new GioiThieuTruyen();
                        break;
                    case R.id.danhSachChuong_fragment:
                        if (appBarLayout.getTop() < 0)
                            appBarLayout.setExpanded(true);
                        fragment1 = new  DanhSachChuong_fragment();
                        break;
                    case R.id.binhLuanVaDanhGia:
                        if (appBarLayout.getTop() >= 0)
                            appBarLayout.setExpanded(false);
                        fragment1 = new BinhLuanVaDanhGia();
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + item.getItemId());
                }
                fragment1.setArguments(bundle);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment_ttt,fragment1)
                        .commit();
                return true;
            }
        });
    }

}