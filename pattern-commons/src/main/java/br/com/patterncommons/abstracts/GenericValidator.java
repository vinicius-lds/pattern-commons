package br.com.patterncommons.abstracts;

import org.springframework.beans.factory.BeanNameAware;

public abstract class GenericValidator<T> implements BeanNameAware {
    public abstract boolean validate(T object);
}
