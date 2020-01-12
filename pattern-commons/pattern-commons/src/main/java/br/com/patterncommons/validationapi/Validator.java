package br.com.patterncommons.validationapi;

import br.com.patterncommons.concretes.ObjectPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class Validator<T, K extends ValidatorObject> {

    @Autowired
    protected ObjectPool<K> validatorObjectPool;

    private final List<GenericValidatorDecorator<T, K, ?>> genericValidators;
    private final List<FieldValidatorDecorator<T, K>> fieldValidators;
    private final List<ValidatorDecorator<T, K, ?>> validators;

    public Validator() {
        this.genericValidators = new ArrayList<>();
        this.fieldValidators = new ArrayList<>();
        this.validators = new ArrayList<>();
    }

    /**
     * Validates a nullable {@link T} object.
     *
     * @param object {@link T} object to be validated.
     * @return true if the object is valid, false otherwise.
     */
    public boolean validate(@NonNull T object) {
        return validatorObjectPool.getWith(validatorObject -> validate(object, validatorObject));
    }

    /**
     * Validates a nullable {@link T} object with the help of a {@link K} validator helper object.
     *
     * @param object          {@link T} object to be validated.
     * @param validatorObject {@link K} object validator helper.
     * @return true if the object is valid, false otherwise.
     */
    public boolean validate(@NonNull T object, @NonNull K validatorObject) {
        this.genericValidators.forEach(genericValidator -> genericValidator.validate(object, validatorObject));
        this.fieldValidators.forEach(fieldValidator -> fieldValidator.validate(object, validatorObject));
        this.validators.forEach(validator -> validator.validate(object, validatorObject));
        return validatorObject.isValid();
    }

    public Validator<T, K> addGenericValidators(Consumer<GenericValidatorDecoratorCollectionBuilder<T, K>> genericValidatorDecoratorCollectionBuilderConsumer) {
        var genericValidatorDecoratorCollectionBuilder = new GenericValidatorDecoratorCollectionBuilder<T, K>();
        genericValidatorDecoratorCollectionBuilderConsumer.accept(genericValidatorDecoratorCollectionBuilder);
        var genericValidatorDecoratorCollection = genericValidatorDecoratorCollectionBuilder.build();
        this.genericValidators.addAll(genericValidatorDecoratorCollection);
        return this;
    }

}
