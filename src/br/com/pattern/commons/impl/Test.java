package br.com.pattern.commons.impl;

import br.com.pattern.commons.interfaces.PoolObject;

public class Test implements PoolObject {

    private String message;
    private String user;
    private String password;
    private long ms;

    @Override
    public void clear() {
        this.message = null;
        this.user = null;
        this.password = null;
        this.ms = 0;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getMs() {
        return ms;
    }

    public void setMs(long ms) {
        this.ms = ms;
    }
}
