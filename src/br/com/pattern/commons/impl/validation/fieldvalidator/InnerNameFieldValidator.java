package br.com.pattern.commons.impl.validation.fieldvalidator;

import br.com.pattern.commons.abstracts.FieldValidator;
import br.com.pattern.commons.impl.di.DependencyInjection;
import br.com.pattern.commons.impl.validation.InnerAnyValidator;
import br.com.pattern.commons.impl.validation.ValidatorObjectImpl;
import br.com.pattern.commons.impl.validation.entity.AnyInnerEntity;

public class InnerNameFieldValidator extends FieldValidator<AnyInnerEntity, ValidatorObjectImpl> {

    @Override
    public void initialize() {
        DependencyInjection.di().get(InnerAnyValidator.class).addFieldValidator(this);
    }

    @Override
    public boolean validate(AnyInnerEntity object, ValidatorObjectImpl validatorObject) {
        System.out.println("InnerNameFieldValidator");
        return true;
    }

}
