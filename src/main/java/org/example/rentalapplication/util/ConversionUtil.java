package org.example.rentalapplication.util;

import java.text.DecimalFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ConversionUtil {
    private static final String DATE_FORMAT = "MM/dd/yy";
    private static final DecimalFormat PERCENTAGE_FORMAT = new DecimalFormat("##.##%");

    public static LocalDate stringToDate(String dateString) throws DateTimeException {
        return LocalDate.parse(dateString, DateTimeFormatter.ofPattern(DATE_FORMAT));
    }

    public static String dateToString(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
    }

    public static String doubleToPercentage(double value) {
        return PERCENTAGE_FORMAT.format(value/100);
    }

    public static String doubleToDollar(double amount) {
        return new DecimalFormat("$###,###.##").format(amount);
    }

}
