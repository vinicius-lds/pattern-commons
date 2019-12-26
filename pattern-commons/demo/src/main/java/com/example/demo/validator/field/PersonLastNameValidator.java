package com.example.demo.validator.field;

import br.com.patterncommons.validationapi.FieldValidator;
import br.com.patterncommons.validationapi.ValidatorObject;
import com.example.demo.model.Person;
import com.example.demo.validator.object.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersonLastNameValidator extends FieldValidator<Person, ValidatorObject> {

    @Autowired
    private PersonValidator personValidator;

    @Override
    public boolean validate(Person person, ValidatorObject validatorObject) {
        if (person.getLastName() == null || person.getLastName().length() < 3) {
            validatorObject.addErrorMessage("LastName na entidade Person tem que ter pelo menos 3 letras.");
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void initialize() {
        personValidator.addFieldValidator(this);
    }

}
