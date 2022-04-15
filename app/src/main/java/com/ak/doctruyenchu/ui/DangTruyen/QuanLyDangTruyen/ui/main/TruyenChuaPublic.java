package com.ak.doctruyenchu.ui.DangTruyen.QuanLyDangTruyen.ui.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.ak.doctruyenchu.ui.DangTruyen.QuanLyDangTruyen.adapter.QLDTadapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TruyenChuaPublic#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TruyenChuaPublic extends Fragment {

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

    public TruyenChuaPublic() {
        this.truyenArrayList = new ArrayList<>();
        adapter = new QLDTadapter(this.truyenArrayList);
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TruyenChuaPublic.
     */
    // TODO: Rename and change types and number of parameters
    public static TruyenChuaPublic newInstance(String param1, String param2) {
        TruyenChuaPublic fragment = new TruyenChuaPublic();
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
        view = inflater.inflate(R.layout.fragment_truyen_chua_public, container, false);

        recyclerView = view.findViewById(R.id.rcv_chua_pubplic);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        this.truyenArrayList.clear();
        run();
        event();
    }

    private void event() {
        adapter.onClick(new QLDTadapter.QLDTadapterOnClick() {
            @Override
            public void onClick(View view, TRUYEN truyen) {
                PopupMenu menu = new PopupMenu(view.getContext(),view);
                menu.inflate(R.menu.menu_item_dang_truyen);
                menu.setGravity(Gravity.END);
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.public_truyen:
                                break;
                            case R.id.edit_truyen:
                                String path = Constans.QUAN_LY_DANG_TRUYEN +"/"+ Constans.AUTH.getCurrentUser().getUid() +"/"+Constans.CHUA_PUBLIC + "/" + Constans.TRUYEN + "/" + truyen.getTen_truyen();
                                Intent intent = new Intent(getContext(), DangTruyen.class);
                                intent.putExtra("path",path);
                                startActivity(intent);
                                break;
                            case R.id.them_chuong:
                                break;
                            case R.id.delete_truyen:
                                Constans.STORAGE.getReference(Constans.PICTURE).child(Constans.ANH_BIA_TRUYEN).child(Constans.AUTH.getCurrentUser().getUid())
                                        .child(truyen.getTen_truyen()).delete();
                                Constans.DATABASE.getReference(Constans.QUAN_LY_DANG_TRUYEN).child(Constans.AUTH.getCurrentUser().getUid()).child(Constans.CHUA_PUBLIC)
                                        .child(Constans.TRUYEN)
                                        .child(truyen.getTen_truyen()).removeValue();
                                truyenArrayList.remove(truyen);
                                adapter.notifyDataSetChanged();
                                Toast.makeText(getContext(), "Thành công", Toast.LENGTH_SHORT).show();
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
        Constans.DATABASE.getReference(Constans.QUAN_LY_DANG_TRUYEN).child(Constans.AUTH.getCurrentUser().getUid()).child(Constans.CHUA_PUBLIC).child(Constans.TRUYEN)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()){
                            for (DataSnapshot data : task.getResult().getChildren()){
                                TRUYEN truyen = data.getValue(TRUYEN.class);
                                truyenArrayList.add(truyen);
                                Log.e("TRUYEN",""+truyen.getTen_truyen());
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }
}