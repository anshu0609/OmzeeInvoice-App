package com.omzee.invoice;

import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.omzee.invoice.adapter.InvoiceHistoryAdapter;
import com.omzee.invoice.database.DBHelper;

public class InvoiceHistory extends AppCompatActivity {

    private RecyclerView rvInvoiceHistory;
    private DBHelper dbHelper;
    private InvoiceHistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_history);

        rvInvoiceHistory =
                findViewById(R.id.rvInvoiceHistory);

        dbHelper = new DBHelper(this);

        rvInvoiceHistory.setLayoutManager(
                new LinearLayoutManager(this)
        );

        loadInvoices();
    }

    private void loadInvoices() {

        Cursor cursor =
                dbHelper.getAllInvoices();

        adapter =
                new InvoiceHistoryAdapter(this,cursor);

        rvInvoiceHistory.setAdapter(adapter);
    }
}