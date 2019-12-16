package br.com.pattern.commons.abstracts;

public interface Validator<T> {

    boolean validate(T pojo, PojoValidator.PojoAux pojoAux);

}
