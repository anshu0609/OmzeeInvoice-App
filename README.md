# 🧾 OmzeeInvoice -- Android Billing & Invoice Management App

**OmzeeInvoice** is an Android-based billing and invoice management
application designed for small businesses. It helps users manage
customers and products, create invoices, calculate billing amounts,
maintain invoice history, preview invoices, and generate PDF invoices.

------------------------------------------------------------------------

## ✨ Features

### 🧾 Invoice Management

-   Create new invoices
-   Add multiple products to an invoice
-   Select customers while creating invoices
-   Enter product quantities and prices
-   Automatically calculate item totals
-   Calculate invoice totals
-   Preview invoices before generating them
-   Generate invoice PDFs
-   Convert invoice amounts into words

### 👥 Customer Management

-   Add and manage customer information
-   Store customer details locally
-   Select customers while creating invoices
-   Maintain customer records for billing

### 📦 Product Management

-   Add and manage products
-   Store product information locally
-   Add multiple products to invoices
-   Manage product quantities and prices
-   Display products using a custom adapter

### 📜 Invoice History

-   View previously created invoices
-   Access invoice details
-   Maintain local invoice records using SQLite
-   Review previous billing information

### 📄 PDF Generation

-   Generate invoice PDFs
-   Include customer and product information
-   Display calculated invoice totals
-   Convert total amounts into words

------------------------------------------------------------------------

## 🛠️ Technologies Used

  Technology             Purpose
  ---------------------- -----------------------------
  **Java**               Application development
  **Android**            Mobile application platform
  **XML**                User interface design
  **SQLite**             Local database
  **Gradle**             Build system
  **Android PDF APIs**   PDF invoice generation
  **RecyclerView**       Displaying lists
  **Git & GitHub**       Version control

------------------------------------------------------------------------

## 🏗️ Application Architecture

The application is organized into separate components for screens, data
models, database operations, adapters, and utility functions.

``` text
Activities
    │
    ├── MainActivity
    ├── CustomerActivity
    ├── NewInvoiceActivity
    ├── PreviewActivity
    └── InvoiceHistoryActivity
            │
            ▼
       Model Classes
            │
            ├── Customer
            ├── Product
            ├── Invoice
            └── InvoiceItem
            │
            ▼
       Database Layer
            │
            └── DBHelper
            │
            ▼
       Utility Layer
            │
            ├── PdfGenerator
            └── NumberToWords
```

### Main Components

-   **Activities** -- Handle screens, navigation, and user interaction.
-   **Models** -- Represent customers, products, invoices, and invoice
    items.
-   **Database Layer** -- Uses SQLite for local data storage and CRUD
    operations.
-   **Adapter** -- Displays product-related information in lists.
-   **Utility Classes** -- Handle PDF generation and number-to-words
    conversion.

------------------------------------------------------------------------

## 📂 Project Structure

``` text
OmzeeInvoice/
│
├── app/
│   └── src/
│       └── main/
│           ├── java/com/omzee/invoice/
│           │   ├── MainActivity.java
│           │   ├── CustomerActivity.java
│           │   ├── HistoryActivity.java
│           │   ├── InvoiceHistoryActivity.java
│           │   ├── NewInvoiceActivity.java
│           │   ├── PreviewActivity.java
│           │   │
│           │   ├── adapter/
│           │   │   └── ProductAdapter.java
│           │   │
│           │   ├── database/
│           │   │   └── DBHelper.java
│           │   │
│           │   ├── interfaces/
│           │   │   └── CalculationListener.java
│           │   │
│           │   ├── model/
│           │   │   ├── Customer.java
│           │   │   ├── Invoice.java
│           │   │   ├── InvoiceItem.java
│           │   │   └── Product.java
│           │   │
│           │   └── utils/
│           │       ├── NumberToWords.java
│           │       └── PdfGenerator.java
│           │
│           └── res/
│               ├── layout/
│               ├── drawable/
│               ├── mipmap/
│               ├── values/
│               └── xml/
│
├── gradle/
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
├── gradlew
├── gradlew.bat
└── README.md
```

------------------------------------------------------------------------

## 🔄 Application Workflow

``` text
Start Application
       │
       ▼
   Main Screen
       │
       ├───────────────┐
       ▼               ▼
 Customers          Invoices
       │               │
       ▼               ▼
Manage Customers   Create Invoice
                       │
                       ▼
                Select Customer
                       │
                       ▼
                  Add Products
                       │
                       ▼
                Enter Quantity
                       │
                       ▼
              Calculate Invoice
                       │
                       ▼
                 Preview Invoice
                       │
                       ▼
                  Generate PDF
                       │
                       ▼
                Invoice History
```

------------------------------------------------------------------------

## 🗄️ Database

OmzeeInvoice uses **SQLite** for local data storage.

The database manages information related to:

-   Customers
-   Products
-   Invoices
-   Invoice Items

This allows billing information to be maintained locally on the Android
device.

------------------------------------------------------------------------

## ⚙️ Setup & Installation

### 1. Clone the Repository

``` bash
git clone https://github.com/anshu0609/OmzeeInvoice-App.git
```

### 2. Open the Project

Open the cloned `OmzeeInvoice-App` folder in Android Studio and allow
Gradle to synchronize the project.

### 3. Build the Project

**Windows:**

``` bash
gradlew.bat build
```

**Linux/macOS:**

``` bash
./gradlew build
```

### 4. Generate APK

To generate a debug APK:

**Windows:**

``` bash
gradlew.bat assembleDebug
```

**Linux/macOS:**

``` bash
./gradlew assembleDebug
```

The APK will be generated at:

``` text
app/build/outputs/apk/debug/app-debug.apk
```

### 5. Run the Application

The application can be run using:

-   Android Emulator
-   Physical Android device with USB debugging enabled

------------------------------------------------------------------------

## 📱 Application Screens

The application includes screens for:

-   🏠 Main Dashboard
-   👥 Customer Management
-   📦 Product Management
-   🧾 New Invoice
-   👁️ Invoice Preview
-   📜 Invoice History
-   📄 PDF Invoice Generation

------------------------------------------------------------------------

## 🎯 Project Objective

The objective of OmzeeInvoice is to provide a simple and efficient
**mobile billing solution for small businesses**.

The application combines customer management, product management,
invoice creation, automated calculations, invoice history, and PDF
generation into a single Android application.

------------------------------------------------------------------------

## 🚀 Key Highlights

-   📱 Android-based billing application
-   ☕ Developed using Java
-   🗄️ SQLite local database
-   🧮 Automated invoice calculations
-   📄 PDF invoice generation
-   🔢 Number-to-words conversion
-   👥 Customer management
-   📦 Product management
-   📜 Invoice history
-   🧩 Modular project structure
-   🔧 Gradle-based build system
-   🔗 GitHub version control

------------------------------------------------------------------------

## 🔮 Future Improvements

Possible future enhancements include:

-   Cloud database synchronization
-   User authentication
-   Business profile management
-   GST/tax calculation
-   Multiple invoice templates
-   Invoice sharing through WhatsApp or email
-   Online backup and restore
-   Sales dashboard and statistics
-   Search and filtering for invoices
-   Export invoice history to Excel/CSV

------------------------------------------------------------------------

## 👨‍💻 Author

### Anshu Jha

GitHub:\
https://github.com/anshu0609

------------------------------------------------------------------------

## ⭐ Repository

GitHub Repository:

https://github.com/anshu0609/OmzeeInvoice-App

If you find this project useful, consider giving the repository a ⭐
star.
