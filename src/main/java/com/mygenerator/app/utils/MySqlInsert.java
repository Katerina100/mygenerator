package com.mygenerator.app.utils;

import java.sql.*;

    public class MySqlInsert {
        private static final String url = "jdbc:mysql://localhost/persons?serverTimezone=Europe/Moscow&useSSL=false";
        private static final String user = "root";
        private static final String password = "abramowa";

        public static void sqlConnectInsert() {
            try {
                Connection conn = DriverManager.getConnection(url, user, password);
                System.out.println("Success");
                Statement st = conn.createStatement();
                st.execute("INSERT INTO persons (surname , name , middlename,birthday, gender, inn, postcode, country, region, city, street, house, flat) "
                        +"VALUES (person.getLastName(), person.getFirstName(),person.getPatronymic(),person.getBirthDate(),person.getSex(),person.getInn(),person.getIndex(),person.getCountry(),person.getRegion(),person.getCity(),person.getStreet(),person.getHouse(),person.getFlat)");

                conn.close();
            } catch (Exception ex) {
                System.out.println("ERROR");
            }
        }
    }

