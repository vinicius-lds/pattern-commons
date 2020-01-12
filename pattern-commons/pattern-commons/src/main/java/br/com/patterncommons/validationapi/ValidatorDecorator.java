package br.com.patterncommons.validationapi;

public class ValidatorDecorator<T, K extends ValidatorObject, U> extends Validator<T, K> {

    private Validator<T, K> validator;

    public ValidatorDecorator(Validator<T, K> validator) {
        this.validator = validator;
    }

    @Override
    public boolean validate(T object) {
        return super.validate(object);
    }

    @Override
    public boolean validate(T object, K validatorObject) {
        return super.validate(object, validatorObject);
    }
}
