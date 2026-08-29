package com.omzee.invoice;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.omzee.invoice.database.DBHelper;

public class CustomerActivity extends AppCompatActivity {

    private EditText etCustomerName;
    private EditText etCustomerGST;
    private EditText etCustomerState;
    private EditText etCustomerAddress;
    private Button btnSaveCustomer;

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        dbHelper = new DBHelper(this);

        initViews();

        btnSaveCustomer.setOnClickListener(v -> saveCustomer());
    }

    private void initViews() {

        etCustomerName = findViewById(R.id.etCustomerName);
        etCustomerGST = findViewById(R.id.etCustomerGST);
        etCustomerState = findViewById(R.id.etCustomerState);
        etCustomerAddress = findViewById(R.id.etCustomerAddress);

        btnSaveCustomer = findViewById(R.id.btnSaveCustomer);
    }

    private void saveCustomer() {

        String name = etCustomerName.getText().toString().trim();
        String gst = etCustomerGST.getText().toString().trim();
        String state = etCustomerState.getText().toString().trim();
        String address = etCustomerAddress.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            etCustomerName.setError("Enter Customer Name");
            etCustomerName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(gst)) {
            etCustomerGST.setError("Enter GSTIN");
            etCustomerGST.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(state)) {
            etCustomerState.setError("Enter State");
            etCustomerState.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(address)) {
            etCustomerAddress.setError("Enter Address");
            etCustomerAddress.requestFocus();
            return;
        }

        if (dbHelper.customerExists(name)) {
            Toast.makeText(this,
                    "Customer already exists",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        long result = dbHelper.insertCustomer(
                name,
                address,
                gst,
                state
        );

        if (result > 0) {

            Toast.makeText(this,
                    "Customer Saved Successfully",
                    Toast.LENGTH_SHORT).show();

            clearFields();

        } else {

            Toast.makeText(this,
                    "Failed to Save Customer",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void clearFields() {

        etCustomerName.setText("");
        etCustomerGST.setText("");
        etCustomerState.setText("");
        etCustomerAddress.setText("");

        etCustomerName.requestFocus();
    }
}