package com.mygenerator.app.model;

import java.util.Date;

public class Person {
    private String firstName;
    private String lastName;
    private String patronymic;
    private Date birthDate;
    private long inn;
    private String country;

    public Person(String lastName, String firstName, String patronymic, Date birthDate, long inn, String country) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.birthDate = birthDate;
        this.inn = inn;
        this.country = country;
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



    @Override
    public String toString() {
        return getLastName() + " " + getFirstName() + " " + getPatronymic();
    }
}