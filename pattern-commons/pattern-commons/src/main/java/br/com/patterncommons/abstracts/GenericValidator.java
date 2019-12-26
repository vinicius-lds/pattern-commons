package br.com.patterncommons.abstracts;

import org.springframework.lang.Nullable;

import javax.annotation.PostConstruct;

public abstract class GenericValidator<T> {

    public abstract boolean validate(@Nullable T object);

    @PostConstruct
    public abstract void initialize();

}
