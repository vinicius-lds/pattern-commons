package br.com.pattern.commons.impl.validation;

import br.com.pattern.commons.abstracts.Validator;
import br.com.pattern.commons.concretes.ObjectPool;
import br.com.pattern.commons.impl.validation.entity.AnyEntity;

public class AnyValidator extends Validator<AnyEntity, ValidatorObjectImpl> {

    private ObjectPool<ValidatorObjectImpl> validatorObjectPool = new ObjectPool<>(ValidatorObjectImpl::new);

    @Override
    public void initialize() {

    }

    @Override
    public ObjectPool<ValidatorObjectImpl> getValidatorObjectPool() {
        return validatorObjectPool;
    }
}
