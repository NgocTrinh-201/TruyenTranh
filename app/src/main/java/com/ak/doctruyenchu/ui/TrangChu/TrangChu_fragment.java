package com.ak.doctruyenchu.ui.TrangChu;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageSwitcher;

import com.ak.doctruyenchu.Constans.Comco;
import com.ak.doctruyenchu.Constans.Constans;
import com.ak.doctruyenchu.R;
import com.ak.doctruyenchu.models.TRUYEN;
import com.ak.doctruyenchu.ui.moduleView.Module;
import com.ak.doctruyenchu.ui.moduleView.ModuleView1;
import com.ak.doctruyenchu.ui.moduleView.ModuleView2;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TrangChu_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrangChu_fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //
    private View view;
    private SearchView searchView;
    private ModuleView2 moduleView2 = new ModuleView2();
    private ModuleView1 moduleView1 = new ModuleView1();
    private ModuleView2 moduleView3 = new ModuleView2();
    private ModuleView2 moduleView4 = new ModuleView2();

    public TrangChu_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TrangChu_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TrangChu_fragment newInstance(String param1, String param2) {
        TrangChu_fragment fragment = new TrangChu_fragment();
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
        view = inflater.inflate(R.layout.fragment_trang_chu_fragment, container, false);


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        unitView();

        setView(moduleView1,R.id.home_frg1);
        setView(moduleView2,R.id.home_frg2);
        setView(moduleView3,R.id.home_frg3);
        setView(moduleView4,R.id.home_frg4);
    }

    private void unitView(){

        moduleView1.setModule(new Module(getContext().getString(R.string.truyen_moi_nhat),false));
        Comco.layTopTruyenMoiNhat(10, new Comco.DataRecived() {
            @Override
            public void complete(ArrayList<TRUYEN> truyenArrayList) {
                moduleView1.setArray(truyenArrayList);
            }
        });


        moduleView2.setModule(new Module(getString(R.string.truyen_de_cu),2,false,R.color.black));
        Comco.laytTopTruyenDeCu(10,new Comco.DataRecived() {
            @Override
            public void complete(ArrayList<TRUYEN> truyenArrayList) {
                moduleView2.setArray(truyenArrayList);
                Log.e("Error",""+truyenArrayList.size());
            }
        });

        moduleView3.setModule(new Module(getString(R.string.truyen_top_rate),2,false,R.color.white,R.color.harbor_rat));
        Comco.laytTopRateTruyen(10,new Comco.DataRecived() {
            @Override
            public void complete(ArrayList<TRUYEN> truyenArrayList) {
                moduleView3.setArray(truyenArrayList);

            }
        });

        moduleView4.setModule(new Module(getString(R.string.truyen_top_view),1,false,R.color.black));
        Comco.laytTopViewTruyen(10,new Comco.DataRecived() {
            @Override
            public void complete(ArrayList<TRUYEN> truyenArrayList) {
                moduleView4.setArray(truyenArrayList);

            }
        });

    }
    private void setView(Fragment fragment,int id){
        getChildFragmentManager()
                .beginTransaction()
                .replace(id,fragment)
                .commit();
    }


}