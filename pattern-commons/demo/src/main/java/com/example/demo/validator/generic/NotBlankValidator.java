package com.example.demo.validator.generic;

import br.com.patterncommons.abstracts.GenericValidator;
import com.example.demo.model.Address;
import com.example.demo.model.Employee;
import com.example.demo.model.Person;
import com.example.demo.validator.object.AddressValidator;
import com.example.demo.validator.object.EmployeeValidator;
import com.example.demo.validator.object.PersonValidator;
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
                .addingFailMessage((employee, employeeId, validatorObjectCustom) -> validatorObjectCustom.addErrorMessage("EmployeeId não pode estar em branco."));

        personValidator.addGenericValidator(this)
                .using(Person::getFirstName)
                .addingFailMessage((employee, firstName, validatorObjectCustom) -> validatorObjectCustom.addErrorMessage("FirstName não pode estar em branco."));

        addressValidator.addGenericValidator(this)
                .using(Address::getStreetName)
                .addingFailMessage((employee, streetName, validatorObjectCustom) -> validatorObjectCustom.addErrorMessage("StreetName não poder estar em branco."));

    }
}
