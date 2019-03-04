package com.mygenerator.app.model;

import java.util.Date;

public class Person {
    private String firstName;
    private String lastName;
    private String patronymic;
    private String sex;
    private Date birthDate;
    private String inn;
    private String index;
    private String country;
    private String region;
    private String city;
    private String street;
    private int house;
    private int flat;


    public Person(String lastName, String firstName, String patronymic, String sex, Date birthDate, String inn, String index, String country,
            String region, String city, String street, int house, int flat) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.sex = sex;
        this.birthDate = birthDate;
        this.inn = inn;
        this.index = index;
        this.country = country;
        this.city = city;
        this.region = region;
        this.street = street;
        this.house = house;
        this.flat = flat;


    }

    public String getLastName() {
        return this.lastName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getPatronymic() {
        return this.patronymic;
    }

    public String getSex() {
        return this.sex;
    }

    public Date getBirthDate() {
        return this.birthDate;
    }

    public String getInn() {
        return this.inn;
    }

    public String getIndex() { return this.index; }

    public String getCountry() {
        return this.country;
    }

    public String getRegion() {
        return this.region;
    }

    public String getCity() {
        return this.city;
    }

    public String getStreet() {
        return this.street;
    }

    public int getHouse() {
        return this.house;
    }

    public int getFlat() {
        return this.flat;
    }



    @Override
    public String toString() {
        return getLastName() + " " + getFirstName() + " " + getPatronymic();
    }
}

