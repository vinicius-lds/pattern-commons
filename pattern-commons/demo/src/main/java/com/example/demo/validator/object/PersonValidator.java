package com.example.demo.validator.object;

import br.com.patterncommons.validationapi.Validator;
import br.com.patterncommons.validationapi.ValidatorObject;
import com.example.demo.model.Employee;
import com.example.demo.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersonValidator extends Validator<Person, ValidatorObject> {

    @Autowired
    private EmployeeValidator employeeValidator;

    @Override
    public void initialize() {
        employeeValidator.addValidator(this).using(Employee::getPerson);
    }
}
