package br.com.patterncommons.validationapi;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class InnerValidator<T, K extends ValidatorObject, U> implements EnumConditionalBehavior<T> {

    private Function<T, U> innerObjectSupplier;
    private Validator<U, K> innerValidator;
    private NullArgumentOnSupplierFunctionRule nullArgumentOnSupplierFunctionRule;
    private List<EnumConditional<T, ? extends EnumConditionalBehavior<T>>> enumConditionals;

    InnerValidator(Validator<U, K> validator) {
        this.nullArgumentOnSupplierFunctionRule = NullArgumentOnSupplierFunctionRule.ACCEPT;
        this.innerValidator = validator;
    }

    public InnerValidator<T, K, U> failOnNullArgumentOnSupplierFunction() {
        this.nullArgumentOnSupplierFunctionRule = NullArgumentOnSupplierFunctionRule.FAIL;
        return this;
    }

    public InnerValidator<T, K, U> using(Function<T, U> innerObjectSupplier) {
        this.innerObjectSupplier = innerObjectSupplier;
        return this;
    }

    public boolean validateInnerObjectFrom(T object, K validatorObject) {
        if (object == null) {
            return this.nullArgumentOnSupplierFunctionRule == NullArgumentOnSupplierFunctionRule.ACCEPT;
        } else {
            if (this.enumConditionals == null || this.enumConditionals.stream().allMatch(enumConditional -> enumConditional.hasValidEnum(object))) {
                return this.innerValidator.validate(this.innerObjectSupplier.apply(object), validatorObject);
            } else {
                return true;
            }
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
}
