package com.ak.doctruyenchu.ui;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.ak.doctruyenchu.Constans.Constans;
import com.ak.doctruyenchu.R;
import com.ak.doctruyenchu.models.TRUYEN;
import com.ak.doctruyenchu.ui.TuTruyen.TuTruyen;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.squareup.picasso.Picasso;

public class BottomSheet extends Fragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.bottom_sheet_tu_truyen,container,false);

        String ten_truyen = getArguments().getString(Constans.TEN_TRUYEN_KEY);
        String url = getArguments().getString(Constans.URL_ANH_NEN_TRUYEN_KEY);

        TextView textView = view.findViewById(R.id.ten_truyen_bottom_sheet);
        ImageView imageView = view.findViewById(R.id.image_boottom_sheet);
        CardView cardView = view.findViewById(R.id.bt_delete_bottom_sheet);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constans.DATABASE.getReference(Constans.LICH_SU).child(Constans.AUTH.getCurrentUser().getUid()).child(ten_truyen)
                        .removeValue()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Bundle bundle= new Bundle();
                                bundle.putString("result","1");
                                getParentFragmentManager().setFragmentResult("result",bundle);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Bundle bundle= new Bundle();
                                bundle.putString("result","0");
                                getParentFragmentManager().setFragmentResult("result",bundle);
                            }
                        });
            }
        });

        textView.setText(ten_truyen);
        Picasso.get()
                .load(url)
                .into(imageView);


        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
