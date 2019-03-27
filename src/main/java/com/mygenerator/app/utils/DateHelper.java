package com.mygenerator.app.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DateHelper {
    public static LocalDate getLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}