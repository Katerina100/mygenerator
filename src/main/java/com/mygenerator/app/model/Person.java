package com.mygenerator.app.model;

import java.util.Date;

public class Person {
    private String firstName;
    private String lastName;
    private String patronymic;
    private Date birthDate;
    private String inn;
    private String country;
    private String region;
    private String city;
    private String street;
    private int house;
    private int flat;
    private String index;

    public Person(String lastName, String firstName, String patronymic, Date birthDate, String inn, String country,
            String region, String city, String street, int house, int flat, String index) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.birthDate = birthDate;
        this.inn = inn;
        this.country = country;
        this.city = city;
        this.region = region;
        this.street = street;
        this.house = house;
        this.flat = flat;
        this.index = index;

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

    public Date getBirthDate() {
        return this.birthDate;
    }

    public String getInn() {
        return this.inn;
    }

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

    public String getIndex() {
        return this.index;
    }

    @Override
    public String toString() {
        return getLastName() + " " + getFirstName() + " " + getPatronymic();
    }
}

