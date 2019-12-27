package br.com.patterncommons.validationapi;

import java.util.function.Function;

public class ValueSupplier<T, U, P extends ValueSupplierBehavior<T, U>> {

    private Function<T, U> supplierFunction;
    private P caller;
    private SupplierFunctionNullPointerAction supplierFunctionNullPointerAction;

    public ValueSupplier(Function<T, U> supplierFunction, P caller) {
        this.supplierFunction = supplierFunction;
        this.caller = caller;
        acceptOnNullPointer();
    }

    public P acceptOnNullPointer() {
        this.supplierFunctionNullPointerAction = SupplierFunctionNullPointerAction.ACCEPT;
        this.caller.setValueSupplier(this);
        return this.caller;
    }

    public P failOnNullPointer() {
        this.supplierFunctionNullPointerAction = SupplierFunctionNullPointerAction.FAIL;
        this.caller.setValueSupplier(this);
        return this.caller;
    }

    boolean getAndValidateOrIfInvalid(T object, Function<U, Boolean> getAndValidateFunction, Runnable orIfInvalid) {
        try {
            var result = supplierFunction.apply(object);
            return getAndValidateFunction.apply(result);
        } catch (NullPointerException e) {
            if (this.supplierFunctionNullPointerAction == SupplierFunctionNullPointerAction.FAIL) {
                orIfInvalid.run();
                return false;
            } else {
                return true;
            }
        }
    }

}
