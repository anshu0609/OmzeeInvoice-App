package com.omzee.invoice.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Environment;

import com.omzee.invoice.model.Customer;
import com.omzee.invoice.model.InvoiceItem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class PdfGenerator {

    // =====================================================
    // PAGE
    // =====================================================

    private static final int PAGE_WIDTH = 595;
    private static final int PAGE_HEIGHT = 842;

    private static final float LEFT = 15;
    private static final float RIGHT = 580;
    private static final float TOP = 15;

    // =====================================================
    // SECTION POSITIONS
    // =====================================================

    private static final float TITLE_BOTTOM = 52;

    private static final float COMPANY_DETAILS_BOTTOM = 166;
    private static final float COMPANY_BOTTOM = 180;

    private static final float BUYER_HEADER_BOTTOM = 200;
    private static final float BUYER_BOTTOM = 275;

    private static final float TABLE_HEADER_BOTTOM = 297;
    private static final float TABLE_BOTTOM = 650;

    private static final float TOTAL_BOTTOM = 670;
    private static final float TAX_BOTTOM = 735;
    private static final float WORDS_BOTTOM = 760;


    // =====================================================
    // GENERATE PDF
    // =====================================================

    public static File generateTestPdf(
            Context context,
            String invoiceNo,
            String invoiceDate,
            Customer customer,
            ArrayList<InvoiceItem> invoiceItems,
            double subTotal,
            double cgst,
            double sgst,
            double grandTotal
    ) throws IOException {

        PdfDocument document = new PdfDocument();

        PdfDocument.PageInfo pageInfo =
                new PdfDocument.PageInfo.Builder(
                        PAGE_WIDTH,
                        PAGE_HEIGHT,
                        1
                ).create();

        PdfDocument.Page page =
                document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        Paint paint =
                new Paint(Paint.ANTI_ALIAS_FLAG);


        // =================================================
        // OUTER BORDER
        // =================================================

        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(2f);

        canvas.drawRect(
                LEFT,
                TOP,
                RIGHT,
                PAGE_HEIGHT - 15,
                paint
        );


        // =================================================
        // TAX INVOICE TITLE
        // =================================================

        setBoldText(
                paint,
                20,
                Paint.Align.CENTER
        );

        canvas.drawText(
                "Tax Invoice",
                PAGE_WIDTH / 2f,
                40,
                paint
        );

        drawStrongLine(
                canvas,
                paint,
                LEFT,
                TITLE_BOTTOM,
                RIGHT,
                TITLE_BOTTOM
        );


        // =================================================
        // COMPANY / INVOICE DIVISION
        // =================================================

        float invoiceDivision = 390;

        drawStrongLine(
                canvas,
                paint,
                invoiceDivision,
                TITLE_BOTTOM,
                invoiceDivision,
                COMPANY_DETAILS_BOTTOM
        );


        // =================================================
        // COMPANY NAME
        // =================================================

        setBoldText(
                paint,
                12,
                Paint.Align.LEFT
        );

        canvas.drawText(
                "OMZEE CHEMICALS",
                LEFT + 4,
                78,
                paint
        );


        // =================================================
        // COMPANY ADDRESS
        // =================================================

        setNormalText(
                paint,
                9,
                Paint.Align.LEFT
        );

        canvas.drawText(
                "",
                315,
                78,
                paint
        );

        canvas.drawText(
                "F-9, Akash ,Bhumi Complex Near Dakshewar  Pandesara Surat-394221",
                LEFT + 4,
                94,
                paint
        );

        canvas.drawText(
                "",
                LEFT + 4,
                108,
                paint
        );


        // Divider after address

        drawNormalLine(
                canvas,
                paint,
                LEFT,
                114,
                invoiceDivision,
                114
        );


        // =================================================
        // COMPANY REGISTRATION DETAILS
        // =================================================

        setNormalText(
                paint,
                9,
                Paint.Align.LEFT
        );


        // GSTIN

        canvas.drawText(
                "GSTIN",
                LEFT + 4,
                126,
                paint
        );

        canvas.drawText(
                "24DPRPK8104L1ZG",
                70,
                126,
                paint
        );


        // UDYAM

        canvas.drawText(
                "U.A NO :-",
                LEFT + 4,
                141,
                paint
        );

        canvas.drawText(
                "UDYAM-GJ-22-0350119",
                70,
                141,
                paint
        );


        // CODE

        canvas.drawText(
                "Code",
                310,
                141,
                paint
        );

        canvas.drawText(
                "24",
                350,
                141,
                paint
        );


        // CONTACT

        canvas.drawText(
                "Cont No",
                LEFT + 4,
                156,
                paint
        );

        canvas.drawText(
                "9924506649",
                70,
                156,
                paint
        );


        // =================================================
// RIGHT SIDE INVOICE DETAILS
        // =================================================

        setNormalText(
                paint,
                9,
                Paint.Align.LEFT
        );

        canvas.drawText(
                "Invoice No:- " + invoiceNo,
                invoiceDivision + 4,
                68,
                paint
        );

        canvas.drawText(
                "Date:- " + invoiceDate,
                invoiceDivision + 4,
                84,
                paint
        );

        canvas.drawText(
                "Order Ref No",
                invoiceDivision + 4,
                100,
                paint
        );

        drawNormalLine(
                canvas,
                paint,
                invoiceDivision,
                108,
                RIGHT,
                108
        );

        // Reset text paint after drawing the line
        setNormalText(
                paint,
                9,
                Paint.Align.LEFT
        );

        // LR details - all four lines stay inside the LR section
        canvas.drawText(
                "LR. No",
                invoiceDivision + 4,
                123,
                paint
        );

        canvas.drawText(
                "LR Date",
                invoiceDivision + 4,
                136,
                paint
        );

        canvas.drawText(
                "Transporter",
                invoiceDivision + 4,
                149,
                paint
        );

        canvas.drawText(
                "Vehicle No.",
                invoiceDivision + 4,
                162,
                paint
        );

        // Bottom border of LR section
        drawNormalLine(
                canvas,
                paint,
                LEFT,
                COMPANY_DETAILS_BOTTOM,
                invoiceDivision,
                COMPANY_DETAILS_BOTTOM
        );

        drawNormalLine(
                canvas,
                paint,
                invoiceDivision,
                COMPANY_DETAILS_BOTTOM,
                RIGHT,
                COMPANY_DETAILS_BOTTOM
        );

        // Reset after drawing borders
        setNormalText(
                paint,
                9,
                Paint.Align.LEFT
        );

        // Email row belongs only to the left company-details section
        drawNormalLine(
                canvas,
                paint,
                300,
                COMPANY_DETAILS_BOTTOM,
                300,
                COMPANY_BOTTOM
        );

        setNormalText(
                paint,
                9,
                Paint.Align.LEFT
        );

        canvas.drawText(
                "Email",
                LEFT + 4,
                174,
                paint
        );

        paint.setColor(Color.BLUE);
        paint.setUnderlineText(true);

        canvas.drawText(
                "omzeechemicals2019@gmail.com",
                70,
                174,
                paint
        );

        // Reset text paint before Buyer / Consignee section
        setNormalText(
                paint,
                9,
                Paint.Align.LEFT
        );

// =================================================
        // BUYER / CONSIGNEE HEADER
        // =================================================

        float middle = PAGE_WIDTH / 2f;

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(
                Color.rgb(235, 110, 0)
        );

        canvas.drawRect(
                LEFT,
                COMPANY_BOTTOM,
                RIGHT,
                BUYER_HEADER_BOTTOM,
                paint
        );


        drawStrongLine(
                canvas,
                paint,
                LEFT,
                COMPANY_BOTTOM,
                RIGHT,
                COMPANY_BOTTOM
        );

        drawStrongLine(
                canvas,
                paint,
                LEFT,
                BUYER_HEADER_BOTTOM,
                RIGHT,
                BUYER_HEADER_BOTTOM
        );

        drawStrongLine(
                canvas,
                paint,
                middle,
                COMPANY_BOTTOM,
                middle,
                BUYER_BOTTOM
        );


        // Buyer headings

        setBoldText(
                paint,
                10,
                Paint.Align.CENTER
        );

        canvas.drawText(
                "Buyer",
                (LEFT + middle) / 2f,
                194,
                paint
        );

        canvas.drawText(
                "Consignee",
                (middle + RIGHT) / 2f,
                194,
                paint
        );


        // =================================================
        // CUSTOMER VALUES
        // =================================================

        String customerName =
                customer != null
                        && customer.getName() != null
                        ? customer.getName()
                        : "";

        String address =
                customer != null
                        && customer.getAddress() != null
                        ? customer.getAddress()
                        : "";

        String state =
                customer != null
                        && customer.getState() != null
                        ? customer.getState()
                        : "";

        String gst =
                customer != null
                        && customer.getGst() != null
                        ? customer.getGst()
                        : "";


        float buyerX = LEFT + 4;
        float consigneeX = middle + 4;

        float customerY = 217;


        // =================================================
        // CUSTOMER NAME - BOLD
        // =================================================

        setBoldText(
                paint,
                10,
                Paint.Align.LEFT
        );

        canvas.drawText(
                customerName,
                buyerX,
                customerY,
                paint
        );

        canvas.drawText(
                customerName,
                consigneeX,
                customerY,
                paint
        );


        // =================================================
        // CUSTOMER ADDRESS - NORMAL
        // =================================================

        setNormalText(
                paint,
                9,
                Paint.Align.LEFT
        );

        float buyerAddressEnd =
                drawWrappedText(
                        canvas,
                        paint,
                        address,
                        buyerX,
                        customerY + 17,
                        middle - buyerX - 8,
                        12
                );

        float consigneeAddressEnd =
                drawWrappedText(
                        canvas,
                        paint,
                        address,
                        consigneeX,
                        customerY + 17,
                        RIGHT - consigneeX - 5,
                        12
                );


        float detailsY =
                Math.max(
                        buyerAddressEnd,
                        consigneeAddressEnd
                ) + 3;


        // =================================================
        // STATE / GST - NORMAL
        // =================================================

        setNormalText(
                paint,
                9,
                Paint.Align.LEFT
        );

        canvas.drawText(
                "STATE: " + state.toUpperCase(),
                buyerX,
                detailsY,
                paint
        );

        canvas.drawText(
                "STATE: " + state.toUpperCase(),
                consigneeX,
                detailsY,
                paint
        );


        canvas.drawText(
                "GSTIN:- " + gst,
                buyerX,
                detailsY + 15,
                paint
        );

        canvas.drawText(
                "GSTIN:- " + gst,
                consigneeX,
                detailsY + 15,
                paint
        );


        drawStrongLine(
                canvas,
                paint,
                LEFT,
                BUYER_BOTTOM,
                RIGHT,
                BUYER_BOTTOM
        );


        // =================================================
        // PRODUCT TABLE
        // =================================================

        float colSr = 55;
        float colDescription = 300;
        float colHsn = 345;
        float colQty = 405;
        float colUnit = 450;
        float colRate = 505;


        // Green heading background

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(
                Color.rgb(0, 180, 80)
        );

        canvas.drawRect(
                LEFT,
                BUYER_BOTTOM,
                RIGHT,
                TABLE_HEADER_BOTTOM,
                paint
        );


        // Product table borders

        drawStrongLine(
                canvas,
                paint,
                LEFT,
                BUYER_BOTTOM,
                RIGHT,
                BUYER_BOTTOM
        );

        drawStrongLine(
                canvas,
                paint,
                LEFT,
                TABLE_HEADER_BOTTOM,
                RIGHT,
                TABLE_HEADER_BOTTOM
        );


        drawNormalLine(
                canvas,
                paint,
                colSr,
                BUYER_BOTTOM,
                colSr,
                TABLE_BOTTOM
        );

        drawNormalLine(
                canvas,
                paint,
                colDescription,
                BUYER_BOTTOM,
                colDescription,
                TABLE_BOTTOM
        );

        drawNormalLine(
                canvas,
                paint,
                colHsn,
                BUYER_BOTTOM,
                colHsn,
                TABLE_BOTTOM
        );

        drawNormalLine(
                canvas,
                paint,
                colQty,
                BUYER_BOTTOM,
                colQty,
                TABLE_BOTTOM
        );

        drawNormalLine(
                canvas,
                paint,
                colUnit,
                BUYER_BOTTOM,
                colUnit,
                TABLE_BOTTOM
        );

        drawNormalLine(
                canvas,
                paint,
                colRate,
                BUYER_BOTTOM,
                colRate,
                TABLE_BOTTOM
        );


        // =================================================
        // PRODUCT HEADINGS - BOLD
        // =================================================

        setBoldText(
                paint,
                8,
                Paint.Align.LEFT
        );

        canvas.drawText(
                "Sr. No",
                LEFT + 2,
                290,
                paint
        );

        canvas.drawText(
                "Description",
                colSr + 3,
                290,
                paint
        );

        canvas.drawText(
                "HSN",
                colDescription + 3,
                290,
                paint
        );

        canvas.drawText(
                "Quantity",
                colHsn + 3,
                290,
                paint
        );

        canvas.drawText(
                "Unit",
                colQty + 3,
                290,
                paint
        );

        canvas.drawText(
                "Rate",
                colUnit + 3,
                290,
                paint
        );

        canvas.drawText(
                "Amount Rs",
                colRate + 3,
                290,
                paint
        );


        // =================================================
        // PRODUCT ROWS - NORMAL
        // =================================================

        setNormalText(
                paint,
                9,
                Paint.Align.LEFT
        );

        float productY = 315;


        for (int i = 0;
             i < invoiceItems.size();
             i++) {

            InvoiceItem item =
                    invoiceItems.get(i);


            // Serial No

            setNormalText(
                    paint,
                    9,
                    Paint.Align.CENTER
            );

            canvas.drawText(
                    String.valueOf(i + 1),
                    (LEFT + colSr) / 2f,
                    productY,
                    paint
            );


            // Description

            setNormalText(
                    paint,
                    9,
                    Paint.Align.LEFT
            );

            String description =
                    item.getProductDescription() != null
                            ? item.getProductDescription()
                            : "";


            float descriptionEnd =
                    drawWrappedText(
                            canvas,
                            paint,
                            description,
                            colSr + 3,
                            productY,
                            colDescription
                                    - colSr
                                    - 8,
                            12
                    );


            // HSN

            setNormalText(
                    paint,
                    9,
                    Paint.Align.CENTER
            );

            canvas.drawText(
                    item.getHsn(),
                    (colDescription + colHsn)
                            / 2f,
                    productY,
                    paint
            );


            // Quantity

            setNormalText(
                    paint,
                    9,
                    Paint.Align.RIGHT
            );

            canvas.drawText(
                    formatNumber(
                            item.getQuantity()
                    ),
                    colQty - 3,
                    productY,
                    paint
            );


            // Unit

            setNormalText(
                    paint,
                    9,
                    Paint.Align.CENTER
            );

            canvas.drawText(
                    "KG",
                    (colQty + colUnit) / 2f,
                    productY,
                    paint
            );


            // Rate

            setNormalText(
                    paint,
                    9,
                    Paint.Align.RIGHT
            );

            canvas.drawText(
                    String.format(
                            Locale.getDefault(),
                            "%.2f",
                            item.getRate()
                    ),
                    colRate - 3,
                    productY,
                    paint
            );


            // Amount

            setNormalText(
                    paint,
                    9,
                    Paint.Align.RIGHT
            );

            canvas.drawText(
                    String.format(
                            Locale.getDefault(),
                            "%.2f",
                            item.getAmount()
                    ),
                    RIGHT - 3,
                    productY,
                    paint
            );


            productY =
                    Math.max(
                            productY + 28,
                            descriptionEnd + 12
                    );
        }


        // =================================================
        // TOTAL
        // =================================================

        drawStrongLine(
                canvas,
                paint,
                LEFT,
                TABLE_BOTTOM,
                RIGHT,
                TABLE_BOTTOM
        );

        drawStrongLine(
                canvas,
                paint,
                LEFT,
                TOTAL_BOTTOM,
                RIGHT,
                TOTAL_BOTTOM
        );


        setBoldText(
                paint,
                9,
                Paint.Align.CENTER
        );

        canvas.drawText(
                "Total",
                320,
                664,
                paint
        );


        setBoldText(
                paint,
                9,
                Paint.Align.RIGHT
        );

        canvas.drawText(
                String.format(
                        Locale.getDefault(),
                        "%.2f",
                        subTotal
                ),
                RIGHT - 3,
                664,
                paint
        );


        // =================================================
        // BANK + TAX DIVISION
        // =================================================

        float taxMiddle = 390;

        drawStrongLine(
                canvas,
                paint,
                taxMiddle,
                TOTAL_BOTTOM,
                taxMiddle,
                TAX_BOTTOM
        );

        drawStrongLine(
                canvas,
                paint,
                LEFT,
                TAX_BOTTOM,
                RIGHT,
                TAX_BOTTOM
        );


        // =================================================
        // BANK DETAILS HEADING - BOLD
        // =================================================

        setBoldText(
                paint,
                9,
                Paint.Align.LEFT
        );

        canvas.drawText(
                "Bank Details",
                70,
                685,
                paint
        );


        // =================================================
        // BANK VALUES - NORMAL
        // =================================================

        setNormalText(
                paint,
                9,
                Paint.Align.LEFT
        );

        canvas.drawText(
                "Bank",
                LEFT + 4,
                700,
                paint
        );

        canvas.drawText(
                "State Bank of India",
                75,
                700,
                paint
        );

        canvas.drawText(
                "A/c No",
                LEFT + 4,
                715,
                paint
        );

        canvas.drawText(
                "39200441075",
                75,
                715,
                paint
        );

        canvas.drawText(
                "IFSC",
                LEFT + 4,
                730,
                paint
        );

        canvas.drawText(
                "SBIN0018159",
                75,
                730,
                paint
        );


        // =================================================
        // CGST / SGST - NORMAL
        // =================================================

        setNormalText(
                paint,
                9,
                Paint.Align.LEFT
        );

        canvas.drawText(
                "CGST@9%",
                taxMiddle + 4,
                688,
                paint
        );

        canvas.drawText(
                "SGST@9%",
                taxMiddle + 4,
                706,
                paint
        );


        setNormalText(
                paint,
                9,
                Paint.Align.RIGHT
        );

        canvas.drawText(
                String.format(
                        Locale.getDefault(),
                        "%.2f",
                        cgst
                ),
                RIGHT - 4,
                688,
                paint
        );

        canvas.drawText(
                String.format(
                        Locale.getDefault(),
                        "%.2f",
                        sgst
                ),
                RIGHT - 4,
                706,
                paint
        );


        // =================================================
        // NET AMOUNT
        // =================================================

        drawNormalLine(
                canvas,
                paint,
                505,
                714,
                RIGHT,
                714
        );


        setBoldText(
                paint,
                9,
                Paint.Align.LEFT
        );

        canvas.drawText(
                "Net Amount",
                taxMiddle + 4,
                730,
                paint
        );


        setBoldText(
                paint,
                9,
                Paint.Align.RIGHT
        );

        canvas.drawText(
                String.format(
                        Locale.getDefault(),
                        "%.2f",
                        grandTotal
                ),
                RIGHT - 4,
                730,
                paint
        );


        // =================================================
        // AMOUNT IN WORDS - BOLD
        // =================================================

        drawStrongLine(
                canvas,
                paint,
                LEFT,
                WORDS_BOTTOM,
                RIGHT,
                WORDS_BOTTOM
        );


        long roundedTotal =
                Math.round(grandTotal);

        String amountWords =
                NumberToWords
                        .convert(roundedTotal)
                        .toUpperCase();


        setBoldText(
                paint,
                9,
                Paint.Align.CENTER
        );

        canvas.drawText(
                "Amount In Words:-  "
                        + amountWords,
                PAGE_WIDTH / 2f,
                752,
                paint
        );


        // =================================================
        // SIGNATURE
        // =================================================

        setBoldText(
                paint,
                9,
                Paint.Align.LEFT
        );

        canvas.drawText(
                "For: Omzee Chemicals",
                430,
                780,
                paint
        );


        // Authorised Signatory NORMAL

        setNormalText(
                paint,
                9,
                Paint.Align.LEFT
        );

        canvas.drawText(
                "Authorised Signatory",
                430,
                815,
                paint
        );


        // =================================================
        // FINISH PDF
        // =================================================

        document.finishPage(page);


        File directory =
                new File(
                        context.getExternalFilesDir(
                                Environment
                                        .DIRECTORY_DOCUMENTS
                        ),
                        "Invoices"
                );


        if (!directory.exists()) {
            directory.mkdirs();
        }


        String safeInvoiceNo =
                invoiceNo.replaceAll(
                        "[^a-zA-Z0-9_-]",
                        "_"
                );


        File file =
                new File(
                        directory,
                        "OMZEE_Invoice_"
                                + safeInvoiceNo
                                + ".pdf"
                );


        FileOutputStream outputStream =
                new FileOutputStream(file);

        document.writeTo(outputStream);

        outputStream.close();

        document.close();

        return file;
    }


    // =====================================================
    // NORMAL TEXT
    // =====================================================

    private static void setNormalText(
            Paint paint,
            float size,
            Paint.Align align
    ) {

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        paint.setTextSize(size);
        paint.setTextAlign(align);

        // Critical reset
        paint.setFakeBoldText(false);
        paint.setUnderlineText(false);
        paint.setTypeface(null);
    }


    // =====================================================
    // BOLD TEXT
    // =====================================================

    private static void setBoldText(
            Paint paint,
            float size,
            Paint.Align align
    ) {

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        paint.setTextSize(size);
        paint.setTextAlign(align);

        paint.setUnderlineText(false);
        paint.setTypeface(null);
        paint.setFakeBoldText(true);
    }


    // =====================================================
    // STRONG SECTION LINE
    // =====================================================

    private static void drawStrongLine(
            Canvas canvas,
            Paint paint,
            float startX,
            float startY,
            float endX,
            float endY
    ) {

        Paint linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setColor(Color.BLACK);
        linePaint.setStrokeWidth(1.5f);

        canvas.drawLine(
                startX,
                startY,
                endX,
                endY,
                linePaint
        );
    }


    // =====================================================
    // NORMAL TABLE LINE
    // =====================================================

    private static void drawNormalLine(
            Canvas canvas,
            Paint paint,
            float startX,
            float startY,
            float endX,
            float endY
    ) {

        Paint linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setColor(Color.BLACK);
        linePaint.setStrokeWidth(0.8f);

        canvas.drawLine(
                startX,
                startY,
                endX,
                endY,
                linePaint
        );
    }


    // =====================================================
    // WRAP LONG TEXT
    // =====================================================

    private static float drawWrappedText(
            Canvas canvas,
            Paint paint,
            String text,
            float x,
            float y,
            float maxWidth,
            float lineHeight
    ) {

        if (text == null
                || text.trim().isEmpty()) {

            return y;
        }


        String[] manualLines =
                text.split("\n");

        float currentY = y;


        for (String manualLine :
                manualLines) {

            String[] words =
                    manualLine
                            .trim()
                            .split("\\s+");

            StringBuilder line =
                    new StringBuilder();


            for (String word : words) {

                String testLine;

                if (line.length() == 0) {

                    testLine = word;

                } else {

                    testLine =
                            line + " " + word;
                }


                if (paint.measureText(testLine)
                        <= maxWidth) {

                    line =
                            new StringBuilder(
                                    testLine
                            );

                } else {

                    if (line.length() > 0) {

                        canvas.drawText(
                                line.toString(),
                                x,
                                currentY,
                                paint
                        );

                        currentY += lineHeight;
                    }

                    line =
                            new StringBuilder(
                                    word
                            );
                }
            }


            if (line.length() > 0) {

                canvas.drawText(
                        line.toString(),
                        x,
                        currentY,
                        paint
                );

                currentY += lineHeight;
            }
        }


        return currentY;
    }


    // =====================================================
    // QUANTITY FORMAT
    // =====================================================

    private static String formatNumber(
            double value
    ) {

        if (value == Math.floor(value)) {

            return String.format(
                    Locale.getDefault(),
                    "%.0f",
                    value
            );
        }


        return String.format(
                Locale.getDefault(),
                "%.2f",
                value
        );
    }
}