package com.mygenerator.app.util;

import java.util.Date;
import java.util.GregorianCalendar;

public class RandomBirthDateGenerator {
    public static Date getNew() {
        GregorianCalendar gc = new GregorianCalendar();

        int year = randBetween(1930, 2001);
        gc.set(GregorianCalendar.YEAR, year);

        int dayOfYear = randBetween(1, gc.getActualMaximum(GregorianCalendar.DAY_OF_YEAR));
        gc.set(GregorianCalendar.DAY_OF_YEAR, dayOfYear);

        return gc.getTime();
    }

    private static int randBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));
    }
}
