package com.ak.doctruyenchu.ui.TuTruyen;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.ak.doctruyenchu.Constans.Comco;
import com.ak.doctruyenchu.Constans.Constans;
import com.ak.doctruyenchu.R;
import com.ak.doctruyenchu.models.TRUYEN;
import com.ak.doctruyenchu.ui.BottomSheet;
import com.ak.doctruyenchu.ui.CaNhan.ChuaDangNhap;
import com.ak.doctruyenchu.ui.moduleView.Module;
import com.ak.doctruyenchu.ui.moduleView.ModuleView1;
import com.ak.doctruyenchu.ui.moduleView.ModuleView2;
import com.ak.doctruyenchu.ui.moduleView.adapter.OptionsOnClick;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TuTruyen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TuTruyen extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View view;
    private ModuleView2 moduleView2 = new ModuleView2();
    private boolean clickOption;
    private int positions;
    private ModuleView2 m4 = new ModuleView2();

    public TuTruyen() {
        // Required empty public constructor
        clickOption = false;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ThongBao_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TuTruyen newInstance(String param1, String param2) {
        TuTruyen fragment = new TuTruyen();
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
        view = inflater.inflate(R.layout.tu_truyen_fragment, container, false);


        return view;
    }



    private void setView(Fragment fragment, int id){
        Fragment fragment1;
        if ((fragment1=getParentFragmentManager().findFragmentById(id))!=null){
            getParentFragmentManager().beginTransaction()
                    .remove(fragment1)
                    .commit();
        }
        getChildFragmentManager()
                .beginTransaction()
                .replace(id,fragment)
                .commit();
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();

        runA();
        if (Constans.AUTH.getCurrentUser()!=null){
            setView(moduleView2,R.id.fr1);
        }
        setView(m4,R.id.fr4);

        endBottomSheet();

    }

    private void runA() {
        AppBarLayout appBarLayout = view.findViewById(R.id.appbar_tu_truyen);
        NestedScrollView nestedScrollView = view.findViewById(R.id.bottom_sheet_tu_truyen);
        CoordinatorLayout coordinatorLayout = view.findViewById(R.id.coordinator_layout_tu_truyen);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(nestedScrollView);
        m4.setEventForOptionOnClick(new OptionsOnClick() {
            @Override
            public void onClick(TRUYEN truyen, int position) {

                if (appBarLayout.getTop() >= 0)
                    appBarLayout.setExpanded(false);

                if (!clickOption){
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    clickOption=true;
                    Fragment fragment = new BottomSheet();
                    Bundle bundle = new Bundle();
                    bundle.putString(Constans.TEN_TRUYEN_KEY,truyen.getTen_truyen());
                    bundle.putString(Constans.URL_ANH_NEN_TRUYEN_KEY,truyen.getUrl_anh_nen_truyen());
                    fragment.setArguments(bundle);
                    positions = position;
                    getParentFragmentManager().beginTransaction()
                            .replace(R.id.bottom_sheet_frg_tu_truyen,fragment)
                            .commit();

//                    Module md = new Module(getString(R.string.truyen_de_cu),2,false);
//                    ModuleView1 dCu = new ModuleView1();
//                    dCu.setModule(md);
//
//                    Comco.laytTopTruyenDeCu(10, new Comco.DataRecived() {
//                        @Override
//                        public void complete(ArrayList<TRUYEN> truyenArrayList) {
//                            dCu.setArray(truyenArrayList);
//                            getParentFragmentManager().beginTransaction()
//                                    .replace(R.id.frg_truyen_moi_tu_truyen,dCu)
//                                    .commit();
//                        }
//                    });

                }else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                    clickOption=false;
                    endBottomSheet();
                }
            }
        });

        getParentFragmentManager().setFragmentResultListener("result", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                if (Integer.parseInt(result.getString(requestKey))==1){
                    m4.notifyAdapterDelete(positions);
                    endBottomSheet();
                }else
                    Toast.makeText(getContext(),"That bai", Toast.LENGTH_SHORT).show();
            }
        });


        if (Constans.AUTH.getCurrentUser()!=null){
            Module module = new Module(getContext().getString(R.string.truyen_cat_giu),2,false);
            moduleView2.setModule(module);
            Comco.layLichTuTruyen(new Comco.DataRecived() {
                @Override
                public void complete(ArrayList<TRUYEN> truyenArrayList) {
                    moduleView2.setArray(truyenArrayList);
                }
            });

            Module module1 = new Module(Constans.LICH_SU_DOC_TRUYEN,1,true);
            m4.setModule(module1);
            Comco.layLichSuDocTruyen(new Comco.DataRecived() {
                @Override
                public void complete(ArrayList<TRUYEN> truyenArrayList) {
                    module1.enableOptionButton();
                    m4.setArray(truyenArrayList);

                }
            });
        }else {

            setView(new ChuaDangNhap(),R.id.fr1);

            Module module = new Module(getString(R.string.truyen_moi_nhat),1,true);
            m4.setModule(module);
            Comco.layTopTruyenMoiNhat(0, new Comco.DataRecived() {
                @Override
                public void complete(ArrayList<TRUYEN> truyenArrayList) {
                    moduleView2.setArray(truyenArrayList);
                }
            });
        }

    }

    private void endBottomSheet() {
        Fragment fragment = getParentFragmentManager().findFragmentById(R.id.bottom_sheet_frg_tu_truyen);
        if (fragment!=null){
            clickOption=false;
            getParentFragmentManager().beginTransaction()
                    .remove(fragment)
                    .commit();
        }

        Fragment fr = getParentFragmentManager().findFragmentById(R.id.frg_truyen_moi_tu_truyen);
        if (fr!=null){
            clickOption=false;
            getParentFragmentManager().beginTransaction()
                    .remove(fr)
                    .commit();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}