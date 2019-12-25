package com.example.demo.validator.generic;

import br.com.patterncommons.abstracts.GenericValidator;
import com.example.demo.model.Address;
import com.example.demo.model.Employee;
import com.example.demo.model.Person;
import com.example.demo.validator.object.AddressValidator;
import com.example.demo.validator.object.EmployeeValidator;
import com.example.demo.validator.object.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotNullValidator extends GenericValidator<Object> {

    @Autowired
    private EmployeeValidator employeeValidator;

    @Autowired
    private PersonValidator personValidator;

    @Autowired
    private AddressValidator addressValidator;

    @Override
    public boolean validate(Object object) {
        return object != null;
    }

    @Override
    public void initialize() {
        employeeValidator.addGenericValidator(this)
                .using(Employee::getEmployeeId)
                .addingFailMessage((fieldValue, validatorObject) -> validatorObject.addErrorMessage("EmployeeId não pode ser nulo."));

        personValidator.addGenericValidator(this)
                .using(Person::getFirstName)
                .addingFailMessage((fieldValue, validatorObject) -> validatorObject.addErrorMessage("FirstName não pode ser nulo."));

        addressValidator.addGenericValidator(this)
                .using(Address::getStreetName)
                .addingFailMessage((fieldValue, validatorObject) -> validatorObject.addErrorMessage("StreetName não poder ser nulo."));
    }

}
