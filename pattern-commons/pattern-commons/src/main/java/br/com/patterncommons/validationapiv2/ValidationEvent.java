package br.com.patterncommons.validationapiv2;

public interface ValidationEvent<K extends ValidatorObject> {

    void handle(K validatorObject);

}
