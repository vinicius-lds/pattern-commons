package br.com.patterncommons.validationapi;

interface ValueSupplierBehavior<T, U> {

    @Deprecated
    void setValueSupplier(ValueSupplier<T, U, ?> valueSupplier);

}
