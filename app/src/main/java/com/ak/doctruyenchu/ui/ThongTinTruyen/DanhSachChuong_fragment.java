package com.ak.doctruyenchu.ui.ThongTinTruyen;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ak.doctruyenchu.Constans.Comco;
import com.ak.doctruyenchu.Constans.Constans;
import com.ak.doctruyenchu.R;
import com.ak.doctruyenchu.models.CHUONG;
import com.ak.doctruyenchu.models.LICHSU;
import com.ak.doctruyenchu.ui.DocTruyen.DocTruyen;
import com.ak.doctruyenchu.ui.ThongTinTruyen.adapter.DanhSachChuongAdapter;
import com.ak.doctruyenchu.ui.moduleView.Module;
import com.ak.doctruyenchu.ui.moduleView.ModuleView2;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import java.util.ArrayList;;
import java.util.concurrent.Executor;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DanhSachChuong_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DanhSachChuong_fragment extends Fragment {

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

    public DanhSachChuong_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DanhSachChuong_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DanhSachChuong_fragment newInstance(String param1, String param2) {
        DanhSachChuong_fragment fragment = new DanhSachChuong_fragment();
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
        System.out.println("mart b");
        tenTruyen=getArguments().getString("ten_truyen");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//
//        getChildFragmentManager().beginTransaction()
//                .replace(R.id.subFrag_DSC,new ListChuong())
//                .commit();

        view = inflater.inflate(R.layout.fragment_danh_sach_chuong_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Constans.DATABASE.getReference("CHUONG").child(tenTruyen).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot dataSnapshot = task.getResult();
                    chuongs = new ArrayList<>();
                    for (DataSnapshot x : dataSnapshot.getChildren()) {
                        CHUONG c = x.getValue(CHUONG.class);
                        chuongs.add(c);
                    }
                    System.out.println("size arr "+ chuongs.size());
                    adapter = new DanhSachChuongAdapter(chuongs);
                    recyclerView = view.findViewById(R.id.rcv_DSC);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(adapter);
                    adapter.onClick(new DanhSachChuongAdapter.HolderOnClick() {
                        @Override
                        public void holderOnClick(CHUONG chuong) {
                            Intent intent = new Intent(getContext(), DocTruyen.class);
                            intent.putExtra("ten_truyen",tenTruyen);
                            intent.putExtra("ten_chuong",chuong.getTen_chuoong());
                            startActivity(intent);
                            Comco.capNhatView(tenTruyen);
                        }
                    });
                }
            }
        });
    }

    //


}