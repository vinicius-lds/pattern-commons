package br.com.patterncommons.validationapi;

interface EnumConditionalBehavior<T> {

    @Deprecated
    void addEnumConditional(EnumConditional<T, ? extends EnumConditionalBehavior<T>> enumConditional);

}
