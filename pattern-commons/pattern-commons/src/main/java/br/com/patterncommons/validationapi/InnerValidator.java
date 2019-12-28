package br.com.patterncommons.validationapi;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class InnerValidator<T, K extends ValidatorObject, U> implements EnumConditionalBehavior<T>, ValueSupplierBehavior<T, K, U> {

    private ValueSupplier<T, K, U, ?> valueSupplier;
    private Validator<U, K> innerValidator;
    private List<EnumConditional<T, ? extends EnumConditionalBehavior<T>>> enumConditionals;

    InnerValidator(Validator<U, K> validator) {
        this.innerValidator = validator;
    }

    public ValueSupplier<T, K, U, InnerValidator<T, K, U>> using(Function<T, U> innerObjectSupplier) {
        return new ValueSupplier<>(innerObjectSupplier, this);
    }

    public InnerValidator<T, K, U> usingValidator(Validator<U, K> validator) {
        this.innerValidator = validator;
        return this;
    }

    public boolean validateInnerObjectFrom(T object, K validatorObject) {
        if (this.enumConditionals == null || this.enumConditionals.stream().allMatch(enumConditional -> enumConditional.hasValidEnum(object))) {
            return this.valueSupplier.getAndValidate(object, validatorObject, suppliedValue -> innerValidator.validate(suppliedValue, validatorObject));
        } else {
            return true;
        }
    }

    public EnumConditional<T, InnerValidator<T, K, U>> when(Function<T, ? extends Enum> enumProvider) {
        return new EnumConditional<>(enumProvider, this);
    }

    @Override
    public void addEnumConditional(EnumConditional<T, ? extends EnumConditionalBehavior<T>> enumConditional) {
        if (this.enumConditionals == null) {
            this.enumConditionals = new ArrayList<>(1);
        }
        this.enumConditionals.add(enumConditional);
    }

    @Override
    public void setValueSupplier(ValueSupplier<T, K, U, ?> valueSupplier) {
        this.valueSupplier = valueSupplier;
    }
}
