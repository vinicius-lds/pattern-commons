package br.com.patterncommons.validationapi;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public interface FieldValidator<T, K extends ValidatorObject> {

    boolean validate(@Nullable T object, @NonNull K validatorObject);

}
