package br.com.patterncommons.validationapi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class GenericValidatorDecoratorCollectionBuilder<T, K extends ValidatorObject> {

    private Action onEnumProviderNullPointerAction;
    private Function<T, ? extends Enum> enumProvider;

    private List<? extends Enum> validEnums;

    private Action onNullValueAction;
    private BiConsumer<T, K> failingNullValueConsumer;

    private Action onNonReachebleValueAction;
    private BiConsumer<T, K> failingNonReachebleValueConsumer;

    private List<GenericValidatorDecorator<T, K, ?>> genericValidatorDecorators;

    public GenericValidatorDecoratorCollectionBuilder() {
        this.validEnums = new ArrayList<>();
        this.genericValidatorDecorators = new ArrayList<>();
    }

    public List<GenericValidatorDecorator<T, K, ?>> build() {
        this.genericValidatorDecorators.forEach(genericValidatorDecorator -> {
            genericValidatorDecorator.setOnEnumProviderNullPointerAction(this.onEnumProviderNullPointerAction);
            genericValidatorDecorator.setEnumProvider(this.enumProvider);
            genericValidatorDecorator.setValidEnums(this.validEnums);
            genericValidatorDecorator.setOnNullValueAction(this.onNullValueAction);
            genericValidatorDecorator.setFailingNullValueConsumer(this.failingNullValueConsumer);
            genericValidatorDecorator.setOnNonReachebleValueAction(this.onNonReachebleValueAction);
            genericValidatorDecorator.setFailingNonReachebleValueConsumer(this.failingNonReachebleValueConsumer);
        });
        return this.genericValidatorDecorators;
    }

    public <E extends Enum> GenericValidatorDecoratorCollectionBuilder<T, K> when(Function<T, E> enumProvider) {
        this.enumProvider = enumProvider;
        return this;
    }

    public <E extends Enum> GenericValidatorDecoratorCollectionBuilder<T, K> in(E... validEnums) {
        this.validEnums = Arrays.asList(validEnums);
        return this;
    }

    public GenericValidatorDecoratorCollectionBuilder<T, K> orIsNonReachable() {
        this.onEnumProviderNullPointerAction = Action.ACCEPT;
        return this;
    }

    public GenericValidatorDecoratorCollectionBuilder<T, K> acceptingNullValue() {
        this.onNullValueAction = Action.ACCEPT;
        return this;
    }

    public GenericValidatorDecoratorCollectionBuilder<T, K> failingNullValue(BiConsumer<T, K> failingNullValueConsumer) {
        this.onNullValueAction = Action.FAIL;
        this.failingNullValueConsumer = failingNullValueConsumer;
        return this;
    }

    public GenericValidatorDecoratorCollectionBuilder<T, K> acceptingNonReachebleValue() {
        this.onNonReachebleValueAction = Action.ACCEPT;
        return this;
    }

    public GenericValidatorDecoratorCollectionBuilder<T, K> failingNonReachebleValue(BiConsumer<T, K> failingNonReachebleValueConsumer) {
        this.onNonReachebleValueAction = Action.FAIL;
        this.failingNonReachebleValueConsumer = failingNonReachebleValueConsumer;
        return this;
    }

    public <U> GenericValidatorDecoratorCollectionBuilder<T, K> withGenericValidator(GenericValidator<U> genericValidator, Consumer<GenericValidatorDecoratorBuilder<T, K, U>> validatorDecoratorBuilderConsumer) {
        GenericValidatorDecoratorBuilder<T, K, U> genericValidatorDecoratorBuilder = new GenericValidatorDecoratorBuilder<>(genericValidator);
        validatorDecoratorBuilderConsumer.accept(genericValidatorDecoratorBuilder);
        var genericValidatorDecorator = genericValidatorDecoratorBuilder.build();
        this.genericValidatorDecorators.add(genericValidatorDecorator);
        return this;
    }

}
