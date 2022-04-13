package com.ak.doctruyenchu.ui.ThongTinTruyen.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ak.doctruyenchu.R;
import com.ak.doctruyenchu.models.CHUONG;


import java.util.ArrayList;

public class DanhSachChuongAdapter extends  RecyclerView.Adapter{
    private ArrayList<CHUONG> chuongArrayList;
    private HolderOnClick holderOnClick;

    public DanhSachChuongAdapter() {
    }

    public DanhSachChuongAdapter(ArrayList<CHUONG> truyenArrayList) {
        this.chuongArrayList = truyenArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_danh_sach_chuong,parent,false);
        return new DanhSachChuongAdapter.ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((DanhSachChuongAdapter.ItemHolder) holder).bind(chuongArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return chuongArrayList.size();
    }

    public void onClick(HolderOnClick holderOnClick){
        this.holderOnClick=holderOnClick;
    }

    private class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView ten_chuong, ngay_cap_nhat;
        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            ten_chuong = itemView.findViewById(R.id.item_dsc_ten_chuong);
            ngay_cap_nhat = itemView.findViewById(R.id.item_dsc_ngay_cap_nhat);
            itemView.setOnClickListener(this::onClick);
        }

        public void bind(CHUONG chuong){
            ten_chuong.setText(chuong.getTen_chuoong());
            ngay_cap_nhat.setText(chuong.getNgay_cap_nhat());
        }

        @Override
        public void onClick(View v) {
            holderOnClick.holderOnClick(chuongArrayList.get(getAdapterPosition()));
        }
    }

    public interface HolderOnClick{
        public void holderOnClick(CHUONG chuong);
    }
}
