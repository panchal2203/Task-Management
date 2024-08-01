package com.taskapp.task.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtility {

    public static final String DATE_FORMAT = "dd-MM-yyyy";
    public static LocalDate stringToLocalDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        return LocalDate.parse(dateString, formatter);
    }

    public static String localDateToString(LocalDate date) {
        if (date == null) return "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        return date.format(formatter);
    }
}
