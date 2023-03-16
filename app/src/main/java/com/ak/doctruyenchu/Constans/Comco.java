package com.ak.doctruyenchu.Constans;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ak.doctruyenchu.models.LICHSU;
import com.ak.doctruyenchu.models.TRUYEN;

import com.google.android.gms.tasks.OnCompleteListener;;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class Comco {


    public static void capNhatView(String tenTruyen){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Constans.DATABASE.getReference(Constans.TRUYEN).child(tenTruyen).child("so_view")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                if (task.isSuccessful()){
                                    int view = task.getResult().getValue(int.class);
                                    Constans.DATABASE.getReference(Constans.TRUYEN).child(tenTruyen).child("so_view").setValue(view+1);
                                }
                            }
                        });
            }
        }).start();
    }

    public static void capNhatDeCu(String tenTruyen, boolean isIncre){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Constans.DATABASE.getReference(Constans.TRUYEN).child(tenTruyen).child("de_cu")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                if (task.isSuccessful()){
                                    int de_cu = task.getResult().getValue(int.class);
                                    if (isIncre)
                                        Constans.DATABASE.getReference(Constans.TRUYEN).child(tenTruyen).child("de_cu").setValue(de_cu+1);
                                    else Constans.DATABASE.getReference(Constans.TRUYEN).child(tenTruyen).child("de_cu").setValue(de_cu-1);
                                }
                            }
                        });
            }
        }).start();
    }

    public static void capNhatRateVaLuotRate(String tenTruyen, float rate){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Constans.DATABASE.getReference(Constans.TRUYEN).child(tenTruyen)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                            @Override
                            public void onSuccess(DataSnapshot dataSnapshot) {
                                TRUYEN truyen = dataSnapshot.getValue(TRUYEN.class);
                                if (truyen!=null){
                                    int so_luot_rate;
                                    float newRate = ((truyen.getRate()* truyen.getSo_luot_rate())+rate)/(so_luot_rate=truyen.getSo_luot_rate()+1);
                                    truyen.setRate(newRate);
                                    truyen.setSo_luot_rate(so_luot_rate);
                                    Constans.DATABASE.getReference(Constans.TRUYEN).child(tenTruyen).setValue(truyen);
                                }
                            }
                        });
            }
        }).start();
    }

    public static void capNhatRate(String tenTruyen, float rate){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Constans.DATABASE.getReference(Constans.DANH_GIA).child(tenTruyen).child(Constans.AUTH.getCurrentUser().getUid()).child("rate")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                if (task.isSuccessful()){
                                    float oldRate = task.getResult().getValue(float.class);
                                    System.out.println("old "+oldRate);
                                    Constans.DATABASE.getReference(Constans.TRUYEN).child(tenTruyen)
                                            .get()
                                            .addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                                                @Override
                                                public void onSuccess(DataSnapshot dataSnapshot) {
                                                    TRUYEN truyen = dataSnapshot.getValue(TRUYEN.class);
                                                    if (truyen!=null){
                                                        float newRate = truyen.getRate() - (oldRate/ truyen.getSo_luot_rate()) + (rate/ truyen.getSo_luot_rate());
                                                        truyen.setRate(newRate);
                                                        Constans.DATABASE.getReference(Constans.TRUYEN).child(tenTruyen).setValue(truyen);
                                                    }
                                                }
                                            });
                                }
                            }
                        });
            }
        }).start();
    }


    public static void layTruyenCungTacGia(int top,String tac_gia,String ten_truyen,DataRecived dataRecived) {
        int z =0;
        if (top<1)
            z=1000;
        else z=top;
        ArrayList<TRUYEN> truyenArrayList = new ArrayList<>();
        Query query = Constans.DATABASE.getReference().child("TRUYEN").orderByChild("tac_gia").startAt(tac_gia).endAt(tac_gia+"\uf8ff").limitToFirst(z);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                TRUYEN truyen = snapshot.getValue(TRUYEN.class);
                if (truyen!=null){
                    if (!truyen.getTen_truyen().equals(ten_truyen)){
                        truyenArrayList.add(truyen);
                        dataRecived.complete(truyenArrayList);
                    }
                }
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

    public static void laytTopTruyenDeCu(int top,DataRecived dataRecived) {
        int z =0;
        if (top<1)
            z=1000;
        else z=top;
        ArrayList<TRUYEN> truyenArrayList = new ArrayList<>();
        Query query = Constans.DATABASE.getReference().child("TRUYEN").orderByChild("de_cu").limitToLast(z);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                TRUYEN truyen = snapshot.getValue(TRUYEN.class);
                if (truyen!=null){
                    truyenArrayList.add(0,truyen);
                    dataRecived.complete(truyenArrayList);
                }
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

    public static void layTopTruyenMoiNhat(int top,DataRecived dataRecived) {
        int z =0;
        if (top<1)
            z=1000;
        else z=top;
        ArrayList<TRUYEN> truyenArrayList = new ArrayList<>();
        Query query = Constans.DATABASE.getReference().child("TRUYEN").orderByChild("ngay_dang/time").limitToLast(z);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                TRUYEN truyen = snapshot.getValue(TRUYEN.class);
                if (truyen!=null){
                    truyenArrayList.add(0,truyen);
                    dataRecived.complete(truyenArrayList);
                }
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

    public static void laytTopRateTruyen(int top,DataRecived dataRecived) {
        int z =0;
        if (top<1)
            z=1000;
        else z=top;
        ArrayList<TRUYEN> truyenArrayList = new ArrayList<>();
        Query query = Constans.DATABASE.getReference().child("TRUYEN").orderByChild("rate").limitToLast(z);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                TRUYEN truyen = snapshot.getValue(TRUYEN.class);
                if (truyen!=null){
                    truyenArrayList.add(0,truyen);
                    dataRecived.complete(truyenArrayList);
                }
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

    public static void laytTopViewTruyen(int top,DataRecived dataRecived) {
        int z =0;
        if (top<1)
            z=1000;
        else z=top;
        ArrayList<TRUYEN> truyenArrayList = new ArrayList<>();
        Query query = Constans.DATABASE.getReference().child("TRUYEN").orderByChild("de_cu").limitToLast(z);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                TRUYEN truyen = snapshot.getValue(TRUYEN.class);
                if (truyen!=null){
                    truyenArrayList.add(0,truyen);
                    dataRecived.complete(truyenArrayList);
                }
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

    public static void layLichSuDocTruyen(DataRecived dataRecived) {
        ArrayList<TRUYEN> truyenArrayList = new ArrayList<>();
        Constans.DATABASE.getReference().child(Constans.LICH_SU).child(Constans.AUTH.getCurrentUser().getUid()).orderByChild("time")
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        LICHSU lichsu = snapshot.getValue(LICHSU.class);
                        Constans.DATABASE.getReference(Constans.TRUYEN).child(lichsu.getTen_truyen())
                                .get()
                                .addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                                    @Override
                                    public void onSuccess(DataSnapshot dataSnapshot) {
                                        TRUYEN truyen = dataSnapshot.getValue(TRUYEN.class);
                                        if (truyen!=null){
                                            truyen.setChap_moi_nhat(lichsu.getTen_chuong());
                                            truyenArrayList.add(0,truyen);
                                        }
//                                        if (i==snapshot.getChildrenCount()-1)
                                            dataRecived.complete(truyenArrayList);
                                    }
                                });

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
//        query.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                LICHSU lichsu = snapshot.getValue(LICHSU.class);
//                if (lichsu!=null){
//                    lichsuArrayList.add(0,lichsu);
//
//                }
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }

    public static void layLichTuTruyen(DataRecived dataRecived) {

//        Constans.DATABASE.getReference(Constans.TU_TRUYEN).child(Constans.AUTH.getCurrentUser().getUid())
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DataSnapshot> task) {
//                        ArrayList<LICHSU> lichsuArrayList;
//                    }
//                });

        ArrayList<TRUYEN> truyenArrayList = new ArrayList<>();
        Constans.DATABASE.getReference().child(Constans.TU_TRUYEN).child(Constans.AUTH.getCurrentUser().getUid()).orderByChild("time")
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        LICHSU lichsu = snapshot.getValue(LICHSU.class);
                        System.out.println("test "+lichsu.getTen_truyen());
                        Constans.DATABASE.getReference(Constans.TRUYEN).child(lichsu.getTen_truyen())
                                .get()
                                .addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                                    @Override
                                    public void onSuccess(DataSnapshot dataSnapshot) {
                                        TRUYEN truyen = dataSnapshot.getValue(TRUYEN.class);

                                        if (truyen!=null){
                                            truyen.setChap_moi_nhat(lichsu.getTen_chuong());
                                            truyenArrayList.add(0,truyen);
                                        }

//                                        if (i==snapshot.getChildrenCount()-1)
                                        dataRecived.complete(truyenArrayList);
                                    }
                                });


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
//        query.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                LICHSU lichsu = snapshot.getValue(LICHSU.class);
//                if (lichsu!=null){
//                    lichsuArrayList.add(0,lichsu);
//
//                }
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }

    //
    public interface DataRecived{
        public void complete(ArrayList<TRUYEN> truyenArrayList);
    }
}


