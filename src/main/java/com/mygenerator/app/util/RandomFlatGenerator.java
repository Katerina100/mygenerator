package com.mygenerator.app.util;

import java.util.Random;

public class RandomFlatGenerator {
    public static int getNew() {
        int numb;
        Random rand = new Random();
        numb = rand.nextInt(300);
        return numb;
    }
}