package com.ak.doctruyenchu.ui.moduleView.adapter;

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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ModuleView2Adapter extends RecyclerView.Adapter {

    private HolderOnClick holderOnClick;
    private OptionsOnClick optionsOnClick;
    private ArrayList<TRUYEN> truyenArrayList;
    private int textColor;
    private Context context;
    private boolean orientationVertical;
    private boolean enableOptionButton;

    public ModuleView2Adapter(ArrayList<TRUYEN> truyenArrayList) {
        this.truyenArrayList=truyenArrayList;
        this.textColor = 0;
        this.orientationVertical = false;
        this.orientationVertical = false;
    }

    public void EnableOptionButton(boolean enableOptionButton){
        this.enableOptionButton = enableOptionButton;
    }

    public void setTextColor(int textColor, Context context) {
        this.textColor = textColor;
        this.context = context;
    }

    public void setOrientationVertical(boolean orientationVertical) {
        this.orientationVertical = orientationVertical;
    }

    public void deleteItem(int position) {
        this.truyenArrayList.remove(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (orientationVertical){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_module_view2_vertical,parent,false);
            return new  ModuleView2Adapter.ItemHolderVertical(view);
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_module_view2,parent,false);
        return new  ModuleView2Adapter.ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (orientationVertical)
            ((ModuleView2Adapter.ItemHolderVertical) holder).blind(truyenArrayList.get(position));
        else
            ((ModuleView2Adapter.ItemHolder) holder).blind(truyenArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return truyenArrayList.size();
    }

    public void onClick(HolderOnClick mdv2HolderOnClick){
        this.holderOnClick=mdv2HolderOnClick;
    }

    public void setOptionsOnClick(OptionsOnClick optionsOnClick){this.optionsOnClick = optionsOnClick;}

    private class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imageView;
        private TextView textView;
        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_image_mdv2);
            textView = itemView.findViewById(R.id.item_tv_mdv2);
            itemView.setOnClickListener(this::onClick);
        }

        public void blind(TRUYEN truyen){
            Picasso.get()
                    .load(truyen.getUrl_anh_nen_truyen())
                    .fit()
                    .into(imageView);
            if (textColor!=0)
                textView.setTextColor(context.getColor(textColor));
            textView.setText(truyen.getTen_truyen());
        }

        @Override
        public void onClick(View v) {
            holderOnClick.onClick(truyenArrayList.get(getAdapterPosition()));
        }
    }

    private class ItemHolderVertical extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imageView;
        private TextView textView, tv_tac_gia, item_tv_chap_moi_mdv2;
        private RatingBar ratingBar;
        private ImageButton optionBtton;
        public ItemHolderVertical(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_image_mdv2);
            ratingBar = itemView.findViewById(R.id.ratebar_mdv2);
            textView = itemView.findViewById(R.id.item_tv_mdv2);
            tv_tac_gia = itemView.findViewById(R.id.item_tv_tac_gia_mdv2);
            item_tv_chap_moi_mdv2 = itemView.findViewById(R.id.item_tv_chap_moi_mdv2);
            optionBtton = itemView.findViewById(R.id.options_mdv2);
            itemView.setOnClickListener(this::onClick);
        }

        public void blind(TRUYEN truyen){
            Picasso.get()
                    .load(truyen.getUrl_anh_nen_truyen())
                    .fit()
                    .into(imageView);
            if (textColor!=0){
                int color = context.getColor(textColor);
                textView.setTextColor(color);
                tv_tac_gia.setTextColor(color);
                item_tv_chap_moi_mdv2.setTextColor(color);
            }
            ratingBar.setRating(truyen.getRate());
            tv_tac_gia.setText(truyen.getTac_gia());
            textView.setText(truyen.getTen_truyen());
            item_tv_chap_moi_mdv2.setText(truyen.getChap_moi_nhat());
            //
            if (enableOptionButton){
                optionBtton.setVisibility(View.VISIBLE);
            } else  optionBtton.setVisibility(View.GONE);

            optionBtton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    optionsOnClick.onClick(truyen,truyenArrayList.indexOf(truyen));
                }
            });
        }

        @Override
        public void onClick(View v) {
            holderOnClick.onClick(truyenArrayList.get(getAdapterPosition()));
        }
    }

//    public interface Mdv2HolderOnClick{
//        public void mdv2HolderOnClick(TRUYEN truyen);
//    }

}
