package com.example.demo.validator.generic;

import br.com.patterncommons.abstracts.GenericValidator;
import com.example.demo.model.Person;
import com.example.demo.validator.object.PersonValidator;
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
                .addingFailMessage((person, birthday, validatorObjectCustom) -> validatorObjectCustom.addErrorMessage("Birthday não pode ser nulo."));
    }

}
