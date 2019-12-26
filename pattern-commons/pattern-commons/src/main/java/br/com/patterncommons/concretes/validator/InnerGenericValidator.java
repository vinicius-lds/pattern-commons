package br.com.patterncommons.concretes.validator;

import br.com.patterncommons.abstracts.GenericValidator;
import org.apache.logging.log4j.util.TriConsumer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class InnerGenericValidator<T, K extends ValidatorObject, U> implements EnumConditionalBehavior<T> {

    private GenericValidator<U> genericValidator;
    private Function<T, U> fieldValueSupplier;
    private TriConsumer<T, U, K> failMessageSetter;
    private NullArgumentOnSupplierFunctionRule nullArgumentOnSupplierFunctionRule = NullArgumentOnSupplierFunctionRule.ACCEPT;
    private List<EnumConditional<T, ? extends EnumConditionalBehavior<T>>> enumConditionals;

    public InnerGenericValidator(GenericValidator<U> genericValidator) {
        this.genericValidator = genericValidator;
    }

    public InnerGenericValidator<T, K, U> using(Function<T, U> fieldValueSupplier) {
        this.fieldValueSupplier = fieldValueSupplier;
        return this;
    }

    public InnerGenericValidator<T, K, U> failOnNullArgumentOnSupplierFunction() {
        this.nullArgumentOnSupplierFunctionRule = NullArgumentOnSupplierFunctionRule.FAIL;
        return this;
    }

    public void addingFailMessage(TriConsumer<T, U, K> failMessageSetter) {
        this.failMessageSetter = failMessageSetter;
    }

    public boolean validateFieldValueFromObject(T object, K validatorObject) {
        if (object == null) {
            return this.nullArgumentOnSupplierFunctionRule == NullArgumentOnSupplierFunctionRule.ACCEPT;
        }

        if ((this.enumConditionals == null || this.enumConditionals.stream().allMatch(enumConditional -> enumConditional.hasValidEnum(object)))) {
            if (!this.genericValidator.validate(this.fieldValueSupplier.apply(object))) {
                this.failMessageSetter.accept(object, this.fieldValueSupplier.apply(object), validatorObject);
                return false;
            }
        }
        return true;
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
}