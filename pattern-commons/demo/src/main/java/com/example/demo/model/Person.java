package com.example.demo.model;

import java.time.LocalDate;

public class Person {

    private String firstName;
    private String middleName;
    private String lastName;
    private LocalDate birthday;
    private Address address;
    private AnyEnum anyEnum;

    public Person() {
    }

    public Person(String firstName, String middleName, String lastName, LocalDate birthday, Address address, AnyEnum anyEnum) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.address = address;
        this.anyEnum = anyEnum;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public AnyEnum getAnyEnum() {
        return anyEnum;
    }

    public void setAnyEnum(AnyEnum anyEnum) {
        this.anyEnum = anyEnum;
    }

    public enum AnyEnum {
        ANY_VALUE, ANY_OTHER_VALUE
    }
}
