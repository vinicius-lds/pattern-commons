package br.com.patterncommons.validationapi;

import java.util.List;

public class FieldProxy<T, K extends ValidatorObject, O> extends Proxy<T, K, O> {

    private List<FieldValidator<O, K>> fieldValidator;


    @Override
    O getTransform() {
        return null;
    }

    @Override
    boolean actualValidator(O object, K validatorObject) {
        return fieldValidator.validate(object, validatorObject);
    }
}
