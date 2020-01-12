package br.com.patterncommons.validationapi;

public class FieldValidatorDecorator<T, K extends ValidatorObject> extends FieldValidator<T, K> {

    private FieldValidator<T, K> fieldValidator;

    public FieldValidatorDecorator(FieldValidator<T, K> fieldValidator) {
        this.fieldValidator = fieldValidator;
    }

    @Override
    public boolean validate(T object, K validatorObject) {
        return false;
    }

}
