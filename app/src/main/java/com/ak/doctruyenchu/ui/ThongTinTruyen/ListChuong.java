package com.ak.doctruyenchu.ui.ThongTinTruyen;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ak.doctruyenchu.R;
import com.ak.doctruyenchu.models.CHUONG;
import com.ak.doctruyenchu.ui.ThongTinTruyen.adapter.DanhSachChuongAdapter;

import java.util.ArrayList;
;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListChuong#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListChuong extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View view;
    private String tenTruyen;
    private RecyclerView recyclerView;
    private DanhSachChuongAdapter adapter;
    private ArrayList<CHUONG> chuongs;

    public ListChuong() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListChuong.
     */
    // TODO: Rename and change types and number of parameters
    public static ListChuong newInstance(String param1, String param2) {
        ListChuong fragment = new ListChuong();
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
        view = inflater.inflate(R.layout.fragment_list_chuong, container, false);

        return view;
    }



}