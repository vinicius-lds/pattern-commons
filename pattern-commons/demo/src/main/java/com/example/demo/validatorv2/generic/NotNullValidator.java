package com.example.demo.validatorv2.generic;

import br.com.patterncommons.validationapi.GenericValidator;
import com.example.demo.model.Person;
import com.example.demo.validatorv2.object.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotNullValidator extends GenericValidator<Object> {

    @Autowired
    private PersonValidator personValidator;

    @Override
    public boolean validate(Object object) {
        return object != null;
    }

    @Override
    public void initialize() {
        personValidator.addGenericValidator(this)
                .using(Person::getBirthday)
                .acceptOnNullPointer()
                .addingFailMessage((person, birthday, validatorObjectCustom) -> validatorObjectCustom.addErrorMessage("Birthday não pode ser nulo."));
    }

}
