package com.omzee.invoice.utils;

public class NumberToWords {

    private static final String[] ONES = {
            "", "One", "Two", "Three", "Four", "Five",
            "Six", "Seven", "Eight", "Nine", "Ten",
            "Eleven", "Twelve", "Thirteen", "Fourteen",
            "Fifteen", "Sixteen", "Seventeen", "Eighteen",
            "Nineteen"
    };

    private static final String[] TENS = {
            "", "", "Twenty", "Thirty", "Forty",
            "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"
    };

    public static String convert(long number) {

        if (number == 0) {
            return "Zero Rupees Only";
        }

        StringBuilder result = new StringBuilder();

        // Crore
        if (number >= 10000000) {
            result.append(convertBelowThousand(number / 10000000))
                    .append(" Crore ");
            number %= 10000000;
        }

        // Lakh
        if (number >= 100000) {
            result.append(convertBelowThousand(number / 100000))
                    .append(" Lakh ");
            number %= 100000;
        }

        // Thousand
        if (number >= 1000) {
            result.append(convertBelowThousand(number / 1000))
                    .append(" Thousand ");
            number %= 1000;
        }

        // Remaining
        if (number > 0) {
            result.append(convertBelowThousand(number));
        }

        return result.toString().trim() + " Rupees Only";
    }

    private static String convertBelowThousand(long number) {

        StringBuilder result = new StringBuilder();

        if (number >= 100) {
            result.append(ONES[(int) (number / 100)])
                    .append(" Hundred ");

            number %= 100;
        }

        if (number >= 20) {
            result.append(TENS[(int) (number / 10)])
                    .append(" ");

            number %= 10;
        }

        if (number > 0) {
            result.append(ONES[(int) number])
                    .append(" ");
        }

        return result.toString().trim();
    }
}