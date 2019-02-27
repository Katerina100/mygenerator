package com.mygenerator.app.util;

import java.util.Random;

public class RandomIndexGenerator {
    public static int getNew() {
        int num;
        Random rand = new Random();
        num = 100000 + rand.nextInt(200000 - 100000 + 1);
        return num;
    }
}