package br.com.patterncommons.validationapi;

import org.apache.logging.log4j.util.TriConsumer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class InnerGenericValidator<T, K extends ValidatorObject, U> implements EnumConditionalBehavior<T>, ValueSupplierBehavior<T, U> {

    private GenericValidator<U> genericValidator;
    private ValueSupplier<T, U, ?> valueSupplier;
    private TriConsumer<T, U, K> failMessageSetter;
    private List<EnumConditional<T, ? extends EnumConditionalBehavior<T>>> enumConditionals;

    InnerGenericValidator(GenericValidator<U> genericValidator) {
        this.genericValidator = genericValidator;
    }

    public ValueSupplier<T, U, InnerGenericValidator<T, K, U>> using(Function<T, U> fieldValueSupplier) {
        return new ValueSupplier<>(fieldValueSupplier, this);
    }

    public InnerGenericValidator<T, K, U> addingFailMessage(TriConsumer<T, U, K> failMessageSetter) {
        this.failMessageSetter = failMessageSetter;
        return this;
    }

    public boolean validateFieldValueFromObject(T object, K validatorObject) {
        if ((this.enumConditionals == null || this.enumConditionals.stream().allMatch(enumConditional -> enumConditional.hasValidEnum(object)))) {
            return this.valueSupplier.getAndValidateOrIfInvalid(object, suppliedValue -> {
                if (!this.genericValidator.validate(suppliedValue)) {
                    this.failMessageSetter.accept(object, suppliedValue, validatorObject);
                    return false;
                } else {
                    return true;
                }
            }, validatorObject::setInvalid);
        } else {
            return true;
        }
    }

    public EnumConditional<T, InnerGenericValidator<T, K, U>> when(Function<T, ? extends Enum> enumProvider) {
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
    public void setValueSupplier(ValueSupplier<T, U, ?> valueSupplier) {
        this.valueSupplier = valueSupplier;
    }
}