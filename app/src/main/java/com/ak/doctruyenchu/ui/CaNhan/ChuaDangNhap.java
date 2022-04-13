package com.ak.doctruyenchu.ui.CaNhan;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ak.doctruyenchu.Constans.Constans;
import com.ak.doctruyenchu.R;
import com.ak.doctruyenchu.ui.DangKyDangNhap.Login;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChuaDangNhap#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChuaDangNhap extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View view;
    private CardView cardView;

    public ChuaDangNhap() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChuaDangNhap.
     */
    // TODO: Rename and change types and number of parameters
    public static ChuaDangNhap newInstance(String param1, String param2) {
        ChuaDangNhap fragment = new ChuaDangNhap();
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
        view = inflater.inflate(R.layout.fragment_chua_dang_nhap, container, false);
        cardView = view.findViewById(R.id.bt_dang_nhap_dang_ky);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Login.class));
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (Constans.AUTH.getCurrentUser()!=null){
//            getParentFragmentManager().beginTransaction().remove(getParentFragmentManager().findFragmentByTag("CHUA_DANG_NHAP")).commit();
//            getParentFragmentManager().beginTransaction().replace(R.id.frg_trang_ca_nhan,new DaDangNhap(),"DA_DANG_NHAP").commit();
//        }

//        if (Constans.AUTH.getCurrentUser()!=null)
//            getParentFragmentManager().beginTransaction().remove(this).commit();


    }



}