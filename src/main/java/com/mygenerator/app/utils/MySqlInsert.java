package com.mygenerator.app.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Period;
import java.util.Date;
import java.util.List;

import com.mygenerator.app.models.Person;

public class MySqlInsert {
    private static final String URL = "jdbc:mysql://localhost:3306/mygenerator?serverTimezone=Europe/Moscow&useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static void sqlConnectInsert(List<Person> people) {
        DateFormat dateFormat = new SimpleDateFormat("dd.mm.yyyy");
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connection to DB successfully established");
            Statement st = conn.createStatement();
            for (int i = 0; i < people.size(); i++) {
                Person person = people.get(i);
                int personAge = Period
                        .between(DateHelper.getLocalDate(person.getBirthDate()), DateHelper.getLocalDate(new Date()))
                        .getYears();
                st.execute(
                        "INSERT INTO people (lfp, age, gender, dob, inn, zip, country, region, city ,street, house, flat) "
                                + (String.format(
                                        "VALUES ('%s', %s, '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', %s, %s)",
                                        person.toString(), personAge, person.getSex(),
                                        dateFormat.format(person.getBirthDate()), person.getInn(), person.getIndex(),
                                        person.getCountry(), person.getRegion(), person.getCity(), person.getStreet(),
                                        person.getHouse(), person.getFlat())));
            }
            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
