package br.com.pattern.commons.interfaces;

public interface GenericValidator<T> {
    boolean validate(T object);
}
