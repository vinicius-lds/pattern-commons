package br.com.patterncommons.validationapi;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class GenericProxy<T, K extends ValidatorObject, O> implements Proxy<T, K> {

    private List<ValidatorSupplier<GenericValidator<O>, Function<T, O>, BiConsumer<O, K>>> genericValidator;

    private Function<T, O> transformFunction;
    private Action actionOnTransformNPE;
    private BiConsumer<T, K> onTransformNPEConsumer;

    private Action actionNullTransform;
    private BiConsumer<T, K> nullTransformConsumer;

    private BiConsumer<O, K> onValidationFail;

    GenericProxy() {
        this.actionNullTransform = Action.ACCEPT;
        this.actionOnTransformNPE = Action.ACCEPT;
    }

    @Override
    public boolean validate(T object, K validatorObject) {

        O transform;
        try {
            transform = this.transformFunction.apply(object);
        } catch (NullPointerException e) {
            if (actionOnTransformNPE == Action.FAIL) {
                onTransformNPEConsumer.accept(object, validatorObject);
                return false;
            } else {
                return true;
            }
        }

        if (transform == null) {
            if (this.actionNullTransform == Action.FAIL) {
                if (this.nullTransformConsumer != null) {
                    this.nullTransformConsumer.accept(object, validatorObject);
                }
                return false;
            } else {
                return true;
            }
        }

        if (!this.actualValidator(transform, validatorObject)) {
            if (this.onValidationFail != null) {
                this.onValidationFail.accept(transform, validatorObject);
            }
            return false;
        } else {
            return true;
        }

    }

    private boolean actualValidator(O object, K validatorObject) {
        return this.genericValidator.stream().allMatch(genericValidator -> genericValidator.validate(object));
    }

    public GenericProxy<T, K, O> addValidator(Consumer<ValidatorSupplier<GenericValidator<O>, Function<T, O>, BiConsumer<O, K>>> genericValidator) {
        ValidatorSupplier<GenericValidator<O>, Function<T, O>, BiConsumer<O, K>> aux = new ValidatorSupplier<>();
        genericValidator.accept(aux);
        this.genericValidator.add(aux);
        return this;
    }

    public GenericProxy<T, K, O> onTransformNPE(BiConsumer<T, K> transformNPEFunction) {
        this.onTransformNPEConsumer = transformNPEFunction;
        return this;
    }

    public GenericProxy<T, K, O> onNullTransform(BiConsumer<T, K> nullTransformFunction) {
        this.nullTransformConsumer = nullTransformFunction;
        return this;
    }

}
