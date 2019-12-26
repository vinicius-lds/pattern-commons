package br.com.patterncommons.abstracts;

import br.com.patterncommons.concretes.ObjectPool;
import br.com.patterncommons.concretes.validator.InnerFieldValidator;
import br.com.patterncommons.concretes.validator.InnerGenericValidator;
import br.com.patterncommons.concretes.validator.InnerValidator;
import br.com.patterncommons.concretes.validator.ValidatorObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class Validator<T, K extends ValidatorObject> {

    @Autowired
    protected ObjectPool<K> validatorObjectPool;

    private final List<InnerGenericValidator> innerGenericValidators;

    private final List<InnerFieldValidator> fieldValidators;

    private final List<InnerValidator> innerValidators;

    public Validator() {
        this.innerGenericValidators = new ArrayList<>();
        this.fieldValidators = new ArrayList<>();
        this.innerValidators = new ArrayList<>();
    }

    public boolean validate(@Nullable T object) {
        return validatorObjectPool.getWith(validatorObject -> {
            return validate(object, validatorObject);
        });
    }

    public boolean validate(@Nullable T object, @NonNull K validatorObject) {
        var result = new AtomicBoolean(true);
        this.innerGenericValidators
                .forEach(innerGenericValidator -> result.set(result.get() & innerGenericValidator.validateFieldValueFromObject(object, validatorObject)));
        this.fieldValidators
                .forEach(fieldValidator -> result.set(result.get() & fieldValidator.validateObject(object, validatorObject)));
        this.innerValidators
                .forEach(innerValidator -> result.set(result.get() & innerValidator.validateInnerObjectFrom(object, validatorObject)));
        return result.get();
    }

    public InnerFieldValidator<T, K> addFieldValidator(FieldValidator<T, K> fieldValidator) {
        var innerFieldValidator = new InnerFieldValidator<>(fieldValidator);
        this.fieldValidators.add(innerFieldValidator);
        return innerFieldValidator;
    }

    public <U> InnerValidator<T, K, U> addValidator(Validator<U, K> validator) {
        var innerValidator = new InnerValidator<T, K, U>(validator);
        this.innerValidators.add(innerValidator);
        return innerValidator;
    }

    public <U> InnerGenericValidator<T, K, U> addGenericValidator(GenericValidator<U> genericValidator) {
        var innerGenericValidator = new InnerGenericValidator<T, K, U>(genericValidator);
        this.innerGenericValidators.add(innerGenericValidator);
        return innerGenericValidator;
    }

    @PostConstruct
    public abstract void initialize();

}
