package br.com.patterncommons.validationapi;

import org.springframework.lang.Nullable;

public interface GenericValidator<T> {

    boolean validate(@Nullable T object);

}
