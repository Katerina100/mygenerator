package com.mygenerator.app.util;

import java.util.Arrays;
import java.util.Random;

public class RandomValidInnGenerator {
    private static int[] verificationCoefficientsArray = { 3, 7, 2, 4, 10, 3, 5, 9, 4, 6, 8, 0 };

    public static String getNew() {
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

        return stringedInn.toString();
    }

    private static int getInnVerificationDigit(int[] digitsArray) {
        int verificationDigit = 0;
        int coefStartIndex = digitsArray.length % 10 > 0 ? 0 : 1;

        for (int i = 0; i < digitsArray.length; i++) {
            verificationDigit += verificationCoefficientsArray[coefStartIndex] * digitsArray[i];
            coefStartIndex++;
        }

        verificationDigit = verificationDigit % 11;
        verificationDigit = verificationDigit > 9 ? verificationDigit % 10 : verificationDigit;
        return verificationDigit;
    }
}
