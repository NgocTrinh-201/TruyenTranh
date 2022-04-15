package com.ak.doctruyenchu.ui.ThongTinTruyen;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ak.doctruyenchu.Constans.Comco;
import com.ak.doctruyenchu.Constans.Constans;
import com.ak.doctruyenchu.R;
import com.ak.doctruyenchu.models.TRUYEN;
import com.ak.doctruyenchu.ui.moduleView.Module;
import com.ak.doctruyenchu.ui.moduleView.ModuleView2;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.BlurTransformation;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GioiThieuTruyen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GioiThieuTruyen extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View view;
    private TextView mo_ta;
    private String tentruyen;

    public GioiThieuTruyen() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GioiThieuTruyen.
     */
    // TODO: Rename and change types and number of parameters
    public static GioiThieuTruyen newInstance(String param1, String param2) {
        GioiThieuTruyen fragment = new GioiThieuTruyen();
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
        tentruyen=getArguments().getString("ten_truyen");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_gioi_thieu_truyen, container, false);
        unitUI();
        run();

        return view;
    }

    private void run() {
        Query query = Constans.DATABASE.getReference().child("TRUYEN").child(tentruyen);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("NewApi")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TRUYEN truyen = snapshot.getValue(TRUYEN.class);
                mo_ta.setText(truyen.getMo_ta());
                ModuleView2 moduleView2 = new ModuleView2();
                //moduleView2.setModule(new Module(  false,1,Constans.LAY_TRUYEN_THEO_TEN_TAC_GIA,truyen.getTac_gia(),"Truyện cùng tác giả",Constans.VIEW_GIOI_THIEU_TRUYEN,truyen.getTen_truyen()));
                new Comco().layTruyenCungTacGia(10,truyen.getTac_gia(),tentruyen, new Comco.DataRecived() {
                    @Override
                    public void complete(ArrayList<TRUYEN> truyenArrayList) {
                        moduleView2.setModuleAndArray(new Module(getString(R.string.truyen_cung_tac_gia),1,false),truyenArrayList);
                        setView(moduleView2,R.id.frg_truyen_cung_tac_gia_GTT);
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void unitUI() {
        mo_ta = view.findViewById(R.id.tv_mo_ta_GTT);
    }

    private void setView(Fragment fragment,int id){
        getChildFragmentManager()
                .beginTransaction()
                .replace(id,fragment)
                .commit();
    }
}