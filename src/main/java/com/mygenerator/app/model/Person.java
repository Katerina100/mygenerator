package com.mygenerator.app.model;

import java.util.Date;

public class Person {
    private String firstName;
    private String lastName;
    private String patronymic;
    private Date birthDate;
    private long inn;
    private String country;
    private String region;
    private String city;
    private String street;

    public Person(String lastName, String firstName, String patronymic, Date birthDate, long inn, String country, String region, String city, String street) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.birthDate = birthDate;
        this.inn = inn;
        this.country = country;
        this.city = city;
        this.region = region;
        this.street = street;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPatronymic() {
        return this.patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public Date getBirthDate() {
        return this.birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public long getInn() {
        return this.inn;
    }

    public void setInn(long inn) {
        this.inn = inn;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return this.region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return this.street;
    }

    public void setStreet(String street) {
        this.street = street;
    }


    @Override
    public String toString() {
        return getLastName() + " " + getFirstName() + " " + getPatronymic();
    }
}