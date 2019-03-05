package com.mygenerator.app.models;

import java.util.Date;

public final class Person {
    private final String firstName;
    private final String lastName;
    private final String patronymic;
    private final String sex;
    private final Date birthDate;
    private final String inn;
    private final String index;
    private final String country;
    private final String region;
    private final String city;
    private final String street;
    private final int house;
    private final int flat;


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
