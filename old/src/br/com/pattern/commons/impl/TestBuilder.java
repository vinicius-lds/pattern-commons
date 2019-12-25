package br.com.pattern.commons.impl;

import br.com.pattern.commons.interfaces.Builder;
import br.com.pattern.commons.interfaces.PoolObject;

public class TestBuilder implements Builder<Test>, PoolObject {

    private String message;
    private String user;

    public TestBuilder withMessage(String message) {
        this.message = message;
        return this;
    }

    public TestBuilder fromUser(String user) {
        this.user = user;
        return this;
    }

    @Override
    public Test build() {
        var test = new Test();
        test.setMessage(this.message);
        test.setUser(this.user);
        test.setMs(System.currentTimeMillis());
        test.setPassword("123456789");
        return test;
    }

    @Override
    public void clear() {
        this.message = null;
        this.user = null;
    }
}
