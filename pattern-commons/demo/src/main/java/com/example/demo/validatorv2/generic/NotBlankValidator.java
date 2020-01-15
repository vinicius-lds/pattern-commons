package com.example.demo.validatorv2.generic;

import br.com.patterncommons.validationapi.GenericValidator;
import com.example.demo.model.Address;
import com.example.demo.model.Employee;
import com.example.demo.model.Person;
import com.example.demo.validatorv2.object.AddressValidator;
import com.example.demo.validatorv2.object.EmployeeValidator;
import com.example.demo.validatorv2.object.PersonValidator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotBlankValidator extends GenericValidator<String> {

    @Autowired
    private EmployeeValidator employeeValidator;

    @Autowired
    private PersonValidator personValidator;

    @Autowired
    private AddressValidator addressValidator;

    @Override
    public boolean validate(String value) {
        return StringUtils.isNotBlank(value);
    }

    @Override
    public void initialize() {
        employeeValidator.addGenericValidator(this)
                .using(Employee::getEmployeeId)
                .acceptOnNullPointer()
                .addingFailMessage((employee, employeeId, validatorObjectCustom) -> validatorObjectCustom.addErrorMessage("EmployeeId não pode estar em branco."));

        personValidator.addGenericValidator(this)
                .using(Person::getFirstName)
                .acceptOnNullPointer()
                .addingFailMessage((employee, firstName, validatorObjectCustom) -> validatorObjectCustom.addErrorMessage("FirstName não pode estar em branco."));

        addressValidator.addGenericValidator(this)
                .using(Address::getStreetName)
                .acceptOnNullPointer()
                .addingFailMessage((employee, streetName, validatorObjectCustom) -> validatorObjectCustom.addErrorMessage("StreetName não poder estar em branco."));

    }
}
