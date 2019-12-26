package br.com.patterncommons.abstracts;

import br.com.patterncommons.concretes.validator.ValidatorObject;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.annotation.PostConstruct;

public abstract class FieldValidator<T, K extends ValidatorObject> {

    public abstract boolean validate(@Nullable T object, @NonNull K validatorObject);

    @PostConstruct
    public abstract void initialize();

}
