package com.ak.doctruyenchu.ui.DangTruyen.QuanLyDangTruyen.QuanLyTruyen.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.ak.doctruyenchu.Constans.Constans;
import com.ak.doctruyenchu.R;
import com.ak.doctruyenchu.models.TRUYEN;
import com.ak.doctruyenchu.ui.DangTruyen.DangTruyen;
import com.ak.doctruyenchu.ui.DangTruyen.QuanLyDangTruyen.QuanLyChuong.Them_Sua_Chuong;
import com.ak.doctruyenchu.ui.DangTruyen.QuanLyDangTruyen.adapter.QLDTadapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TruyenDaPublic#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TruyenDaPublic extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private QLDTadapter adapter;
    private View view;
    private ArrayList<TRUYEN> truyenArrayList;

    public TruyenDaPublic() {
        // Required empty public constructor
        this.truyenArrayList = new ArrayList<>();
        adapter = new QLDTadapter(this.truyenArrayList);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TruyenDaPublic.
     */
    // TODO: Rename and change types and number of parameters
    public static TruyenDaPublic newInstance(String param1, String param2) {
        TruyenDaPublic fragment = new TruyenDaPublic();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_truyen_da_public, container, false);

        recyclerView = view.findViewById(R.id.rcv_dapublic);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        run();
        event();

    }

    private void event() {
        adapter.onClick(new QLDTadapter.QLDTadapterOnClick() {
            @Override
            public void onClick(View view, TRUYEN truyen) {
                PopupMenu menu = new PopupMenu(view.getContext(),view);
                menu.inflate(R.menu.menu_item_dapublic);
                menu.setGravity(Gravity.END);
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.them_chuong:
                                Intent intent = new Intent(getContext(),Them_Sua_Chuong.class);
                                intent.putExtra("ten_truyen",truyen.getTen_truyen());
                                startActivity(intent);
                                break;
                        }
                        return true;
                    }
                });
                menu.show();

            }
        });
    }

    private void run() {
        truyenArrayList.clear();
        Constans.DATABASE.getReference(Constans.QUAN_LY_DANG_TRUYEN).child(Constans.AUTH.getCurrentUser().getUid()).child(Constans.DA_PUBLIC)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        for (DataSnapshot data : dataSnapshot.getChildren()){
                            String ten_truyen = data.getValue(String.class);
                            Constans.DATABASE.getReference(Constans.TRUYEN).child(ten_truyen)
                                    .get()
                                    .addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                                        @Override
                                        public void onSuccess(DataSnapshot dataSnapshot) {
                                            TRUYEN truyen = dataSnapshot.getValue(TRUYEN.class);
                                            if (truyen!=null){
                                                truyenArrayList.add(truyen);
                                                adapter.notifyDataSetChanged();
                                            }
                                        }
                                    });
                        }
                    }
                });
    }
}