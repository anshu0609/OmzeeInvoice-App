package com.omzee.invoice;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;import android.content.Intent;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnNew, btnHistory;
    Button btnProductMaster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNew = findViewById(R.id.btnNew);
        btnHistory = findViewById(R.id.btnHistory);


        btnNew.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this,
                    NewInvoiceActivity.class));
        });

        btnHistory.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this,
                    InvoiceHistory.class));
        });

        Button btnCustomers = findViewById(R.id.btnCustomers);

        btnCustomers.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CustomerActivity.class);
            startActivity(intent);
        });

        btnProductMaster = findViewById(R.id.btnProductMaster);

        btnProductMaster.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    ProductActivity.class
            );

            startActivity(intent);
        });
    }
}