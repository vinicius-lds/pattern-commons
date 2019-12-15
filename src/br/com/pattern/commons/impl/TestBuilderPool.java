package br.com.pattern.commons.impl;

import br.com.pattern.commons.abstracts.ObjectPool;

public class TestBuilderPool extends ObjectPool<TestBuilder> {

    @Override
    protected TestBuilder newObject() {
        return new TestBuilder();
    }
}
