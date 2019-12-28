package br.com.patterncommons.validationapi;

import org.apache.logging.log4j.util.TriConsumer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class InnerGenericValidator<T, K extends ValidatorObject, U, P extends InnerGenericValidatorContainer<T, K, U>> implements EnumConditionalBehavior<T> {

    private final P caller;
    private List<GenericValidator<U>> genericValidators;
    private List<Function<T, U>> fieldValueSupplier;

    private TriConsumer<T, U, K> failMessageSetter;
    private List<EnumConditional<T, ? extends EnumConditionalBehavior<T>>> enumConditionals;
    private Action nullValueAction;
    private BiConsumer<T, K> nullValueConsumer;
    private Action nonReachebleAction;
    private BiConsumer<T, K> nonReachebleConsumer;


    InnerGenericValidator(P caller) {
        this.caller = caller;
    }

    public EnumConditional<T, InnerGenericValidator<T, K, U, P>> when(Function<T, ? extends Enum> enumProvider) {
        return new EnumConditional<>(enumProvider, this);
    }

    @Override
    public void addEnumConditional(EnumConditional<T, ? extends EnumConditionalBehavior<T>> enumConditional) {
        if (this.enumConditionals == null) {
            this.enumConditionals = new ArrayList<>(1);
        }
        this.enumConditionals.add(enumConditional);
    }

    public InnerGenericValidator<T, K, U, P> failingNullValue(BiConsumer<T, K> nullValueConsumer) {
        this.nullValueAction = Action.FAIL;
        this.nullValueConsumer = nullValueConsumer;
        return this;
    }

    public InnerGenericValidator<T, K, U, P> acceptingNullValue() {
        this.nullValueAction = Action.ACCEPT;
        return this;
    }

    public InnerGenericValidator<T, K, U, P> failingNonReachebleValue(BiConsumer<T, K> nullValueConsumer) {
        this.nonReachebleAction = Action.FAIL;
        this.nonReachebleConsumer = nullValueConsumer;
        return this;
    }

    public InnerGenericValidator<T, K, U, P> acceptingNonReachebleValue() {
        this.nonReachebleAction = Action.ACCEPT;
        return this;
    }

    public GenericValidation<T, K, U, P> withValidatorSuppliedBy(GenericValidator<U> genericValidator, Function<T, U> fieldValueSupplier) {
        return new GenericValidation();
    }

    public P submitGenericValidators() {
        this.caller.addInnerGenericValidator(this);
        return this.caller;
    }

    public boolean validate(T object) {
        return false;
    }


    private boolean isNonReachableValue(Function<T, U> supplier, T object) {
        try {
            supplier.apply(object);
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    private boolean shouldFailNonReachable() {
        return this.nonReachebleAction == Action.FAIL;
    }

    private boolean isNullValue(T object) {
        return object == null;
    }

    private boolean shouldFailNullValue() {
        return this.nullValueAction == Action.FAIL;
    }

    private boolean enumConditionalsAllowValidatorsToExecute(T object) {
        if (this.enumConditionals == null) {
            return true;
        } else {
            return this.enumConditionals.stream().allMatch(enumConditional -> enumConditional.hasValidEnum(object));
        }
    }

}