package br.com.patterncommons.validationapi;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class ValueSupplier<T, K extends ValidatorObject, U, P extends ValueSupplierBehavior<T, K, U>> {

    private Function<T, U> supplierFunction;
    private P caller;
    private BiConsumer<T, K> failMessageConsumer;
    private Action action;

    public ValueSupplier(Function<T, U> supplierFunction, P caller) {
        this.supplierFunction = supplierFunction;
        this.caller = caller;
        acceptOnNullPointer();
    }

    public P acceptOnNullPointer() {
        this.action = Action.ACCEPT;
        this.caller.setValueSupplier(this);
        return this.caller;
    }

    public P failOnNullPointer(BiConsumer<T, K> failMessageConsumer) {
        this.failMessageConsumer = failMessageConsumer;
        this.action = Action.FAIL;
        this.caller.setValueSupplier(this);
        return this.caller;
    }

    boolean getAndValidate(T object, K validatorObject, Function<U, Boolean> getAndValidateFunction) {
        try {
            var result = supplierFunction.apply(object);
            return getAndValidateFunction.apply(result);
        } catch (NullPointerException e) {
            if (this.action == Action.FAIL) {
                this.failMessageConsumer.accept(object, validatorObject);
                return false;
            } else {
                return true;
            }
        }
    }

}
