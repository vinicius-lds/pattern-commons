package br.com.patterncommons.abstracts;

import br.com.patterncommons.concretes.ObjectPool;
import br.com.patterncommons.concretes.ValidatorObject;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;
import java.util.function.Function;

public abstract class Validator<T, K extends ValidatorObject> {

    @Autowired
    protected ObjectPool<K> validatorObjectPool;

    private final List<InnerGenericValidator> innerGenericValidators;

    private final List<FieldValidator<T, K>> fieldValidators;

    private final List<InnerValidator> innerValidators;

    public Validator() {
        this.innerGenericValidators = new ArrayList<>();
        this.fieldValidators = new ArrayList<>();
        this.innerValidators = new ArrayList<>();
    }

    @PostConstruct
    public abstract void initialize();

    public boolean validate(T object) {
        return validatorObjectPool.with(validatorObject -> {
            return validate(object, validatorObject);
        });
    }

    public boolean validate(T object, K validatorObject) {
        var result = new AtomicBoolean(true);
        this.innerGenericValidators.forEach(innerGenericValidator -> result.set(result.get() & innerGenericValidator.validateFieldValueFromObject(object, validatorObject)));
        this.fieldValidators.forEach(fieldValidator -> result.set(result.get() & fieldValidator.validate(object, validatorObject)));
        this.innerValidators.forEach(innerValidator -> result.set(result.get() & innerValidator.validateInnerObjectFrom(object, validatorObject)));
        return result.get();
    }

    public void addFieldValidator(FieldValidator<T, K> fieldValidator) {
        this.fieldValidators.add(fieldValidator);
    }

    public <P> InnerValidator<P> addInnerValidator(Validator<P, K> validator) {
        var innerValidator = new InnerValidator<>(validator);
        this.innerValidators.add(innerValidator);
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
        private SupplierNullPointerAction supplierNullPointerAction = SupplierNullPointerAction.ACCEPT;

        InnerValidator(Validator<U, K> validator) {
            this.innerValidator = validator;
        }

        public InnerValidator<U> onSupplierNullPointer(SupplierNullPointerAction supplierNullPointerAction) {
            this.supplierNullPointerAction = supplierNullPointerAction;
            return this;
        }

        public void using(Function<T, U> innerObjectSupplier) {
            this.innerObjectSupplier = innerObjectSupplier;
        }

        boolean validateInnerObjectFrom(T object, K validatorObject) {
            U suppliedValue;
            try {
                suppliedValue = this.innerObjectSupplier.apply(object);
            } catch (NullPointerException e) {
                return this.supplierNullPointerAction == SupplierNullPointerAction.ACCEPT;
            }
            return this.innerValidator.validate(suppliedValue, validatorObject);
        }

    }

    public class InnerGenericValidator<U> {

        private GenericValidator<U> genericValidator;
        private Function<T, U> fieldValueSupplier;
        private BiConsumer<T, K> failMessageSetter;
        private SupplierNullPointerAction supplierNullPointerAction = SupplierNullPointerAction.ACCEPT;

        InnerGenericValidator(GenericValidator<U> genericValidator) {
            this.genericValidator = genericValidator;
        }

        public InnerGenericValidator<U> using(Function<T, U> fieldValueSupplier) {
            this.fieldValueSupplier = fieldValueSupplier;
            return this;
        }

        public InnerGenericValidator<U> failOnSupplierNullPointer() {
            this.supplierNullPointerAction = SupplierNullPointerAction.FAIL;
            return this;
        }

        public void addingFailMessage(BiConsumer<T, K> failMessageSetter) {
            this.failMessageSetter = failMessageSetter;
        }

        boolean validateFieldValueFromObject(T object, K validatorObject) {
            U suppliedFieldValue;
            try {
                suppliedFieldValue = this.fieldValueSupplier.apply(object);
            } catch (NullPointerException e) {
                return this.supplierNullPointerAction == SupplierNullPointerAction.ACCEPT;
            }
            if (this.genericValidator.validate(suppliedFieldValue)) {
                return true;
            } else {
                this.failMessageSetter.accept(object, validatorObject);
                return false;
            }
        }

    }

    public enum SupplierNullPointerAction {
        FAIL, ACCEPT
    }

}
