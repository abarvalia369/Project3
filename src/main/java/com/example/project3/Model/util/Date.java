package com.example.project3.Model.util;
import java.util.Calendar;
/**
 Account class for RU Bank project.
 Represents a bank account with an account number, holder profile, and balance.
 Implements deposit and withdrawal operations.
 Overrides equals, toString, and compareTo.

 @author arpeet barvalia, jonathan john
 */

public class Date implements Comparable<Date> {
    private int year;
    private int month;
    private int day;

    public static final int JANUARY = 1;
    public static final int FEBRUARY = 2;
    public static final int MARCH = 3;
    public static final int APRIL = 4;
    public static final int MAY = 5;
    public static final int JUNE = 6;
    public static final int JULY = 7;
    public static final int AUGUST = 8;
    public static final int SEPTEMBER = 9;
    public static final int OCTOBER = 10;
    public static final int NOVEMBER = 11;
    public static final int DECEMBER = 12;
    public static final int LONGMONTHDAYS = 31;
    public static final int SHORTMONTHDAYS = 30;
    public static final int FEBRUARYDAYS = 28;
    public static final int FEBRUARYLEAP = 29;
    private static final int[] DAYS_IN_MONTH = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    /**
     * Initializes an Date Object with the current Date
     */
    public Date() {
        Calendar today = Calendar.getInstance();
        year = today.get(Calendar.YEAR);
        month = today.get(Calendar.MONTH) + 1;
        day = today.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Initializes an Date Object with the following 3 parameters.
     *
     * @param year  int year
     * @param month int month
     * @param day   int day
     */
    public Date(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    /**
     * Initializes an Date Object with the following parameter.
     *
     * @param date date Object
     */
    public Date(Date date) {
        this.year = date.year;
        this.month = date.month;
        this.day = date.day;
    }

    public Date(String date) {
        String[] parts = date.split("/");

        if (parts.length == 3) {
            try {
                int parsedMonth = Integer.parseInt(parts[0]);
                int parsedDay = Integer.parseInt(parts[1]);
                int parsedYear = Integer.parseInt(parts[2]);

                this.year = parsedYear;
                this.month = parsedMonth;
                this.day = parsedDay;

                // Validate the parsed date
                if (!this.isValid()) {
                    this.year = 0;
                    this.month = 0;
                    this.day = 0;
                }
            } catch (NumberFormatException e) {

                this.year = 0;
                this.month = 0;
                this.day = 0;
            }
        } else {

            this.year = 0;
            this.month = 0;
            this.day = 0;
        }
    }


    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDay(int day) {
        this.day = day;
    }

    /**
     * Return a textual representation of the Date object.
     *
     * @return a string of the date in MM/DD/YYYY Format
     */
    @Override
    public String toString() {
        if (this.isValidFuture()) {
            return String.format("%02d/%02d/%04d", month, day, year);
        }
        return "Invalid Date";
    }

    /**
     * Compare two Date objects based on the sum of the year, month and day values
     *
     * @param date the date object to be compared.
     * @return checks whether they are the same year, month day, at they level (if) they are not the same, returns whether our date is
     * is the greater or less than comparison date.
     */
    @Override
    public int compareTo(Date date) {
        if (this.year != date.year) {
            return Integer.compare(this.year, date.year);
        }
        if (this.month != date.month) {
            return Integer.compare(this.month, date.month);
        }
        return Integer.compare(this.day, date.day);
    }

    /**
     * @param obj the object to be compared
     * @return return true if two date objects are equal (year, month and day match); return false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Date) {
            Date date = (Date) obj;
            return (this.year == date.year && this.month == date.month && this.day == date.day);
        }
        return false;
    }


    /**
     * Checks if date is a valid date
     *
     * @return Checks if valid year and if it is a leap year, than checks if it contains valid month and day.
     */
    public boolean isValid() {
        Date pointer = new Date(year, month, day);//Check if Leap Year according to rules
        Date today = new Date();
        if (pointer.compareTo(today) > 0) {
            return false;
        }
        boolean isLeapYear = false;
        if (year < 0) return false;
        if (year % 4 == 0) { //must be divisible by four years
            if (year % 100 != 0) { //if divisible by four, must not be divisible by 100
                isLeapYear = true;
            } else if (year % 400 == 0) { // unless also divisible by 400 years
                isLeapYear = true;
            }
        }
        //Check if valid month
        if (month < JANUARY || month > DECEMBER)
            return false;
        //Check if valid day according to month
        if (day < 1) //first of the month
            return false;
        if (month == FEBRUARY) {
            if (isLeapYear) {
                if (day > FEBRUARYLEAP)
                    return false;
            } else {
                if (day > FEBRUARYDAYS)
                    return false;
            }
        } else {
            if (month == JANUARY || month == MARCH || month == MAY || month == JULY || month == AUGUST || month == OCTOBER || month == DECEMBER) {
                if (day > LONGMONTHDAYS)
                    return false;
            } else if (day > SHORTMONTHDAYS)
                return false;
        }
        return true;
    }

    public boolean isValidFuture() {
        boolean isLeapYear = false;
        if (year < 0) return false;
        if (year % 4 == 0) { //must be divisible by four years
            if (year % 100 != 0) { //if divisible by four, must not be divisible by 100
                isLeapYear = true;
            } else if (year % 400 == 0) { // unless also divisible by 400 years
                isLeapYear = true;
            }
        }

        //Check if valid month
        if (month < JANUARY || month > DECEMBER)
            return false;
        //Check if valid day according to month
        if (day < 1) //first of the month
            return false;
        if (month == FEBRUARY) {
            if (isLeapYear) {
                if (day > FEBRUARYLEAP)
                    return false;
            } else {
                if (day > FEBRUARYDAYS)
                    return false;
            }
        } else {
            if (month == JANUARY || month == MARCH || month == MAY || month == JULY || month == AUGUST || month == OCTOBER || month == DECEMBER) {
                if (day > LONGMONTHDAYS)
                    return false;
            } else if (day > SHORTMONTHDAYS)
                return false;
        }
        return true;
    }


}