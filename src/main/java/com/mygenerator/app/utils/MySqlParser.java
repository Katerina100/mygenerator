package com.mygenerator.app;

import java.sql.*;

    public class MySqlParser {
        private static final String url = "jdbc:mysql://localhost/persons?serverTimezone=Europe/Moscow&useSSL=false";
        private static final String user = "root";
        private static final String password = "abramowa";

        public static void sqlConnect() {
            try  {
                Connection conn = DriverManager.getConnection(url, user, password);
                System.out.println("Success");
            } catch (Exception ex) {
                System.out.println("Connection failed...");
            }
        }
    }

