package br.com.patterncommons.validationapi;

import org.apache.logging.log4j.util.TriConsumer;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class GenericValidatorDecorator<T, K extends ValidatorObject, U> extends GenericValidator<T> {


    private Action onEnumProviderNullPointerAction;
    private Function<T, ? extends Enum> enumProvider;

    private List<? extends Enum> validEnums;

    private Action onNullValueAction;
    private BiConsumer<T, K> failingNullValueConsumer;

    private Action onNonReachebleValueAction;
    private BiConsumer<T, K> failingNonReachebleValueConsumer;

    private GenericValidator<U> genericValidator;
    private Function<T, U> valueSupplierFunction;
    private TriConsumer<T, U, K> failHandler;

    public GenericValidatorDecorator(GenericValidator<U> genericValidator) {
        this.setGenericValidator(genericValidator);
    }

    @Override
    public boolean validate(T object) {
        throw new RuntimeException();
    }

    public boolean validate(T object, K validatorObject) {
        return false;
    }

    public void setOnEnumProviderNullPointerAction(Action onEnumProviderNullPointerAction) {
        this.onEnumProviderNullPointerAction = onEnumProviderNullPointerAction;
    }

    public void setEnumProvider(Function<T, ? extends Enum> enumProvider) {
        this.enumProvider = enumProvider;
    }

    public void setValidEnums(List<? extends Enum> validEnums) {
        this.validEnums = validEnums;
    }

    public void setOnNullValueAction(Action onNullValueAction) {
        this.onNullValueAction = onNullValueAction;
    }

    public void setFailingNullValueConsumer(BiConsumer<T, K> failingNullValueConsumer) {
        this.failingNullValueConsumer = failingNullValueConsumer;
    }

    public void setOnNonReachebleValueAction(Action onNonReachebleValueAction) {
        this.onNonReachebleValueAction = onNonReachebleValueAction;
    }

    public void setFailingNonReachebleValueConsumer(BiConsumer<T, K> failingNonReachebleValueConsumer) {
        this.failingNonReachebleValueConsumer = failingNonReachebleValueConsumer;
    }

    public void setGenericValidator(GenericValidator<U> genericValidator) {
        this.genericValidator = genericValidator;
    }

    public void setValueSupplierFunction(Function<T, U> valueSupplierFunction) {
        this.valueSupplierFunction = valueSupplierFunction;
    }

    public void setFailHandler(TriConsumer<T, U, K> failHandler) {
        this.failHandler = failHandler;
    }

}
