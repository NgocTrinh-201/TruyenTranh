package com.ak.doctruyenchu.ui.ThongTinTruyen;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ak.doctruyenchu.Constans.Constans;
import com.ak.doctruyenchu.R;
import com.ak.doctruyenchu.models.BinhLuan;
import com.ak.doctruyenchu.ui.CaNhan.ChuaDangNhap;
import com.ak.doctruyenchu.ui.ThongTinTruyen.adapter.BinhLuanAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BinhLuanVaDanhGia#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BinhLuanVaDanhGia extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View view;
    private EditText editText;
    private TextView button, sentRate;
    private String ten_truyen;
    private RecyclerView recyclerView;
    private BinhLuanAdapter adapter;
    private ArrayList<BinhLuan> binhLuanArrayList;
    private CountDownTimer countDownTimer;
    private RatingBar ratingBar;
    private boolean rated;

    public BinhLuanVaDanhGia() {
        // Required empty public constructor
        rated = false;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BinhLuanVaDanhGia.
     */
    // TODO: Rename and change types and number of parameters
    public static BinhLuanVaDanhGia newInstance(String param1, String param2) {
        BinhLuanVaDanhGia fragment = new BinhLuanVaDanhGia();
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
        ten_truyen = getArguments().getString("ten_truyen");
    }

    private void run(){
        binhLuanArrayList = new ArrayList<>();
        Constans.DATABASE.getReference(Constans.BINH_LUAN).child(ten_truyen)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        binhLuanArrayList.clear();
                        for (DataSnapshot data : snapshot.getChildren()){
                            BinhLuan binhLuan = data.getValue(BinhLuan.class);
                            binhLuanArrayList.add(0,binhLuan);
                        }

                        adapter = new BinhLuanAdapter(binhLuanArrayList);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        recyclerView.setAdapter(adapter);

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.binh_luan_va_danh_gia, container, false);
        recyclerView = view.findViewById(R.id.rcv_comment);
        run();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Constans.AUTH.getCurrentUser()==null){
            setView(new ChuaDangNhap(),"CHUA_DANG_NHAP");
        }else {
            if (getParentFragmentManager().findFragmentByTag("CHUA_DANG_NHAP")==null){
                Bundle bundle = new Bundle();
                bundle.putString("ten_truyen",ten_truyen);
                Fragment_tool_comment_rate frg = new Fragment_tool_comment_rate();
                frg.setArguments(bundle);
                setView(frg,"FRG_TOOL_COMT");
            }
        }
    }

    private void setView(Fragment fragment,String tag){
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.com_rate_tool,fragment,tag)
                .commit();
    }

}