package org.example.rentalapplication.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DateTimeException;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class ConverstionUtilTest {
    /**
     * Method under test: {@link ConversionUtil#stringToDate(String)}
     */
    @Test
    void testStringToDate() throws DateTimeException {
        assertEquals( LocalDate.of(2024, 03, 21), ConversionUtil.stringToDate("03/21/24"));
    }

    /**
     * Method under test: {@link ConversionUtil#dateToString(LocalDate)}
     */
    @Test
    void testDateToString() {
        assertEquals("01/01/70", ConversionUtil.dateToString(LocalDate.of(1970, 1, 1)));
    }

    /**
     * Method under test: {@link ConversionUtil#doubleToPercentage(double)}
     */
    @Test
    void testDoubleToPercentage() {
        assertEquals("10%", ConversionUtil.doubleToPercentage(10.0d));
    }

    /**
     * Method under test: {@link ConversionUtil#doubleToDollar(double)}
     */
    @Test
    void testDoubleToDollar() {
        assertEquals("$10", ConversionUtil.doubleToDollar(10.0d));
    }
}
