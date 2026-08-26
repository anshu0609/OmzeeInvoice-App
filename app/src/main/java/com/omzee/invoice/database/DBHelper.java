package com.omzee.invoice.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;

import com.omzee.invoice.model.Customer;

import java.util.ArrayList;
import androidx.annotation.Nullable;

import com.omzee.invoice.model.Product;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "OmzeeInvoice.db";
    private static final int DATABASE_VERSION = 4;

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // ================= CUSTOMER =================
        db.execSQL("CREATE TABLE customer(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT UNIQUE," +
                "address TEXT," +
                "gst TEXT," +
                "state TEXT)");

        // ================= PRODUCTS =================
        db.execSQL("CREATE TABLE products(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "description TEXT NOT NULL," +
                "hsn TEXT NOT NULL)");

        // ================= INVOICE HEADER =================
        db.execSQL("CREATE TABLE invoice(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "invoiceNo TEXT," +
                "invoiceDate TEXT," +
                "customerName TEXT," +
                "address TEXT," +
                "gst TEXT," +
                "state TEXT," +
                "subTotal REAL," +
                "cgst REAL," +
                "sgst REAL," +
                "grandTotal REAL)");

        // ================= INVOICE ITEMS =================
        db.execSQL("CREATE TABLE invoice_items(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "invoiceId INTEGER," +
                "productDescription TEXT," +
                "hsn TEXT," +
                "qty REAL," +
                "rate REAL," +
                "amount REAL)");

        insertProducts(db);
    }

    private void insertProducts(SQLiteDatabase db) {

        ContentValues cv = new ContentValues();

        cv.put("description", "FUEL ADDITIVE (ABP:1669)");
        cv.put("hsn", "3815");
        db.insert("products", null, cv);

        cv.clear();
        cv.put("description", "BOILER WATER TREATMENT CHEMICALS\nPRK-92");
        cv.put("hsn", "3815");
        db.insert("products", null, cv);

        cv.clear();
        cv.put("description", "BOILER WATER TREATMENT CHEMICALS\nOAP-78");
        cv.put("hsn", "3815");
        db.insert("products", null, cv);

        cv.clear();
        cv.put("description", "FUEL ADDITIVE\n(COAL ADDITIVE - ABP:1669)");
        cv.put("hsn", "3815");
        db.insert("products", null, cv);

        cv.clear();
        cv.put("description", "COOLING TOWER WATER TREATMENT CHEMICALS\nPNG-15");
        cv.put("hsn", "3815");
        db.insert("products", null, cv);

        cv.clear();
        cv.put("description", "COOLING TOWER WATER TREATMENT CHEMICALS\nHVP-10");
        cv.put("hsn", "3815");
        db.insert("products", null, cv);

        cv.clear();
        cv.put("description", "R.O WATER TREATMENT CHEMICALS\nDOSING CHEMICAL");
        cv.put("hsn", "3815");
        db.insert("products", null, cv);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS customer");
        db.execSQL("DROP TABLE IF EXISTS products");
        db.execSQL("DROP TABLE IF EXISTS invoice");
        db.execSQL("DROP TABLE IF EXISTS invoice_items");

        onCreate(db);
    }

    // ================= GET ALL PRODUCTS =================

    public ArrayList<String> getAllProducts() {

        ArrayList<String> productList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT description FROM products", null);

        while (cursor.moveToNext()) {
            productList.add(cursor.getString(0));
        }

        cursor.close();
        db.close();

        return productList;
    }

    // ================= GET PRODUCT =================

    public Product getProduct(String description) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM products WHERE description=?",
                new String[]{description});

        Product product = null;

        if (cursor.moveToFirst()) {

            product = new Product(
                    cursor.getString(1),
                    cursor.getString(2)
            );

        }

        cursor.close();
        db.close();

        return product;
    }

    public long insertCustomer(String name, String address, String gst, String state) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("name", name);
        cv.put("address", address);
        cv.put("gst", gst);
        cv.put("state", state);

        return db.insert("customer", null, cv);
    }
    public long insertInvoice(String invoiceNo,
                              String invoiceDate,
                              String customerName,
                              String address,
                              String gst,
                              String state,
                              double subTotal,
                              double cgst,
                              double sgst,
                              double grandTotal) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("invoiceNo", invoiceNo);
        cv.put("invoiceDate", invoiceDate);
        cv.put("customerName", customerName);
        cv.put("address", address);
        cv.put("gst", gst);
        cv.put("state", state);
        cv.put("subTotal", subTotal);
        cv.put("cgst", cgst);
        cv.put("sgst", sgst);
        cv.put("grandTotal", grandTotal);

        return db.insert("invoice", null, cv);
    }

    public long insertInvoiceItem(int invoiceId,
                                  String productDescription,
                                  String hsn,
                                  double qty,
                                  double rate,
                                  double amount) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("invoiceId", invoiceId);
        cv.put("productDescription", productDescription);
        cv.put("hsn", hsn);
        cv.put("qty", qty);
        cv.put("rate", rate);
        cv.put("amount", amount);

        return db.insert("invoice_items", null, cv);
    }

    public boolean customerExists(String name) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT id FROM customer WHERE name=?",
                new String[]{name});

        boolean exists = cursor.moveToFirst();

        cursor.close();
        db.close();

        return exists;
    }

    public ArrayList<String> getAllCustomers() {

        ArrayList<String> customers = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT name FROM customer ORDER BY name ASC",
                null
        );

        while (cursor.moveToNext()) {
            customers.add(cursor.getString(0));
        }

        cursor.close();

        return customers;
    }


    public Customer getCustomerDetails(String name) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT id, name, address, gst, state " +
                        "FROM customer WHERE name = ?",
                new String[]{name}
        );

        Customer customer = null;

        if (cursor.moveToFirst()) {

            customer = new Customer();

            customer.setId(cursor.getInt(0));
            customer.setName(cursor.getString(1));
            customer.setAddress(cursor.getString(2));
            customer.setGst(cursor.getString(3));
            customer.setState(cursor.getString(4));
        }

        cursor.close();

        return customer;
    }
}