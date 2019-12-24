package br.com.pattern.commons.impl.validation.genericvalidator;

import br.com.pattern.commons.abstracts.GenericValidator;
import br.com.pattern.commons.impl.di.DependencyInjection;
import br.com.pattern.commons.impl.validation.AnyValidator;
import br.com.pattern.commons.impl.validation.entity.AnyEntity;

public class NotNullValidator extends GenericValidator<Object> {

    @Override
    public void initialize() {
        DependencyInjection.di().get(AnyValidator.class)
                .addGenericValidator(this)
                .using(AnyEntity::getName)
                .addingFailMessage(validatorObject -> validatorObject.addErrorMessage("Field name cannot be null"));
    }

    @Override
    public boolean validate(Object object) {
        return object != null;
    }

}
