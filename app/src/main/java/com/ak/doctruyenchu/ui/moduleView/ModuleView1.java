package com.ak.doctruyenchu.ui.moduleView;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ak.doctruyenchu.Constans.Constans;
import com.ak.doctruyenchu.R;
import com.ak.doctruyenchu.models.TRUYEN;
import com.ak.doctruyenchu.ui.DanhSachTruyen.DanhSachTruyen;
import com.ak.doctruyenchu.ui.ThongTinTruyen.ThongTinTruyen;
import com.ak.doctruyenchu.ui.INTERFACE.HolderOnClick;
import com.ak.doctruyenchu.ui.moduleView.adapter.ModuleView1Adapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ModuleView1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ModuleView1 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View view;
    private ArrayList<TRUYEN> truyenArrayList;
    private Module module;
    private RecyclerView recyclerView;
    private ImageView imageView;
    private TextView the_loai, ten_truyen, mo_ta,tv_so_luot_rate, tv_title_module_view1;
    private CardView doc_truyen;
    private ConstraintLayout constraintLayout;
    private RatingBar ratingBar;
    private ModuleView1Adapter adapter;

    public ModuleView1() {
        // Required empty public constructor
        this.truyenArrayList = new ArrayList<>();
        adapter = new ModuleView1Adapter(truyenArrayList);

    }

    public void setArray(ArrayList<TRUYEN> truyenArrayList){
        this.truyenArrayList.clear();
        this.truyenArrayList.addAll(truyenArrayList);
        if (this.truyenArrayList.size()!=0)
            setView(this.truyenArrayList.get(0));
        adapter.notifyDataSetChanged();
    }

    public void setModule(Module module){
        this.module=module;
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ModuleView1.
     */
    // TODO: Rename and change types and number of parameters
    public static ModuleView1 newInstance(String param1, String param2) {
        ModuleView1 fragment = new ModuleView1();
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
        view = inflater.inflate(R.layout.module_view1, container, false);
        unitUI();
        run(truyenArrayList);
        return view;
    }

    private void run(ArrayList<TRUYEN> truyenArrayList){

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        adapter.onClick(new HolderOnClick() {
            @Override
            public void onClick(TRUYEN truyen) {
                setView(truyen);
            }
        });
        doc_truyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ThongTinTruyen.class);
                intent.putExtra("ten_truyen",ten_truyen.getText());
                startActivity(intent);
            }
        });

        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DanhSachTruyen.class);
                intent.putExtra(Constans.KEY,Constans.TRUYEN_MOI_NHAT);
                startActivity(intent);
            }
        });

    }

    private void setView(TRUYEN truyen){
        Picasso.get()
                .load(truyen.getUrl_anh_nen_truyen())
                .fit()
                .into(imageView);
        ten_truyen.setText(truyen.getTen_truyen());
        mo_ta.setText(truyen.getMo_ta());
        tv_so_luot_rate.setText("("+truyen.getSo_luot_rate()+" lượt)");
        ratingBar.setRating(truyen.getRate());

    }

    private void unitUI(){
        ratingBar = view.findViewById(R.id.ratebar_module_view1);
        tv_so_luot_rate = view.findViewById(R.id.tv_so_luot_rate_module_view1);
        doc_truyen = view.findViewById(R.id.bt_doc_truyen_module_view1);
        imageView = view.findViewById(R.id.image_module_view1);
        recyclerView = view.findViewById(R.id.rcv_module_view1);
        the_loai = view.findViewById(R.id.tv_the_loai_module_view1);
        mo_ta = view.findViewById(R.id.tv_mo_ta_module_view1);
        ten_truyen = view.findViewById(R.id.tv_ten_truyen_module_view1);
        constraintLayout = view.findViewById(R.id.title_module_view1);
        tv_title_module_view1 = view.findViewById(R.id.tv_title_module_view1);

        if (module!=null)
            tv_title_module_view1.setText(module.moduleName);
    }

}