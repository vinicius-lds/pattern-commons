package com.example.demo.model;

public class Employee {

    private Person person;
    private String employeeId;
    private String validationMessage;

    public Employee() {
    }

    public Employee(Person person, String employeeId, String validationMessage) {
        this.person = person;
        this.employeeId = employeeId;
        this.validationMessage = validationMessage;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getValidationMessage() {
        return validationMessage;
    }

    public void setValidationMessage(String validationMessage) {
        this.validationMessage = validationMessage;
    }
}
