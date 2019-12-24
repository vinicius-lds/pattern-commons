package br.com.pattern.commons.impl.validation.fieldvalidator;

import br.com.pattern.commons.abstracts.FieldValidator;
import br.com.pattern.commons.impl.di.DependencyInjection;
import br.com.pattern.commons.impl.validation.AnyValidator;
import br.com.pattern.commons.impl.validation.ValidatorObjectImpl;
import br.com.pattern.commons.impl.validation.entity.AnyEntity;

public class NameFieldValidator extends FieldValidator<AnyEntity, ValidatorObjectImpl> {

    @Override
    public void initialize() {
        DependencyInjection.di().get(AnyValidator.class).addFieldValidator(this);
    }

    @Override
    public boolean validate(AnyEntity object, ValidatorObjectImpl validatorObject) {
        System.out.println("NameFieldValidator");
        return true;
    }


}
