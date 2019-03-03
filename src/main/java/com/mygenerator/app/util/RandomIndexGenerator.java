package com.mygenerator.app.util;

import java.util.Random;

public class RandomIndexGenerator {
    public static String getNew() {
        int min = 100000;
        int max = 200000;
        int diff = max - min;
        Random rand = new Random();
        int number = rand.nextInt(diff + 1);
        number += min;
        return String.format("%06d", number);
    }
}