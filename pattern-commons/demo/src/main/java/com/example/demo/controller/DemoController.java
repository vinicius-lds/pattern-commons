package com.example.demo.controller;

import com.example.demo.model.Address;
import com.example.demo.model.Employee;
import com.example.demo.model.Person;
import com.example.demo.validator.object.EmployeeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @Autowired
    private EmployeeValidator employeeValidator;

    @GetMapping(path = "demo")
    public Employee helloWorld() {

        var employee = new Employee();
        var person = new Person();
        var address = new Address();

        employee.setPerson(person);
        person.setAddress(address);

        employeeValidator.validate(employee);

        return employee;
    }

}
