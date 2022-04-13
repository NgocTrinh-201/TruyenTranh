package com.ak.doctruyenchu.ui.moduleView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ak.doctruyenchu.Constans.Constans;
import com.ak.doctruyenchu.R;
import com.ak.doctruyenchu.models.TRUYEN;
import com.ak.doctruyenchu.ui.DanhSachTruyen.DanhSachTruyen;
import com.ak.doctruyenchu.ui.ThongTinTruyen.ThongTinTruyen;
import com.ak.doctruyenchu.ui.moduleView.adapter.HolderOnClick;
import com.ak.doctruyenchu.ui.moduleView.adapter.ModuleView2Adapter;
import com.ak.doctruyenchu.ui.moduleView.adapter.OptionsOnClick;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ModuleView2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ModuleView2 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View view;
    private RecyclerView recyclerView;
    private ModuleView2Adapter adapter;
    private TextView module_name,more;
    private Module module;
    private ConstraintLayout constraintLayout;
    private LinearLayout background;
    private ArrayList<TRUYEN> truyenArrayList;
    private OptionsOnClick optionsOnClick;

    public ModuleView2() {
        truyenArrayList = new ArrayList<>();
        // Required empty public constructor
        optionsOnClick = new OptionsOnClick() {
            @Override
            public void onClick(TRUYEN truyen, int position) {
                Log.e("Erro","Not set event for Option click");
            }
        };

    }

    public void setModule(Module module,ArrayList<TRUYEN> truyenArrayList){
        this.module = module;
        this.truyenArrayList = truyenArrayList;
    }

    public void setEventForOptionOnClick(OptionsOnClick optionsOnClick){this.optionsOnClick =optionsOnClick;}

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ModuleView2.
     */
    // TODO: Rename and change types and number of parameters

    public static ModuleView2 newInstance(String param1, String param2) {
        ModuleView2 fragment = new ModuleView2();
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

    public void notifyAdapterDelete(int position){
        adapter.deleteItem(position);
        adapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.module_view2, container, false);

//        setUI();
        //
        adapter = new ModuleView2Adapter(truyenArrayList);

        try {
            unitUI();
            event();
        }catch (NullPointerException e){
            module_name = view.findViewById(R.id.tv_name_mdv2);
            constraintLayout = view.findViewById(R.id.title_mdv2);
            more = view.findViewById(R.id.more_mdv2);
            recyclerView = view.findViewById(R.id.rcv_mdv2);
            GridLayoutManager  x = new GridLayoutManager(getContext(),2);
            recyclerView.setLayoutManager(x);
        }
        recyclerView.setAdapter(adapter);
        event();
        //
        return view;
    }
    private void event() {
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DanhSachTruyen.class);
                intent.putExtra(Constans.KEY,module.moduleName);
                if (module.moduleName.equals(Constans.TRUYEN_CUNG_TAC_GIA))
                    intent.putExtra(Constans.TAC_GIA_KEY,truyenArrayList.get(0).getTac_gia());
                startActivity(intent);
            }
        });

        adapter.onClick(new HolderOnClick() {
            @Override
            public void onClick(TRUYEN truyen) {
                Intent intent = new Intent(getContext(), ThongTinTruyen.class);
                intent.putExtra("ten_truyen",truyen.getTen_truyen());
                startActivity(intent);
            }
        });
    }

    private void unitUI() {
        background = view.findViewById(R.id.background_module_view2);
        if ( module.backgroungColor!=0){
            background.setBackground(getContext().getDrawable(module.backgroungColor));
        }
        constraintLayout = view.findViewById(R.id.title_mdv2);
        module_name = view.findViewById(R.id.tv_name_mdv2);
        more = view.findViewById(R.id.more_mdv2);
        module_name.setText(module.moduleName);
        if (module.titleColor!=0){
            module_name.setTextColor(getContext().getColor(module.titleColor));
            more.setTextColor(getContext().getColor(module.titleColor));
        }
        adapter.setTextColor(module.titleColor,getContext());
        adapter.setOrientationVertical(module.orientationVertical);
        adapter.EnableOptionButton(module.enableOptionButton);
        adapter.setOptionsOnClick(optionsOnClick);
        recyclerView = view.findViewById(R.id.rcv_mdv2);
        GridLayoutManager  x = new GridLayoutManager(getContext(),module.rows);
        if (module.orientationVertical)
            x.setOrientation(GridLayoutManager.VERTICAL);
        else
            x.setOrientation(GridLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(x);
    }



}