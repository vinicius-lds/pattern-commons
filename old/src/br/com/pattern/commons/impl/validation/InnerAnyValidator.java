package br.com.pattern.commons.impl.validation;

import br.com.pattern.commons.abstracts.Validator;
import br.com.pattern.commons.concretes.ObjectPool;
import br.com.pattern.commons.impl.di.DependencyInjection;
import br.com.pattern.commons.impl.validation.entity.AnyEntity;
import br.com.pattern.commons.impl.validation.entity.AnyInnerEntity;

public class InnerAnyValidator extends Validator<AnyInnerEntity, ValidatorObjectImpl> {

    private ObjectPool<ValidatorObjectImpl> validatorObjectPool = new ObjectPool<>(ValidatorObjectImpl::new);

    @Override
    public void initialize() {
        DependencyInjection.di().get(AnyValidator.class).addInnerValidator(this).using(AnyEntity::getAnyInnerEntity);
    }

    @Override
    public ObjectPool<ValidatorObjectImpl> getValidatorObjectPool() {
        return validatorObjectPool;
    }

}
