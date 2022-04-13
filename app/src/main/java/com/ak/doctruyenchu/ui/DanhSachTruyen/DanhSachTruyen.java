package com.ak.doctruyenchu.ui.DanhSachTruyen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.ak.doctruyenchu.Constans.Comco;
import com.ak.doctruyenchu.Constans.Constans;
import com.ak.doctruyenchu.R;
import com.ak.doctruyenchu.models.TRUYEN;
import com.ak.doctruyenchu.ui.DanhSachTruyen.adapter.DanhSachTruyenAdapter;

import com.ak.doctruyenchu.ui.ThongTinTruyen.ThongTinTruyen;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

public class DanhSachTruyen extends AppCompatActivity {

    private MaterialToolbar toolbar;

    private RecyclerView recyclerView;
    private DanhSachTruyenAdapter adapter;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_truyen);
        intent = getIntent();
        toolbar = findViewById(R.id.dst_toolbar);
        toolbar.setTitle(intent.getStringExtra(Constans.KEY));
        setSupportActionBar(toolbar);
        unitUI();
        a();
    }
    private void event() {
        adapter.onClick(new DanhSachTruyenAdapter.HolderOnClick() {
            @Override
            public void holderOnClick(TRUYEN truyen) {
                Intent intent = new Intent(DanhSachTruyen.this, ThongTinTruyen.class);
                intent.putExtra("ten_truyen",truyen.getTen_truyen());
                startActivity(intent);
            }
        });
    }

    void a(){
        switch (intent.getStringExtra(Constans.KEY)){
            case Constans.TRUYEN_DE_CU:
                new Comco().laytTopTruyenDeCu(0, new Comco.DataRecived() {
                    @Override
                    public void complete(ArrayList<TRUYEN> truyenArrayList) {
                        run(truyenArrayList);
                    }
                });
                break;
            case Constans.TRUYEN_MOI_NHAT:
                new Comco().layTopTruyenMoiNhat(0, new Comco.DataRecived() {
                    @Override
                    public void complete(ArrayList<TRUYEN> truyenArrayList) {
                        run(truyenArrayList);
                    }
                });
                break;
            case Constans.TRUYEN_CUNG_TAC_GIA:
                new Comco().layTruyenCungTacGia(0, intent.getStringExtra(Constans.TAC_GIA_KEY), "", new Comco.DataRecived() {
                    @Override
                    public void complete(ArrayList<TRUYEN> truyenArrayList) {
                        run(truyenArrayList);
                    }
                });
                break;
            case Constans.LICH_SU_DOC_TRUYEN:
                Comco.layLichSuDocTruyen(new Comco.DataRecived() {
                    @Override
                    public void complete(ArrayList<TRUYEN> truyenArrayList) {
                        run(truyenArrayList);
                    }
                });
                break;

            case Constans.TRUYEN_CAT_GIU:
                Comco.layLichTuTruyen(new Comco.DataRecived() {
                    @Override
                    public void complete(ArrayList<TRUYEN> truyenArrayList) {
                        run(truyenArrayList);
                    }
                });
                break;

            case Constans.TRUYEN_TOP_RATE:
                Comco.laytTopRateTruyen(0, new Comco.DataRecived() {
                    @Override
                    public void complete(ArrayList<TRUYEN> truyenArrayList) {
                        run(truyenArrayList);
                    }
                });
                break;
            case Constans.TRUYEN_TOP_VIEW:
                Comco.laytTopViewTruyen(0, new Comco.DataRecived() {
                    @Override
                    public void complete(ArrayList<TRUYEN> truyenArrayList) {
                        run(truyenArrayList);
                    }
                });
                break;

        }
    }

    private void run(ArrayList<TRUYEN> truyenArrayList){
        adapter = new DanhSachTruyenAdapter(truyenArrayList);
        recyclerView.setAdapter(adapter);
        event();
    }

    private void unitUI(){
        recyclerView = findViewById(R.id.rcv_danh_sach_truyen);
        recyclerView.setLayoutManager(new LinearLayoutManager(DanhSachTruyen.this));
    }

}