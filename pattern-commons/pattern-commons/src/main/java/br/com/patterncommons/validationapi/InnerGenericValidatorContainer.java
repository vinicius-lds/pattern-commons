package br.com.patterncommons.validationapi;

public interface InnerGenericValidatorContainer<T, K extends ValidatorObject, U> {

    void addInnerGenericValidator(InnerGenericValidator<T, K, U, ?> innerGenericValidator);

}
