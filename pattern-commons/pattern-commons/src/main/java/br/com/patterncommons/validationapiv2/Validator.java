package br.com.patterncommons.validationapiv2;

import br.com.patterncommons.concretes.ObjectPool;

public abstract class Validator<T, K extends ValidatorObject> {

    private ObjectPool<K> validatorObjectPool;
    private ValidationEvent<K> postValidation;

    public Validator(ObjectPool<K> validatorObjectPool) {
        this.validatorObjectPool = validatorObjectPool;
    }

    public Validator(ObjectPool<K> validatorObjectPool, ValidationEvent<K> postValidation) {
        this.validatorObjectPool = validatorObjectPool;
        this.postValidation = postValidation;
    }

    public boolean validate(T object) {
        return validatorObjectPool.getWith(validatorObject -> {
            validate(object, validatorObject);
            if (postValidation != null) {
                postValidation.handle(validatorObject);
            }
            return validatorObject.isValid();
        });
    }

    public abstract boolean validate(T object, K validatorObject);

}
