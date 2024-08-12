package com.ideas2it.ems.util;

import java.time.LocalDate;
import java.time.Period;

/**
 * <p>
 * Contains the validation methods for the inputs and calculating age.
 * e.g. name,phone number.
 * </p>
 *
 * @author Saiprasath
 * @version 1.5
 */
public class Util {
    /**
     * <p>
     * Calculates the age of employee using date of birth.
     * </p>
     *
     * param date LocalDate value to calculate.
     * @return String value of age in year and months.
     */
    public static String calculateAge(LocalDate date) {
        return Period.between(date, LocalDate.now()).getYears()
                + "y " + Period.between(date, LocalDate.now()).getMonths() + "m";
    }
}
