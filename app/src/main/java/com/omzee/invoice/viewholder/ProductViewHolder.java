package com.omzee.invoice.viewholder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.omzee.invoice.R;

public class ProductViewHolder extends RecyclerView.ViewHolder {

    public TextView txtProduct;
    public TextView txtQty;
    public TextView txtRate;
    public TextView txtAmount;
    public Button btnDelete;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);

        txtProduct = itemView.findViewById(R.id.txtProduct);
        txtQty = itemView.findViewById(R.id.txtQty);
        txtRate = itemView.findViewById(R.id.txtRate);
        txtAmount = itemView.findViewById(R.id.txtAmount);
        btnDelete = itemView.findViewById(R.id.btnDelete);
    }
}