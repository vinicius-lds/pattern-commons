package br.com.patterncommons.validationapiv2;

public interface GenericValidator<T> {

    boolean validate(T object);

}
