package com.mygenerator.app.util;

import java.util.Random;

public class RandomIndexGenerator {
    public static String getNew() {
        Random rand = new Random();
        int number = rand.nextInt(999999);
        return String.format("%06d", number);
    }
}