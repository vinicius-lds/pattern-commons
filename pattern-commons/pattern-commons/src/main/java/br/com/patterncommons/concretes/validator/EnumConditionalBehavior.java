package br.com.patterncommons.concretes.validator;

interface EnumConditionalBehavior<T> {

    @Deprecated
    void addEnumConditional(EnumConditional<T, ? extends EnumConditionalBehavior<T>> enumConditional);

}
