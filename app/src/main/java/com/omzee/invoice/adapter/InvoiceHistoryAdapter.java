package com.omzee.invoice.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.omzee.invoice.R;

import java.io.File;

public class InvoiceHistoryAdapter
        extends RecyclerView.Adapter<InvoiceHistoryAdapter.ViewHolder> {

    private Cursor cursor;
    private Context context;

    public InvoiceHistoryAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_invoice_history, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position) {

        if (!cursor.moveToPosition(position)) {
            return;
        }

        String invoiceNo =
                cursor.getString(
                        cursor.getColumnIndexOrThrow("invoiceNo")
                );

        String invoiceDate =
                cursor.getString(
                        cursor.getColumnIndexOrThrow("invoiceDate")
                );

        String customer =
                cursor.getString(
                        cursor.getColumnIndexOrThrow("customerName")
                );

        double total =
                cursor.getDouble(
                        cursor.getColumnIndexOrThrow("grandTotal")
                );

        holder.tvInvoiceNo.setText(
                "Invoice No: " + invoiceNo
        );

        holder.tvInvoiceDate.setText(
                "Date: " + invoiceDate
        );

        holder.tvInvoiceCustomer.setText(
                "Customer: " + customer
        );

        holder.tvInvoiceTotal.setText(
                String.format("Total: ₹%.2f", total)
        );

        // CLICK INVOICE
        holder.itemView.setOnClickListener(v -> {

            File directory = new File(
                    context.getExternalFilesDir(
                            Environment.DIRECTORY_DOCUMENTS
                    ),
                    "Invoices"
            );

            File pdfFile = new File(
                    directory,
                    "OMZEE_Invoice_" + invoiceNo + ".pdf"
            );

            if (!pdfFile.exists()) {

                Toast.makeText(
                        context,
                        "PDF not found",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            Uri pdfUri = FileProvider.getUriForFile(
                    context,
                    context.getPackageName() + ".fileprovider",
                    pdfFile
            );

            Intent intent = new Intent(
                    Intent.ACTION_VIEW
            );

            intent.setDataAndType(
                    pdfUri,
                    "application/pdf"
            );

            intent.addFlags(
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
            );

            try {
                context.startActivity(intent);
            } catch (Exception e) {

                Toast.makeText(
                        context,
                        "No PDF viewer found",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public static class ViewHolder
            extends RecyclerView.ViewHolder {

        TextView tvInvoiceNo;
        TextView tvInvoiceDate;
        TextView tvInvoiceCustomer;
        TextView tvInvoiceTotal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvInvoiceNo =
                    itemView.findViewById(R.id.tvInvoiceNo);

            tvInvoiceDate =
                    itemView.findViewById(R.id.tvInvoiceDate);

            tvInvoiceCustomer =
                    itemView.findViewById(R.id.tvInvoiceCustomer);

            tvInvoiceTotal =
                    itemView.findViewById(R.id.tvInvoiceTotal);
        }
    }
}