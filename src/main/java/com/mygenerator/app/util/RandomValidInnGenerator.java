package com.mygenerator.app.util;

import java.util.Arrays;
import java.util.Random;

public class RandomValidInnGenerator {
    public RandomValidInnGenerator() {
    }

    public static long getNew() {
        int[] arrayedInn = new int[12];

        arrayedInn[0] = 7;
        arrayedInn[1] = 7;

        for (int i = 2; i < 10; i++) {
            Random rand = new Random();
            arrayedInn[i] = rand.nextInt(9);
        }

        for (int j = 2; j > 0; j--) {
            arrayedInn[arrayedInn.length - j] = getInnVerificationDigit(
                    Arrays.copyOfRange(arrayedInn, 0, arrayedInn.length - j));
        }

        StringBuilder stringedInn = new StringBuilder();
        for (int digit : arrayedInn) {
            stringedInn.append(digit);
        }

        return Long.parseLong(stringedInn.toString());
    }

    private static int getInnVerificationDigit(int[] digitsArray) {
        int verificationDigit = 0;
        if (digitsArray.length == 10) {
            verificationDigit = ((7 * digitsArray[0]) + (2 * digitsArray[1]) + (4 * digitsArray[2])
                    + (10 * digitsArray[3]) + (3 * digitsArray[4]) + (5 * digitsArray[5]) + (9 * digitsArray[6])
                    + (4 * digitsArray[7]) + (6 * digitsArray[8]) + (8 * digitsArray[9])) % 11;
        }
        if (digitsArray.length == 11) {
            return ((3 * digitsArray[0]) + (7 * digitsArray[1]) + (2 * digitsArray[2]) + (4 * digitsArray[3])
                    + (10 * digitsArray[4]) + (3 * digitsArray[5]) + (5 * digitsArray[6]) + (9 * digitsArray[7])
                    + (4 * digitsArray[8]) + (6 * digitsArray[9]) + (8 * digitsArray[10])) % 11;
        }
        verificationDigit = verificationDigit <= 9 ? verificationDigit : verificationDigit % 10;
        return verificationDigit;
    }
}