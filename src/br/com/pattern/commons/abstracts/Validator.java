package br.com.pattern.commons.abstracts;

import br.com.pattern.commons.concretes.ObjectPool;
import br.com.pattern.commons.concretes.ValidatorObject;
import br.com.pattern.commons.interfaces.Initializable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class Validator<T, K extends ValidatorObject> implements Initializable {

    private List<InnerGenericValidator> innerGenericValidators;
    private List<FieldValidator<T, K>> fieldValidators;
    private List<InnerValidator> innerValidators;

    public Validator() {
        this.innerGenericValidators = new ArrayList<>();
        this.fieldValidators = new ArrayList<>();
        this.innerValidators = new ArrayList<>();
        this.initialize();
    }

    public abstract ObjectPool<K> getValidatorObjectPool();

    public boolean validate(T object) {
        return getValidatorObjectPool().with(validatorObject -> {
            return validate(object, validatorObject);
        });
    }

    public boolean validate(T object, K validatorObject) {
        var result = new AtomicBoolean(true);
        innerGenericValidators.forEach(innerGenericValidator -> result.set(result.get() & innerGenericValidator.validateFieldValueFromObject(object, validatorObject)));
        fieldValidators.forEach(fieldValidator -> result.set(result.get() & fieldValidator.validate(object, validatorObject)));
        innerValidators.forEach(innerValidator -> result.set(result.get() & innerValidator.validateInnerObjectFrom(object, validatorObject)));
        return result.get();
    }

    public void addFieldValidator(FieldValidator<T, K> fieldValidator) {
        fieldValidators.add(fieldValidator);
    }

    public <P> InnerValidator<P> addInnerValidator(Validator<P, K> validator) {
        var innerValidator = new InnerValidator<>(validator);
        innerValidators.add(innerValidator);
        return innerValidator;
    }

    public <P> InnerGenericValidator<P> addGenericValidator(GenericValidator<P> genericValidator) {
        var innerGenericValidator = new InnerGenericValidator<>(genericValidator);
        this.innerGenericValidators.add(innerGenericValidator);
        return innerGenericValidator;
    }

    public class InnerValidator<U> {

        private Function<T, U> innerObjectSupplier;
        private Validator<U, K> innerValidator;

        InnerValidator(Validator<U, K> validator) {
            this.innerValidator = validator;
        }

        public void using(Function<T, U> innerObjectSupplier) {
            this.innerObjectSupplier = innerObjectSupplier;
        }

        boolean validateInnerObjectFrom(T object, K validatorObject) {
            return innerValidator.validate(innerObjectSupplier.apply(object), validatorObject);
        }

    }

    public class InnerGenericValidator<U> {

        private GenericValidator<U> genericValidator;
        private Function<T, U> fieldValueSupplier;
        private Consumer<K> failMessageSetter;

        InnerGenericValidator(GenericValidator<U> genericValidator) {
            this.genericValidator = genericValidator;
        }

        public InnerGenericValidator<U> using(Function<T, U> fieldValueSupplier) {
            this.fieldValueSupplier = fieldValueSupplier;
            return this;
        }

        public void addingFailMessage(Consumer<K> failMessageSetter) {
            this.failMessageSetter = failMessageSetter;
        }

        boolean validateFieldValueFromObject(T object, K validatorObject) {
            if (!this.genericValidator.validate(fieldValueSupplier.apply(object))) {
                this.failMessageSetter.accept(validatorObject);
                return false;
            } else {
                return true;
            }
        }

    }

}
