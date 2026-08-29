package com.omzee.invoice;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.TextView;

import android.app.DatePickerDialog;
import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;

import androidx.core.content.FileProvider;

import com.omzee.invoice.adapter.ProductAdapter;
import com.omzee.invoice.database.DBHelper;
import com.omzee.invoice.model.InvoiceItem;
import com.omzee.invoice.interfaces.CalculationListener;
import com.omzee.invoice.model.Customer;
import com.omzee.invoice.utils.NumberToWords;
import com.omzee.invoice.utils.PdfGenerator;

import java.io.File;
import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class NewInvoiceActivity extends AppCompatActivity implements CalculationListener {

    // Views
    private EditText etInvoiceNo, etDate, etGST, etState, etAddress, etQty, etRate;
    private AutoCompleteTextView actBuyer;
    private Spinner spProduct;
    private Button btnAddProduct, btnGeneratePdf;
    private RecyclerView rvProducts;
    private TextView txtSubTotal, txtCGST, txtSGST, txtGrandTotal, txtAmountWords;

    // Database
    private DBHelper dbHelper;

    // Product List
    private ArrayList<InvoiceItem> invoiceItems;
    private ProductAdapter productAdapter;
    private double subTotal = 0;
    private double cgst = 0;
    private double sgst = 0;
    private double grandTotal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_invoice);

        initViews();

        dbHelper = new DBHelper(this);

        invoiceItems = new ArrayList<>();

        productAdapter = new ProductAdapter(invoiceItems,this);

        rvProducts.setLayoutManager(new LinearLayoutManager(this));
        rvProducts.setAdapter(productAdapter);

        loadProducts();

        setTodayDate();

        etDate.setOnClickListener(v -> showDatePicker());

        loadCustomers();
        btnAddProduct.setOnClickListener(v -> addProduct());
        btnGeneratePdf.setOnClickListener(v -> saveInvoice());
    }

    private void initViews() {

        etInvoiceNo = findViewById(R.id.etInvoiceNo);
        etDate = findViewById(R.id.etDate);

        actBuyer = findViewById(R.id.actBuyer);

        etGST = findViewById(R.id.etGST);
        etState = findViewById(R.id.etState);
        etAddress = findViewById(R.id.etAddress);

        spProduct = findViewById(R.id.spProduct);

        etQty = findViewById(R.id.etQty);
        etRate = findViewById(R.id.etRate);

        btnAddProduct = findViewById(R.id.btnAddProduct);
        btnGeneratePdf = findViewById(R.id.btnGeneratePdf);

        rvProducts = findViewById(R.id.rvProducts);

        txtSubTotal = findViewById(R.id.txtSubTotal);
        txtCGST = findViewById(R.id.txtCGST);
        txtSGST = findViewById(R.id.txtSGST);
        txtGrandTotal = findViewById(R.id.txtGrandTotal);
        txtAmountWords = findViewById(R.id.txtAmountWords);
    }

    private void loadProducts() {

        ArrayList<String> products = dbHelper.getAllProducts();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                products
        );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spProduct.setAdapter(adapter);
    }

    private void setTodayDate() {

        String today = new SimpleDateFormat(
                "dd/MM/yyyy",
                Locale.getDefault()
        ).format(new Date());

        etDate.setText(today);
    }

    private void showDatePicker() {

        Calendar calendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog =
                new DatePickerDialog(
                        this,
                        (view, year, month, dayOfMonth) -> {

                            String selectedDate =
                                    String.format(
                                            Locale.getDefault(),
                                            "%02d/%02d/%04d",
                                            dayOfMonth,
                                            month + 1,
                                            year
                                    );

                            etDate.setText(selectedDate);
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );

        datePickerDialog.show();
    }

    @Override
    public void onCalculationChanged() {
        calculateTotals();
    }

    @Override
    public void onItemDeleted() {
        calculateTotals();
    }

    private void addProduct() {

        String description = spProduct.getSelectedItem().toString();

        String qtyText = etQty.getText().toString().trim();
        String rateText = etRate.getText().toString().trim();

        if (qtyText.isEmpty()) {
            etQty.setError("Enter Quantity");
            return;
        }

        if (rateText.isEmpty()) {
            etRate.setError("Enter Rate");
            return;
        }

        double qty = Double.parseDouble(qtyText);
        double rate = Double.parseDouble(rateText);
        double amount = qty * rate;

        InvoiceItem item = new InvoiceItem(
                description,
                "3815",
                qty,
                rate,
                amount
        );

        invoiceItems.add(item);

        productAdapter.notifyItemInserted(invoiceItems.size() - 1);

        calculateTotals();

        etQty.setText("");
        etRate.setText("");

        spProduct.requestFocus();
    }
    private void calculateTotals() {

        subTotal = 0;

        for (InvoiceItem item : invoiceItems) {
            subTotal += item.getAmount();
        }

        cgst = subTotal * 0.09;
        sgst = subTotal * 0.09;
        grandTotal = subTotal + cgst + sgst;

        txtSubTotal.setText(
                String.format(
                        Locale.getDefault(),
                        "Sub Total : ₹ %.2f",
                        subTotal
                )
        );

        txtCGST.setText(
                String.format(
                        Locale.getDefault(),
                        "CGST (9%%) : ₹ %.2f",
                        cgst
                )
        );

        txtSGST.setText(
                String.format(
                        Locale.getDefault(),
                        "SGST (9%%) : ₹ %.2f",
                        sgst
                )
        );

        txtGrandTotal.setText(
                String.format(
                        Locale.getDefault(),
                        "Grand Total : ₹ %.2f",
                        grandTotal
                )
        );

        // Amount in words - rounded Grand Total
        long roundedTotal = Math.round(grandTotal);

        txtAmountWords.setText(
                "Amount in Words : "
                        + NumberToWords.convert(roundedTotal)
        );
    }

    private void saveInvoice() {

        String invoiceNo = etInvoiceNo.getText().toString().trim();
        String invoiceDate = etDate.getText().toString().trim();
        String customerName = actBuyer.getText().toString().trim();
        String gst = etGST.getText().toString().trim();
        String state = etState.getText().toString().trim();
        String address = etAddress.getText().toString().trim();

        // ================= VALIDATION =================

        if (invoiceNo.isEmpty()) {
            etInvoiceNo.setError("Enter Invoice Number");
            etInvoiceNo.requestFocus();
            return;
        }

        if (customerName.isEmpty()) {
            actBuyer.setError("Select Buyer");
            actBuyer.requestFocus();
            return;
        }

        if (invoiceItems.isEmpty()) {
            Toast.makeText(
                    this,
                    "Add at least one product",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        Customer customer =
                dbHelper.getCustomerDetails(customerName);

        if (customer == null) {
            Toast.makeText(
                    this,
                    "Please select a saved customer",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        // ================= SAVE INVOICE =================

        long invoiceId = dbHelper.insertInvoice(
                invoiceNo,
                invoiceDate,
                customerName,
                address,
                gst,
                state,
                subTotal,
                cgst,
                sgst,
                grandTotal
        );

        if (invoiceId == -1) {
            Toast.makeText(
                    this,
                    "Failed to save invoice",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        // ================= SAVE PRODUCTS =================

        for (InvoiceItem item : invoiceItems) {

            dbHelper.insertInvoiceItem(
                    (int) invoiceId,
                    item.getProductDescription(),
                    item.getHsn(),
                    item.getQuantity(),
                    item.getRate(),
                    item.getAmount()
            );
        }

        // ================= GENERATE PDF =================

        try {

            File pdfFile = PdfGenerator.generateTestPdf(
                    this,
                    invoiceNo,
                    invoiceDate,
                    customer,
                    invoiceItems,
                    subTotal,
                    cgst,
                    sgst,
                    grandTotal
            );

            Toast.makeText(
                    this,
                    "PDF Generated Successfully\n" +
                            pdfFile.getName(),
                    Toast.LENGTH_LONG
            ).show();

            openPdf(pdfFile);

        } catch (IOException e) {

            e.printStackTrace();

            Toast.makeText(
                    this,
                    "PDF Error: " + e.getMessage(),
                    Toast.LENGTH_LONG
            ).show();
        }
    }
    private void loadCustomers() {

        ArrayList<String> customers = dbHelper.getAllCustomers();

        ArrayAdapter<String> customerAdapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_dropdown_item_1line,
                        customers
                );

        actBuyer.setAdapter(customerAdapter);

        // Start suggestions after typing 1 character
        actBuyer.setThreshold(1);

        actBuyer.setOnItemClickListener((parent, view, position, id) -> {

            String selectedCustomer =
                    parent.getItemAtPosition(position).toString();

            Customer customer =
                    dbHelper.getCustomerDetails(selectedCustomer);

            if (customer != null) {

                etGST.setText(customer.getGst());
                etState.setText(customer.getState());
                etAddress.setText(customer.getAddress());
            }
        });
    }

    private void openPdf(File pdfFile) {

        Uri pdfUri = FileProvider.getUriForFile(
                this,
                getPackageName() + ".fileprovider",
                pdfFile
        );

        Intent intent = new Intent(Intent.ACTION_VIEW);

        intent.setDataAndType(pdfUri, "application/pdf");

        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        try {
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(
                    this,
                    "No PDF viewer found",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}