package com.ak.doctruyenchu.ui.ThongTinTruyen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ak.doctruyenchu.Constans.Constans;
import com.ak.doctruyenchu.R;
import com.ak.doctruyenchu.models.BinhLuan;
import com.squareup.picasso.Picasso;


import java.util.List;

import jp.wasabeef.picasso.transformations.internal.Utils;

public class BinhLuanAdapter extends RecyclerView.Adapter {

    private List<BinhLuan> binhLuanList;

    public BinhLuanAdapter( List<BinhLuan> binhLuanList) {
        this.binhLuanList = binhLuanList;
    }

    @Override
    public int getItemCount() {
        return binhLuanList.size();
    }

    // Determines the appropriate ViewType according to the sender of the message.
    @Override
    public int getItemViewType(int position) {
        BinhLuan binhLuan = (BinhLuan) binhLuanList.get(position);

        return 0;
    }

    // Inflates the appropriate layout according to the ViewType.
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_comment, parent, false);
        return new BinhLuanHolder(view);
    }

    // Passes the message object to a ViewHolder so that the contents can be bound to UI.
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        BinhLuan binhLuan = (BinhLuan) binhLuanList.get(position);
        ((BinhLuanHolder) holder).bind(binhLuan);
    }

    private class BinhLuanHolder extends RecyclerView.ViewHolder {
        TextView content, name, dateText;
        CardView cardView;
        ImageView imageView;

        BinhLuanHolder(View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.card_comment_content);
            imageView = itemView.findViewById(R.id.image_comment);
            content = (TextView) itemView.findViewById(R.id.comment_content);
            name = (TextView) itemView.findViewById(R.id.comment_name);
            dateText = (TextView) itemView.findViewById(R.id.comment_date);
        }

        void bind(BinhLuan binhLuan) {

            if (Constans.AUTH.getCurrentUser()!=null){
                if ( binhLuan.getUid().equals(Constans.AUTH.getCurrentUser().getUid())){
                    cardView.setCardBackgroundColor(2131034145);
                    content.setTextColor(-1);
                    name.setText("Bạn");
                }
                else name.setText(binhLuan.getTen_nguoi_dung());
            }else {
                name.setText(binhLuan.getTen_nguoi_dung());
            }


            content.setText(binhLuan.getNoi_dung());
            dateText.setText(binhLuan.getNghay_dang());
            Picasso.get()
                    .load(binhLuan.getUrl_anh_dai_dien())
                    .into(imageView);
        }
    }

}
