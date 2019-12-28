package br.com.patterncommons.validationapi;

import javax.annotation.PostConstruct;

public abstract class ValidatorConfigurator {

    @PostConstruct
    public abstract void configure();

}
