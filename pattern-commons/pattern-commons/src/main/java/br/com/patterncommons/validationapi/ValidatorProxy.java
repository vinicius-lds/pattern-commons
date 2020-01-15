package br.com.patterncommons.validationapi;

public class ValidatorProxy<T, K extends ValidatorObject, O> extends Proxy<T, K, O> {

    private Validator<O, K> validator;

    @Override
    boolean actualValidator(O object, K validatorObject) {
        return validator.validate(object, validatorObject);
    }
}
