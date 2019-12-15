package br.com.pattern.commons;

import br.com.pattern.commons.impl.TestBuilderPool;

public class Main {

    public static void main(String[] args) {

        var pool = new TestBuilderPool();
        pool.with(testBuilder -> {
            testBuilder
                    .withMessage("Hello world!")
                    .fromUser("vinicius-lds")
                    .build();
        });

    }


}
