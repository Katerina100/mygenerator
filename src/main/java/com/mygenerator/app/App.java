package com.mygenerator.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class App {
    public static void main(String[] args) {
        try {
            FileOutputStream file = new FileOutputStream(new File("people.xlsx"));
            file.close();
        } catch (FileNotFoundException e) {
            System.out.println("exception occured is " + e);
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("exception occured is " + e);
            e.printStackTrace();
        }
    }
}
