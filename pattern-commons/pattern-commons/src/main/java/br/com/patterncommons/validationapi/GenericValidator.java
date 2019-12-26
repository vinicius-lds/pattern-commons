package br.com.patterncommons.validationapi;

import org.springframework.lang.Nullable;

import javax.annotation.PostConstruct;

/**
 * Validate's a generic field value.
 *
 * @param <T> The field value type to be validated.
 */
public abstract class GenericValidator<T> {

    /**
     * Given a {@link T} value, validate's if field value is valid or not
     *
     * @param object {@link T} field value to be validated
     * @return true if the field value validated is valid, false otherwise
     */
    public abstract boolean validate(@Nullable T object);

    /**
     * Method where the the current {@link GenericValidator<T>} subscribe's into {@link Validator}'s, to be used in
     * the validation process.
     */
    @PostConstruct
    public abstract void initialize();

}
