package com.mygenerator.app.models;

import java.util.Date;

public final class Person {
    private final String lastName;
    private final String firstName;
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

    private Person(Builder builder) {
        this.lastName = builder.lastName;
        this.firstName = builder.firstName;
        this.patronymic = builder.patronymic;
        this.sex = builder.sex;
        this.birthDate = builder.birthDate;
        this.inn = builder.inn;
        this.index = builder.index;
        this.country = builder.country;
        this.region = builder.region;
        this.city = builder.city;
        this.street = builder.street;
        this.house = builder.house;
        this.flat = builder.flat;
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

    public Date getBirthDate() { return this.birthDate; }

    public String getInn() {
        return this.inn;
    }

    public String getIndex() {
        return this.index;
    }

    public String getCountry() { return this.country; }

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

    public static class Builder {
        private String lastName;
        private String firstName;
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

        public Builder() {
        }

        public Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder setPatronymic(String patronymic) {
            this.patronymic = patronymic;
            return this;
        }

        public Builder setSex(String sex) {
            this.sex = sex;
            return this;
        }

        public Builder setBirthDate(Date birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public Builder setInn(String inn) {
            this.inn = inn;
            return this;
        }

        public Builder setIndex(String index) {
            this.index = index;
            return this;
        }

        public Builder setCountry(String country) {
            this.country = country;
            return this;
        }

        public Builder setRegion(String region) {
            this.region = region;
            return this;
        }

        public Builder setCity(String city) {
            this.city = city;
            return this;
        }

        public Builder setStreet(String street) {
            this.street = street;
            return this;
        }

        public Builder setHouse(int house) {
            this.house = house;
            return this;
        }

        public Builder setFlat(int flat) {
            this.flat = flat;
            return this;
        }

        public Person build() {
            return new Person(this);
        }
    }
}
