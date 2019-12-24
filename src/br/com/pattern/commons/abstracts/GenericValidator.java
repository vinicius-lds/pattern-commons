package br.com.pattern.commons.abstracts;

import br.com.pattern.commons.interfaces.Initializable;

public abstract class GenericValidator<T> implements Initializable {
    public abstract boolean validate(T object);
}
