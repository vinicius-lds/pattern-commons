package com.example.demo.validatorv2.object;

import br.com.patterncommons.validationapi.Validator;
import br.com.patterncommons.validationapi.ValidatorObject;
import com.example.demo.model.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddressValidator extends Validator<Address, ValidatorObject> {

    @Autowired
    private PersonValidator personValidator;

    @Autowired
    private EmployeeValidator employeeValidator;


}
