package br.com.patterncommons.validationapi;

interface Proxy<T, K extends ValidatorObject> {

    boolean validate(T object, K validatorObject);

}
