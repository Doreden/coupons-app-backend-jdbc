package com.dor.couponssystem.utills;

import java.time.LocalDate;
import java.util.Calendar;

public class DateValidationUtil {
    private final static String dateRegex = "[/]|[-]|[.]|[ ]";

    public static boolean isDatePassed (String date) {

        int year = Integer.parseInt(date.split(dateRegex)[2]);
        int month = Integer.parseInt(date.split(dateRegex)[1]);
        int day = Integer.parseInt(date.split(dateRegex)[0]);

        // Gets the current date and time
        Calendar now = Calendar.getInstance();

        //Split current time
        int currentYear = now.get(Calendar.YEAR);

        // For some reason this functions seems to drop 1 month when getting the current date, therefore it increments it by 1 when getting it
        int currentMonth = now.get(Calendar.MONTH) + 1;
        int currentDay = now.get(Calendar.DAY_OF_MONTH);

        //Setting format for dates
        LocalDate currentDate = LocalDate.of(currentYear, currentMonth, currentDay);
        LocalDate EndDate = LocalDate.of(year, month, day);

        //Checking what the time received from the current date is
        return currentDate.isAfter(EndDate);
    }

    public static String getCurrentDate() {

        // Gets the current date and time
        Calendar now = Calendar.getInstance();

        //Split current time
        int currentYear = now.get(Calendar.YEAR);

        // For some reason this functions seems to drop 1 month when getting the current date, therefore it increments it by 1 when getting it
        int currentMonth = now.get(Calendar.MONTH) + 1;
        int currentDay = now.get(Calendar.DAY_OF_MONTH);

        //Setting format for dates
        LocalDate currentDate = LocalDate.of(currentYear, currentMonth, currentDay);

        //Checking what the time received from the current date is
        return currentDate.toString();

    }
}
