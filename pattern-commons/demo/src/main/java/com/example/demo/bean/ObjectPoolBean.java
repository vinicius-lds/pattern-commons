package com.example.demo.bean;

import br.com.patterncommons.concretes.ObjectPool;
import br.com.patterncommons.validationapi.ValidatorObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectPoolBean {

    @Bean
    public ObjectPool<ValidatorObject> validatorObjectObjectPool() {
        return new ObjectPool<>(ValidatorObject::new);
    }

}
