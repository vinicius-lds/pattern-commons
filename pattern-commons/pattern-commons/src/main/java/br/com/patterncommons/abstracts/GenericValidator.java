package br.com.patterncommons.abstracts;

import javax.annotation.PostConstruct;

public abstract class GenericValidator<T> {
    public abstract boolean validate(T object);

    @PostConstruct
    public abstract void initialize();
}
