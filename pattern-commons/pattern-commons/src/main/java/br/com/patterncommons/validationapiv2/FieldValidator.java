package br.com.patterncommons.validationapiv2;

public interface FieldValidator<T, K extends ValidatorObject> {

    boolean validate(T object, K validatorObject);

}
