package com.example.demo.validator.object;

import br.com.patterncommons.validationapi.Validator;
import br.com.patterncommons.validationapi.ValidatorObject;
import com.example.demo.model.Address;
import com.example.demo.model.Employee;
import com.example.demo.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddressValidator extends Validator<Address, ValidatorObject> {

    @Autowired
    private PersonValidator personValidator;

    @Autowired
    private EmployeeValidator employeeValidator;

    @Override
    public void initialize() {
        employeeValidator.addValidator(this)
                .when(Employee::getSubscriptionType)
                .in(Employee.SubscriptionType.CNPJ)
                .using(employee -> employee.getPerson().getAddress());

        personValidator.addValidator(this)
                .when(Person::getAnyEnum)
                .in(Person.AnyEnum.ANY_OTHER_VALUE)
                .using(Person::getAddress);
    }
}
