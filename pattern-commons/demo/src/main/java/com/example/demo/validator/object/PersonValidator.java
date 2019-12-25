package com.example.demo.validator.object;

import br.com.patterncommons.abstracts.Validator;
import br.com.patterncommons.concretes.ValidatorObject;
import com.example.demo.model.Employee;
import com.example.demo.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersonValidator extends Validator<Person, ValidatorObject> {

    @Autowired
    private EmployeeValidator employeeValidator;

    @Override
    public void setBeanName(String s) {
        employeeValidator.addInnerValidator(this).using(Employee::getPerson);
    }
}
