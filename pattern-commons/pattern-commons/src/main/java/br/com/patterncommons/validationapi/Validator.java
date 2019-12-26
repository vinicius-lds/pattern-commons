package br.com.patterncommons.validationapi;

import br.com.patterncommons.concretes.ObjectPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Class responsible for validating a Java object using a helper ValidatorObject.
 *
 * @param <T> The type of object to validate.
 * @param <K> The validator helper object, it's provided by a {@link ObjectPool}, has to be configured as a Spring @Bean.
 */
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

    /**
     * Validates a nullable {@link T} object.
     *
     * @param object {@link T} object to be validated.
     * @return true if the object is valid, false otherwise.
     */
    public boolean validate(@Nullable T object) {
        return validatorObjectPool.getWith(validatorObject -> validate(object, validatorObject));
    }

    /**
     * Validates a nullable {@link T} object with the help of a {@link K} validator helper object.
     *
     * @param object          {@link T} object to be validated.
     * @param validatorObject {@link K} object validator helper.
     * @return true if the object is valid, false otherwise.
     */
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

    /**
     * Add's a new {@link FieldValidator<T, K>} object to be used in the validation process.
     *
     * @param fieldValidator {@link FieldValidator<T, K>} object that is going to be used in the validation process.
     * @return {@link InnerFieldValidator<T, K>} used to help configure the behavior of the {@link FieldValidator<T, K>} in the validation process.
     */
    public InnerFieldValidator<T, K> addFieldValidator(FieldValidator<T, K> fieldValidator) {
        var innerFieldValidator = new InnerFieldValidator<>(fieldValidator);
        this.fieldValidators.add(innerFieldValidator);
        return innerFieldValidator;
    }

    /**
     * Add's a new {@link Validator<U, K>} object to be used in the validation process.
     *
     * @param validator {@link Validator<U, K>} object that is going to be used in the validation process.
     * @param <U>       The object type that the given validator validates.
     * @return a {@link InnerValidator<T, K, U>} used to help configure the behavior of the {@link Validator<U, K>} in the validation process.
     */
    public <U> InnerValidator<T, K, U> addValidator(Validator<U, K> validator) {
        var innerValidator = new InnerValidator<T, K, U>(validator);
        this.innerValidators.add(innerValidator);
        return innerValidator;
    }

    /**
     * Add's a new {@link GenericValidator<U>} object to be used in the validation process.
     *
     * @param genericValidator {@link GenericValidator<U>} object that is going to be used in the validation process.
     * @param <U>              The object type that the given validator validates.
     * @return a {@link InnerValidator<T, K, U>} used to help configure the behavior of the {@link GenericValidator<U>} in the validation process.
     */
    public <U> InnerGenericValidator<T, K, U> addGenericValidator(GenericValidator<U> genericValidator) {
        var innerGenericValidator = new InnerGenericValidator<T, K, U>(genericValidator);
        this.innerGenericValidators.add(innerGenericValidator);
        return innerGenericValidator;
    }

    /**
     * Method where the the current {@link Validator<T, K>} subscribe's into other {@link Validator}'s.
     */
    @PostConstruct
    protected abstract void initialize();

}
