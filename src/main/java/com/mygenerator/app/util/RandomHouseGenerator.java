package com.mygenerator.app.util;

import java.util.Random;

public class RandomHouseGenerator {
    public static int getNew() {
        int number;
        Random rand = new Random();
        number = rand.nextInt(99);
        return number;
    }
}