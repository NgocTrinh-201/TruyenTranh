package com.ak.doctruyenchu.ui.CaNhan;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ak.doctruyenchu.Constans.Constans;
import com.ak.doctruyenchu.R;
import com.ak.doctruyenchu.models.USER;
import com.ak.doctruyenchu.ui.CaNhan.ChinhSuaThongTin.ChinhSuaThongTin;
import com.ak.doctruyenchu.ui.DangTruyen.QuanLyDangTruyen.QuanLyTruyen.QuanLy;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.BlurTransformation;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DaDangNhap#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DaDangNhap extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View view;
    private CardView bt_signout, bt_chinh_sua_thong_tin, bt_dang_truyen;

    private TextView tv_ten_ngoi_dung, tv_email_nguoi_dung, tv_loai_tai_khoan;
    private ImageView img_anh_dai_dien,background;
    private Bundle bundle ;


    public DaDangNhap() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DaDangNhap.
     */
    // TODO: Rename and change types and number of parameters
    public static DaDangNhap newInstance(String param1, String param2) {
        DaDangNhap fragment = new DaDangNhap();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_da_dang_nhap, container, false);

        return view;
    }

    private void unitUI() {
        bt_signout = view.findViewById(R.id.bt_dang_xuat);
        bt_chinh_sua_thong_tin = view.findViewById(R.id.bt_chinh_sua_thong_tin);
        tv_ten_ngoi_dung = view.findViewById(R.id.tv_ten_nguoi_dung);
        tv_email_nguoi_dung = view.findViewById(R.id.tv_email_nguoi_dung);
        img_anh_dai_dien = view.findViewById(R.id.img_anh_dai_dien);
        bt_dang_truyen = view.findViewById(R.id.bt_dang_truyen);
        tv_loai_tai_khoan = view.findViewById(R.id.tv_loai_tai_khoan);
        background = view.findViewById(R.id.background_ddn);

        //
        Constans.DATABASE.getReference("USER").child(Constans.AUTH.getUid()).get()
                .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()){
                            USER user = task.getResult().getValue(USER.class);
                            tv_ten_ngoi_dung.setText(user.getTen_nguoi_dung());
                            tv_email_nguoi_dung.setText(user.getEmail());
                            Picasso.get()
                                    .load(user.getUrl_anh_dai_dien())
                                    .error(R.drawable.ic_baseline_person_outline_24)
                                    .into(img_anh_dai_dien);
                            Picasso.get()
                                    .load(R.drawable.background2)
                                    .transform(new BlurTransformation(getContext(),20,1))
                                    .error(R.drawable.ic_baseline_person_outline_24)
                                    .into(background);
                            tv_loai_tai_khoan.setText(user.getLoai_tai_khoan());
                            if (user.getLoai_tai_khoan().equals(Constans.AUTHOR))
                                bt_dang_truyen.setVisibility(View.VISIBLE);
//                            else bt_dang_truyen.setVisibility(View.GONE);

                        }
                    }
                });
    }

    private void event() {
        bt_signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constans.AUTH.signOut();
//                getParentFragmentManager().beginTransaction().remove(getParentFragmentManager().findFragmentByTag("DA_DANG_NHAP")).commit();

                remove();
                getParentFragmentManager().beginTransaction().replace(R.id.frg_trang_ca_nhan,new ChuaDangNhap(),"CHUA_DANG_NHAP").commit();
            }
        });

        bt_chinh_sua_thong_tin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ChinhSuaThongTin.class));
            }
        });

        bt_dang_truyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), QuanLy.class));
            }
        });

    }
    @Override
    public void onResume() {
        super.onResume();
        unitUI();
        event();
    }

    private void remove(){
        getParentFragmentManager().beginTransaction().remove(this).commit();
    }

    @Override
    public void onPause() {
        super.onPause();
        remove();
    }

}