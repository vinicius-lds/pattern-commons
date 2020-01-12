package br.com.patterncommons.validationapi;

import org.apache.logging.log4j.util.TriConsumer;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class GenericValidatorDecoratorBuilder<T, K extends ValidatorObject, U> {

    private GenericValidator<U> genericValidator;
    private TriConsumer<T, U, K> failHandler;

    private Action onNonReachableValueAction;
    private Function<T, U> valueSupplierFunction;

    private Action onEnumProviderNullPointerAction;
    private Function<T, ? extends Enum> enumProvider;

    private List<? extends Enum> validEnums;

    private Action onNullValueAction;
    private BiConsumer<T, K> failingNullValueConsumer;


    public GenericValidatorDecoratorBuilder(GenericValidator<U> genericValidator) {
        this.genericValidator = genericValidator;
    }

    public GenericValidatorDecorator<T, K, U> build() {

        var genericValidatorDecorator = new GenericValidatorDecorator<T, K, U>(this.genericValidator);
        genericValidatorDecorator.setFailHandler(this.failHandler);
        genericValidatorDecorator.setOnNonReachebleValueAction(this.onNonReachableValueAction);
        genericValidatorDecorator.setValueSupplierFunction(this.valueSupplierFunction);
        genericValidatorDecorator.setOnEnumProviderNullPointerAction(this.onEnumProviderNullPointerAction);
        genericValidatorDecorator.setEnumProvider(this.enumProvider);
        genericValidatorDecorator.setValidEnums(this.validEnums);
        genericValidatorDecorator.setOnNullValueAction(this.onNullValueAction);
        genericValidatorDecorator.setFailingNullValueConsumer(this.failingNullValueConsumer);

        return null;
    }

    public <E extends Enum> GenericValidatorDecoratorBuilder<T, K, U> when(Function<T, E> enumProvider) {
        this.enumProvider = enumProvider;
        return this;
    }

    public <E extends Enum> GenericValidatorDecoratorBuilder<T, K, U> in(E... validEnums) {
        this.validEnums = Arrays.asList(validEnums);
        return this;
    }

    public GenericValidatorDecoratorBuilder<T, K, U> orIsNonReachable() {
        this.onEnumProviderNullPointerAction = Action.ACCEPT;
        return this;
    }

    public GenericValidatorDecoratorBuilder<T, K, U> acceptingNullValue() {
        this.onNullValueAction = Action.ACCEPT;
        return this;
    }

    public GenericValidatorDecoratorBuilder<T, K, U> failingNullValue(BiConsumer<T, K> failingNullValueConsumer) {
        this.onNullValueAction = Action.FAIL;
        this.failingNullValueConsumer = failingNullValueConsumer;
        return this;
    }

    public GenericValidatorDecoratorBuilder<T, K, U> acceptingOnUnreachableSupplier() {
        this.onNonReachableValueAction = Action.ACCEPT;
        return this;
    }

    public GenericValidatorDecoratorBuilder<T, K, U> failingOnUnreachableSupplier() {
        this.onNonReachableValueAction = Action.FAIL;
        return this;
    }

    public GenericValidatorDecoratorBuilder<T, K, U> suppliedBy(Function<T, U> suppliedBy) {
        this.valueSupplierFunction = suppliedBy;
        return this;
    }

    public GenericValidatorDecoratorBuilder<T, K, U> withFailHandler(TriConsumer<T, U, K> failHandler) {
        this.failHandler = failHandler;
        return this;
    }

}
