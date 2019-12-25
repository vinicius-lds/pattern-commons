package com.example.demo.validator.object;

import br.com.patterncommons.abstracts.Validator;
import br.com.patterncommons.concretes.ValidatorObject;
import com.example.demo.model.Address;
import com.example.demo.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddressValidator extends Validator<Address, ValidatorObject> {

    @Autowired
    private PersonValidator personValidator;

    @Override
    public void setBeanName(String s) {
        personValidator.addInnerValidator(this).using(Person::getAddress);
    }
}
