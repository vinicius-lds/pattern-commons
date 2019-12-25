package br.com.pattern.commons;

import br.com.pattern.commons.abstracts.FieldValidator;
import br.com.pattern.commons.abstracts.Validator;
import br.com.pattern.commons.impl.validation.AnyValidator;
import br.com.pattern.commons.impl.validation.InnerAnyValidator;
import br.com.pattern.commons.impl.validation.ValidatorObjectImpl;
import br.com.pattern.commons.impl.validation.entity.AnyEntity;
import br.com.pattern.commons.impl.validation.entity.AnyInnerEntity;
import br.com.pattern.commons.impl.validation.fieldvalidator.InnerNameFieldValidator;
import br.com.pattern.commons.impl.validation.fieldvalidator.NameFieldValidator;

public class Main {

    public static void main(String[] args) {

        AnyEntity anyEntity = new AnyEntity();

        // Validador principal
        Validator<AnyEntity, ValidatorObjectImpl> validator = new AnyValidator();

        // Validador de campo do validador principal
        FieldValidator<AnyEntity, ValidatorObjectImpl> fieldValidator = new NameFieldValidator();
        validator.addFieldValidator(fieldValidator);

        // Validador interno do validador principal
        Validator<AnyInnerEntity, ValidatorObjectImpl> innerEntityValidator = new InnerAnyValidator();
        validator.addInnerValidator(innerEntityValidator).using(AnyEntity::getAnyInnerEntity);

        // Validador de campo do validador interno
        FieldValidator<AnyInnerEntity, ValidatorObjectImpl> innerFieldValidator = new InnerNameFieldValidator();
        innerEntityValidator.addFieldValidator(innerFieldValidator);

        System.out.println(validator.validate(anyEntity));


    }


}
