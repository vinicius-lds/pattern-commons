package br.com.patterncommons.validationapi;

public class ValidatorSupplier<V, S, F> {

    private V validator;
    private S supplier;
    private F validationFail;

    public ValidatorSupplier<V, S, F> validator(V validator) {
        this.validator = validator;
        return this;
    }

    public ValidatorSupplier<V, S, F> usingValue(S supplier) {
        this.supplier = supplier;
        return this;
    }

    public ValidatorSupplier<V, S, F> usingValidationErrorHandler(F validationFail) {
        this.validationFail = validationFail;
        return this;
    }

    public V getValidator() {
        return validator;
    }

    public S getSupplier() {
        return supplier;
    }

    public F getValidationFail() {
        return validationFail;
    }
}
