# 🧾 OmzeeInvoice – Android Billing & Invoice Management App

**OmzeeInvoice** is an Android application designed to simplify **customer, product, and invoice management** for small businesses. The application allows users to create invoices, manage products and customers, calculate billing amounts, and generate invoice previews/PDFs.

## ✨ Features

### 📋 Invoice Management

* Create new invoices
* Add multiple products to an invoice
* Automatically calculate item totals
* Calculate invoice totals
* Preview generated invoices
* Generate invoice PDFs
* Convert invoice amounts into words

### 👥 Customer Management

* Add and manage customer information
* Select customers while creating invoices
* Maintain customer details for billing

### 📦 Product Management

* Add products to invoices
* Manage product information
* Display products using a custom adapter
* Calculate product quantities and prices

### 📜 Invoice History

* View previously created invoices
* Access invoice details
* Maintain local invoice records

## 🛠️ Technologies Used

* **Language:** Java
* **Platform:** Android
* **IDE:** Android Studio
* **Build System:** Gradle
* **Database:** SQLite
* **UI:** XML
* **PDF Generation:** Android PDF APIs
* **Version Control:** Git & GitHub

## 📂 Project Structure

```text
OmzeeInvoice/
│
├── app/
│   └── src/
│       └── main/
│           ├── java/com/omzee/invoice/
│           │   ├── MainActivity.java
│           │   ├── CustomerActivity.java
│           │   ├── HistoryActivity.java
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

## 🏗️ Application Architecture

The application follows a structured approach separating:

* **Activities** – Handle application screens and user interaction
* **Models** – Represent customers, products, invoices, and invoice items
* **Database Layer** – Handles local SQLite database operations
* **Adapters** – Display product and invoice-related data
* **Utilities** – Handle PDF generation and number-to-words conversion

## ⚙️ Setup & Installation

### 1. Clone the Repository

```bash
git clone https://github.com/anshu0609/OmzeeInvoice-App.git
```

### 2. Open in Android Studio

Open the cloned `OmzeeInvoice-App` folder in Android Studio and allow Gradle to sync the project.

### 3. Build the Project

Use Android Studio to build the project or run:

```bash
./gradlew build
```

On Windows:

```bash
gradlew.bat build
```

### 4. Run the Application

Connect an Android device with USB debugging enabled or start an Android Emulator.

Then run the application from Android Studio.

## 🎯 Project Objective

The objective of OmzeeInvoice is to provide a simple mobile billing solution that helps businesses manage **customers, products, invoices, invoice history, calculations, and PDF invoice generation** from a single Android application.

## 📌 Key Highlights

* 📱 Android-based billing solution
* 🧮 Automated invoice calculations
* 🗄️ Local SQLite database
* 📄 PDF invoice generation
* 🔢 Number-to-words conversion
* 👥 Customer and product management
* 📜 Invoice history

## 👨‍💻 Author

**Anshu Jha**

GitHub:
https://github.com/anshu0609

---

⭐ If you find this project useful, consider giving the repository a star.
