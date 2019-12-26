package br.com.patterncommons.validationapi;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.annotation.PostConstruct;

/**
 * Validate's a field inside a object.
 *
 * @param <T> The object type to be validated.
 * @param <K> The object validator helper.
 */
public abstract class FieldValidator<T, K extends ValidatorObject> {

    /**
     * Given a {@link T} object, validate's if a certain field is valid or not
     *
     * @param object          {@link T} object containing the field to be validated
     * @param validatorObject {@link K} validation helper object, used to set error messages
     * @return true if the field validated is valid, false otherwise
     */
    public abstract boolean validate(@Nullable T object, @NonNull K validatorObject);

    /**
     * Method where the the current {@link FieldValidator<T, K>} subscribe's into {@link Validator}'s, to be used in
     * the validation process.
     */
    @PostConstruct
    public abstract void initialize();

}
