package com.omzee.invoice.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.omzee.invoice.model.Customer;
import com.omzee.invoice.model.Product;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "OmzeeInvoice.db";
    private static final int DATABASE_VERSION = 5;

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // =====================================================
    // CREATE DATABASE
    // =====================================================

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
                "hsn TEXT NOT NULL," +
                "mainProduct TEXT," +
                "subProduct TEXT)");

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

        // ================= DEFAULT PRODUCTS =================

        insertProducts(db);
    }

    // =====================================================
    // INSERT DEFAULT PRODUCTS
    // =====================================================

    private void insertProducts(SQLiteDatabase db) {

        ContentValues cv = new ContentValues();

        // -------------------------------------------------
        // FUEL ADDITIVE
        // -------------------------------------------------

        cv.put("mainProduct", "FUEL ADDITIVE");
        cv.put("subProduct", "ABP-1669");
        cv.put("description", "FUEL ADDITIVE (ABP:1669)");
        cv.put("hsn", "3815");

        db.insert("products", null, cv);

        // -------------------------------------------------
        // BOILER WATER - PRK
        // -------------------------------------------------

        cv.clear();

        cv.put(
                "mainProduct",
                "BOILER WATER TREATMENT CHEMICALS"
        );

        cv.put(
                "subProduct",
                "PRK-92"
        );

        cv.put(
                "description",
                "BOILER WATER TREATMENT CHEMICALS\nPRK-92"
        );

        cv.put("hsn", "3815");

        db.insert("products", null, cv);

        // -------------------------------------------------
        // BOILER WATER - OAP
        // -------------------------------------------------

        cv.clear();

        cv.put(
                "mainProduct",
                "BOILER WATER TREATMENT CHEMICALS"
        );

        cv.put(
                "subProduct",
                "OAP-78"
        );

        cv.put(
                "description",
                "BOILER WATER TREATMENT CHEMICALS\nOAP-78"
        );

        cv.put("hsn", "3815");

        db.insert("products", null, cv);

        // -------------------------------------------------
        // COAL ADDITIVE
        // -------------------------------------------------

        cv.clear();

        cv.put(
                "mainProduct",
                "FUEL ADDITIVE"
        );

        cv.put(
                "subProduct",
                "COAL ADDITIVE - ABP:1669"
        );

        cv.put(
                "description",
                "FUEL ADDITIVE\n(COAL ADDITIVE - ABP:1669)"
        );

        cv.put("hsn", "3815");

        db.insert("products", null, cv);

        // -------------------------------------------------
        // COOLING TOWER - PNG
        // -------------------------------------------------

        cv.clear();

        cv.put(
                "mainProduct",
                "COOLING TOWER WATER TREATMENT CHEMICALS"
        );

        cv.put(
                "subProduct",
                "PNG-15"
        );

        cv.put(
                "description",
                "COOLING TOWER WATER TREATMENT CHEMICALS\nPNG-15"
        );

        cv.put("hsn", "3815");

        db.insert("products", null, cv);

        // -------------------------------------------------
        // COOLING TOWER - HVP
        // -------------------------------------------------

        cv.clear();

        cv.put(
                "mainProduct",
                "COOLING TOWER WATER TREATMENT CHEMICALS"
        );

        cv.put(
                "subProduct",
                "HVP-10"
        );

        cv.put(
                "description",
                "COOLING TOWER WATER TREATMENT CHEMICALS\nHVP-10"
        );

        cv.put("hsn", "3815");

        db.insert("products", null, cv);

        // -------------------------------------------------
        // R.O WATER
        // -------------------------------------------------

        cv.clear();

        cv.put(
                "mainProduct",
                "R.O WATER TREATMENT CHEMICALS"
        );

        cv.put(
                "subProduct",
                "DOSING CHEMICAL"
        );

        cv.put(
                "description",
                "R.O WATER TREATMENT CHEMICALS\nDOSING CHEMICAL"
        );

        cv.put("hsn", "3815");

        db.insert("products", null, cv);
    }

    // =====================================================
    // DATABASE UPGRADE
    // =====================================================

    @Override
    public void onUpgrade(
            SQLiteDatabase db,
            int oldVersion,
            int newVersion) {

        if (oldVersion < 5) {

            // Add new columns without deleting existing data

            db.execSQL(
                    "ALTER TABLE products " +
                            "ADD COLUMN mainProduct TEXT"
            );

            db.execSQL(
                    "ALTER TABLE products " +
                            "ADD COLUMN subProduct TEXT"
            );

            // -------------------------------------------------
            // Update existing products
            // -------------------------------------------------

            db.execSQL(
                    "UPDATE products SET " +
                            "mainProduct='FUEL ADDITIVE', " +
                            "subProduct='ABP-1669' " +
                            "WHERE description='FUEL ADDITIVE (ABP:1669)'"
            );

            db.execSQL(
                    "UPDATE products SET " +
                            "mainProduct='BOILER WATER TREATMENT CHEMICALS', " +
                            "subProduct='PRK-92' " +
                            "WHERE description LIKE '%PRK-92%'"
            );

            db.execSQL(
                    "UPDATE products SET " +
                            "mainProduct='BOILER WATER TREATMENT CHEMICALS', " +
                            "subProduct='OAP-78' " +
                            "WHERE description LIKE '%OAP-78%'"
            );

            db.execSQL(
                    "UPDATE products SET " +
                            "mainProduct='FUEL ADDITIVE', " +
                            "subProduct='COAL ADDITIVE - ABP:1669' " +
                            "WHERE description LIKE '%COAL ADDITIVE%'"
            );

            db.execSQL(
                    "UPDATE products SET " +
                            "mainProduct='COOLING TOWER WATER TREATMENT CHEMICALS', " +
                            "subProduct='PNG-15' " +
                            "WHERE description LIKE '%PNG-15%'"
            );

            db.execSQL(
                    "UPDATE products SET " +
                            "mainProduct='COOLING TOWER WATER TREATMENT CHEMICALS', " +
                            "subProduct='HVP-10' " +
                            "WHERE description LIKE '%HVP-10%'"
            );

            db.execSQL(
                    "UPDATE products SET " +
                            "mainProduct='R.O WATER TREATMENT CHEMICALS', " +
                            "subProduct='DOSING CHEMICAL' " +
                            "WHERE description LIKE '%DOSING CHEMICAL%'"
            );
        }
    }

    // =====================================================
    // GET ALL PRODUCTS
    // =====================================================

    public ArrayList<String> getAllProducts() {

        ArrayList<String> productList =
                new ArrayList<>();

        SQLiteDatabase db =
                this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT description FROM products " +
                        "ORDER BY mainProduct ASC, subProduct ASC",
                null
        );

        while (cursor.moveToNext()) {

            productList.add(
                    cursor.getString(0)
            );
        }

        cursor.close();

        return productList;
    }

    // =====================================================
    // GET PRODUCT
    // =====================================================

    public Product getProduct(String description) {

        SQLiteDatabase db =
                this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM products WHERE description=?",
                new String[]{description}
        );

        Product product = null;

        if (cursor.moveToFirst()) {

            product = new Product(
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    "description"
                            )
                    ),
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    "hsn"
                            )
                    )
            );
        }

        cursor.close();

        return product;
    }

    // =====================================================
    // INSERT CUSTOMER
    // =====================================================

    public long insertCustomer(
            String name,
            String address,
            String gst,
            String state) {

        SQLiteDatabase db =
                this.getWritableDatabase();

        ContentValues cv =
                new ContentValues();

        cv.put("name", name);
        cv.put("address", address);
        cv.put("gst", gst);
        cv.put("state", state);

        return db.insert(
                "customer",
                null,
                cv
        );
    }

    // =====================================================
    // INSERT INVOICE
    // =====================================================

    public long insertInvoice(
            String invoiceNo,
            String invoiceDate,
            String customerName,
            String address,
            String gst,
            String state,
            double subTotal,
            double cgst,
            double sgst,
            double grandTotal) {

        SQLiteDatabase db =
                this.getWritableDatabase();

        ContentValues cv =
                new ContentValues();

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

        return db.insert(
                "invoice",
                null,
                cv
        );
    }

    // =====================================================
    // INSERT INVOICE ITEM
    // =====================================================

    public long insertInvoiceItem(
            int invoiceId,
            String productDescription,
            String hsn,
            double qty,
            double rate,
            double amount) {

        SQLiteDatabase db =
                this.getWritableDatabase();

        ContentValues cv =
                new ContentValues();

        cv.put("invoiceId", invoiceId);
        cv.put(
                "productDescription",
                productDescription
        );
        cv.put("hsn", hsn);
        cv.put("qty", qty);
        cv.put("rate", rate);
        cv.put("amount", amount);

        return db.insert(
                "invoice_items",
                null,
                cv
        );
    }

    // =====================================================
    // CUSTOMER EXISTS
    // =====================================================

    public boolean customerExists(String name) {

        SQLiteDatabase db =
                this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT id FROM customer WHERE name=?",
                new String[]{name}
        );

        boolean exists =
                cursor.moveToFirst();

        cursor.close();

        return exists;
    }

    // =====================================================
    // GET ALL CUSTOMERS
    // =====================================================

    public ArrayList<String> getAllCustomers() {

        ArrayList<String> customers =
                new ArrayList<>();

        SQLiteDatabase db =
                this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT name FROM customer " +
                        "ORDER BY name ASC",
                null
        );

        while (cursor.moveToNext()) {

            customers.add(
                    cursor.getString(0)
            );
        }

        cursor.close();

        return customers;
    }

    // =====================================================
    // GET CUSTOMER DETAILS
    // =====================================================

    public Customer getCustomerDetails(
            String name) {

        SQLiteDatabase db =
                this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT id, name, address, gst, state " +
                        "FROM customer WHERE name = ?",
                new String[]{name}
        );

        Customer customer = null;

        if (cursor.moveToFirst()) {

            customer = new Customer();

            customer.setId(
                    cursor.getInt(0)
            );

            customer.setName(
                    cursor.getString(1)
            );

            customer.setAddress(
                    cursor.getString(2)
            );

            customer.setGst(
                    cursor.getString(3)
            );

            customer.setState(
                    cursor.getString(4)
            );
        }

        cursor.close();

        return customer;
    }

    // =====================================================
    // GET ALL INVOICES
    // =====================================================

    public Cursor getAllInvoices() {

        SQLiteDatabase db =
                this.getReadableDatabase();

        return db.rawQuery(
                "SELECT id, invoiceNo, invoiceDate, " +
                        "customerName, grandTotal " +
                        "FROM invoice ORDER BY id DESC",
                null
        );
    }

    // =====================================================
    // GET MAIN PRODUCTS
    // =====================================================

    public ArrayList<String> getMainProducts() {

        ArrayList<String> mainProducts =
                new ArrayList<>();

        SQLiteDatabase db =
                this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT DISTINCT mainProduct " +
                        "FROM products " +
                        "WHERE mainProduct IS NOT NULL " +
                        "AND mainProduct != '' " +
                        "ORDER BY mainProduct ASC",
                null
        );

        while (cursor.moveToNext()) {

            mainProducts.add(
                    cursor.getString(0)
            );
        }

        cursor.close();

        return mainProducts;
    }

    // =====================================================
    // GET SUB PRODUCTS
    // =====================================================

    public ArrayList<String> getSubProducts(
            String mainProduct) {

        ArrayList<String> subProducts =
                new ArrayList<>();

        SQLiteDatabase db =
                this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT subProduct FROM products " +
                        "WHERE mainProduct=? " +
                        "ORDER BY subProduct ASC",
                new String[]{mainProduct}
        );

        while (cursor.moveToNext()) {

            subProducts.add(
                    cursor.getString(0)
            );
        }

        cursor.close();

        return subProducts;
    }

    // =====================================================
    // INSERT NEW PRODUCT
    // =====================================================

    public long insertProduct(
            String mainProduct,
            String subProduct,
            String hsn) {

        SQLiteDatabase db =
                this.getWritableDatabase();

        ContentValues cv =
                new ContentValues();

        String description;

        if (subProduct == null ||
                subProduct.trim().isEmpty()) {

            description = mainProduct;

        } else {

            description =
                    mainProduct +
                            "\n" +
                            subProduct;
        }

        cv.put(
                "mainProduct",
                mainProduct
        );

        cv.put(
                "subProduct",
                subProduct
        );

        cv.put(
                "description",
                description
        );

        cv.put(
                "hsn",
                hsn
        );

        return db.insert(
                "products",
                null,
                cv
        );
    }
}