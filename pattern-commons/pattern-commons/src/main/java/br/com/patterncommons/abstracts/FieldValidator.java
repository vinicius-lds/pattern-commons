package br.com.patterncommons.abstracts;

import br.com.patterncommons.concretes.ValidatorObject;

import javax.annotation.PostConstruct;

public abstract class FieldValidator<T, K extends ValidatorObject> {

    public abstract boolean validate(T object, K validatorObject);

    @PostConstruct
    public abstract void initialize();

}
