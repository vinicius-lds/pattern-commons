package br.com.patterncommons.validationapi;

interface ValueSupplierBehavior<T, K extends ValidatorObject, U> {

    @Deprecated
    void setValueSupplier(ValueSupplier<T, K, U, ?> valueSupplier);

}
