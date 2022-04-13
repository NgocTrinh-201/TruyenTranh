package com.ak.doctruyenchu.ui.DanhSachTruyen.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ak.doctruyenchu.R;
import com.ak.doctruyenchu.models.TRUYEN;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DanhSachTruyenAdapter extends  RecyclerView.Adapter{
    private ArrayList<TRUYEN> truyenArrayList;
    private DanhSachTruyenAdapter.HolderOnClick holderOnClick;
    //test


    public DanhSachTruyenAdapter() {
    }

    public DanhSachTruyenAdapter(ArrayList<TRUYEN> truyenArrayList) {
        this.truyenArrayList = truyenArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_danh_sach_truyen,parent,false);
        return new DanhSachTruyenAdapter.ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((DanhSachTruyenAdapter.ItemHolder) holder).bind(truyenArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return truyenArrayList.size();
    }

    public void onClick(DanhSachTruyenAdapter.HolderOnClick holderOnClick){
        this.holderOnClick=holderOnClick;
    }

    private class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView imageView;
        private TextView ten_truyen, tac_gia;
        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_dst_list);
            ten_truyen = itemView.findViewById(R.id.item_ten_truyen_dst);
            tac_gia = itemView.findViewById(R.id.item_tac_gia_dst);
            itemView.setOnClickListener(this::onClick);
        }

        public void bind(TRUYEN truyen){
            ten_truyen.setText(truyen.getTen_truyen());
            tac_gia.setText(truyen.getTac_gia());
            Picasso.get()
                    .load(truyen.getUrl_anh_nen_truyen())
                    .into(imageView);
        }

        @Override
        public void onClick(View v) {
            holderOnClick.holderOnClick(truyenArrayList.get(getAdapterPosition()));
        }
    }

    public interface HolderOnClick{
        public void holderOnClick(TRUYEN truyen);
    }
}
