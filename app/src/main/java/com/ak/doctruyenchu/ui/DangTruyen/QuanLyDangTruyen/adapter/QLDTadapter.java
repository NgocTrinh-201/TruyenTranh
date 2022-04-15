package com.ak.doctruyenchu.ui.DangTruyen.QuanLyDangTruyen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ak.doctruyenchu.R;
import com.ak.doctruyenchu.models.TRUYEN;
import com.ak.doctruyenchu.ui.DanhSachTruyen.adapter.DanhSachTruyenAdapter;
import com.ak.doctruyenchu.ui.INTERFACE.HolderOnClick;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class QLDTadapter extends RecyclerView.Adapter {

    private ArrayList<TRUYEN> truyenArrayList;
    private QLDTadapterOnClick qldTadapterOnClick;

    public QLDTadapter(ArrayList<TRUYEN> truyenArrayList) {
        this.truyenArrayList = truyenArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_qldt_adapter,parent,false);
        ItemHolder item = new ItemHolder(view);
        return item;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ItemHolder)holder).bind(truyenArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return truyenArrayList.size();
    }

    public void onClick(QLDTadapterOnClick qldTadapterOnClick){
        this.qldTadapterOnClick=qldTadapterOnClick;
    }

    private class ItemHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView ten_truyen, tac_gia,view, luot_danh_gia;
        private RatingBar ratingBar;
        private ImageButton imageButton;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_dst_list);
            ten_truyen = itemView.findViewById(R.id.item_ten_truyen_dst);
            tac_gia = itemView.findViewById(R.id.item_tac_gia_dst);
            view = itemView.findViewById(R.id.item_view_dst);
            luot_danh_gia = itemView.findViewById(R.id.luot_rate_dst);
            ratingBar = itemView.findViewById(R.id.rate_dst);
            imageButton = itemView.findViewById(R.id.more_qldt);
        }

        public void bind(TRUYEN truyen){
            ten_truyen.setText(truyen.getTen_truyen());
            tac_gia.setText(truyen.getTac_gia());
            view.setText(" "+truyen.getSo_view()+" lượt xem");
            luot_danh_gia.setText("("+truyen.getSo_luot_rate()+" lượt)");
            ratingBar.setRating(truyen.getRate());
            Picasso.get()
                    .load(truyen.getUrl_anh_nen_truyen())
                    .into(imageView);
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    qldTadapterOnClick.onClick(v,truyenArrayList.get(getAdapterPosition()));
                }
            });
        }

    }

    public interface QLDTadapterOnClick{
        public void onClick(View view, TRUYEN truyen);
    }

}
