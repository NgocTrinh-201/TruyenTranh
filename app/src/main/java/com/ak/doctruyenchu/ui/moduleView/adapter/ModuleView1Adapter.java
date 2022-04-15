package com.ak.doctruyenchu.ui.moduleView.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ak.doctruyenchu.R;
import com.ak.doctruyenchu.models.TRUYEN;
import com.ak.doctruyenchu.ui.INTERFACE.HolderOnClick;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ModuleView1Adapter extends RecyclerView.Adapter {
    private ArrayList<TRUYEN> truyenArrayList;
    private HolderOnClick holderOnClick;
    //test


    public ModuleView1Adapter() {


    }

    public ModuleView1Adapter(ArrayList<TRUYEN> truyenArrayList) {
        this.truyenArrayList = truyenArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_module_view1,parent,false);
        return new ModuleView1Adapter.ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ItemHolder) holder).bind(truyenArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return truyenArrayList.size();
    }

    public void onClick(HolderOnClick holderOnClick){
        this.holderOnClick=holderOnClick;
    }

    private class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView imageView;
        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_item_module_view1);
            itemView.setOnClickListener(this::onClick);
        }

        public void bind(TRUYEN truyen){
            Picasso.get()
                    .load(truyen.getUrl_anh_nen_truyen())
                    .into(imageView);
        }

        @Override
        public void onClick(View v) {
            holderOnClick.onClick(truyenArrayList.get(getAdapterPosition()));
        }
    }

}
