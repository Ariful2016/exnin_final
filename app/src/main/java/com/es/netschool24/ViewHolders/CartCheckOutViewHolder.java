package com.es.netschool24.ViewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.es.netschool24.R;

public class CartCheckOutViewHolder extends RecyclerView.ViewHolder {
    public TextView first_installment_bdt,courseName_txt;
    public CartCheckOutViewHolder(@NonNull View itemView) {
        super(itemView);
        courseName_txt = itemView.findViewById(R.id.courseName_txt);
        first_installment_bdt = itemView.findViewById(R.id.first_installment_bdt);
    }
}
