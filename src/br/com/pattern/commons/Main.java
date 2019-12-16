package br.com.pattern.commons;

import br.com.pattern.commons.abstracts.ObjectPool;
import br.com.pattern.commons.impl.Test;
import br.com.pattern.commons.impl.TestBuilder;

public class Main {

    public static void main(String[] args) {

        var pool = new ObjectPool<TestBuilder>(TestBuilder::new);
        Test test = pool.with(testBuilder -> {
            return testBuilder.withMessage("Hello world!").fromUser("vinicius-lds").build();
        });

    }


}
