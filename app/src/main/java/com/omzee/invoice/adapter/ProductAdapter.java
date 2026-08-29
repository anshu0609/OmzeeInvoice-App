package com.omzee.invoice.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.omzee.invoice.R;
import com.omzee.invoice.interfaces.CalculationListener;
import com.omzee.invoice.model.InvoiceItem;
import com.omzee.invoice.viewholder.ProductViewHolder;

import java.util.ArrayList;
import java.util.Locale;

public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder> {

    private final ArrayList<InvoiceItem> itemList;
    private final CalculationListener listener;

    public ProductAdapter(ArrayList<InvoiceItem> itemList,
                          CalculationListener listener) {
        this.itemList = itemList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_invoice_product, parent, false);

        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

        InvoiceItem item = itemList.get(position);

        holder.txtProduct.setText(item.getProductDescription());
        holder.txtQty.setText("Qty : " + item.getQuantity() + " KG");
        holder.txtRate.setText("Rate : ₹ " + item.getRate());
        holder.txtAmount.setText(
                String.format(Locale.getDefault(),
                        "Amount : ₹ %.2f",
                        item.getAmount())
        );

        holder.btnDelete.setOnClickListener(v -> {

            int adapterPosition = holder.getBindingAdapterPosition();

            if (adapterPosition != RecyclerView.NO_POSITION) {

                itemList.remove(adapterPosition);
                notifyItemRemoved(adapterPosition);

                listener.onItemDeleted();
            }

        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}