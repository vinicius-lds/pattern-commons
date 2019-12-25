package br.com.pattern.commons.abstracts;

import br.com.pattern.commons.concretes.ValidatorObject;
import br.com.pattern.commons.interfaces.Initializable;

public abstract class FieldValidator<T, K extends ValidatorObject> implements Initializable {

    public FieldValidator() {
        this.initialize();
    }

    public abstract boolean validate(T object, K validatorObject);

}
