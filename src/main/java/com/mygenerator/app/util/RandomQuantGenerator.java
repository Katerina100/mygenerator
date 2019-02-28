package com.mygenerator.app.util;

import java.util.Random;

public class RandomQuantGenerator {
    public static int getNew() {
        int qn;
        Random rand = new Random();
        qn = 1 + rand.nextInt(30 - 1 + 1);
        return qn;
    }
}