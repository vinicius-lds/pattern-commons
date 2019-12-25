package br.com.patterncommons.abstracts;

import br.com.patterncommons.concretes.ValidatorObject;
import org.springframework.beans.factory.BeanNameAware;

public abstract class FieldValidator<T, K extends ValidatorObject> implements BeanNameAware {

    public abstract boolean validate(T object, K validatorObject);

}
