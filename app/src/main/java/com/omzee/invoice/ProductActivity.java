package com.omzee.invoice;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.omzee.invoice.database.DBHelper;

public class ProductActivity extends AppCompatActivity {

    private EditText etMainProduct;
    private EditText etSubProduct;
    private EditText etHSN;
    private Button btnSaveProduct;

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        // ================= VIEWS =================

        etMainProduct = findViewById(R.id.etMainProduct);
        etSubProduct = findViewById(R.id.etSubProduct);
        etHSN = findViewById(R.id.etHSN);
        btnSaveProduct = findViewById(R.id.btnSaveProduct);

        // ================= DATABASE =================

        dbHelper = new DBHelper(this);

        // ================= SAVE =================

        btnSaveProduct.setOnClickListener(v -> saveProduct());
    }

    private void saveProduct() {

        String mainProduct =
                etMainProduct.getText().toString().trim();

        String subProduct =
                etSubProduct.getText().toString().trim();

        String hsn =
                etHSN.getText().toString().trim();

        // ================= VALIDATION =================

        if (mainProduct.isEmpty()) {
            etMainProduct.setError("Enter Main Product");
            etMainProduct.requestFocus();
            return;
        }

        if (hsn.isEmpty()) {
            etHSN.setError("Enter HSN Code");
            etHSN.requestFocus();
            return;
        }

        // ================= INSERT =================

        long result =
                dbHelper.insertProduct(
                        mainProduct,
                        subProduct,
                        hsn
                );

        if (result == -1) {

            Toast.makeText(
                    this,
                    "Failed to save product",
                    Toast.LENGTH_SHORT
            ).show();

        } else {

            Toast.makeText(
                    this,
                    "Product Saved Successfully",
                    Toast.LENGTH_SHORT
            ).show();

            // Clear fields after successful save
            etMainProduct.setText("");
            etSubProduct.setText("");
            etHSN.setText("");

            etMainProduct.requestFocus();
        }
    }
}