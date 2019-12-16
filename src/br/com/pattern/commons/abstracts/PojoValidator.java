package br.com.pattern.commons.abstracts;

import br.com.pattern.commons.interfaces.PoolObject;

import java.util.ArrayList;
import java.util.List;

public abstract class PojoValidator<T> {

    private ObjectPool<PojoAux> pojoAuxObjectPool;
    private List<Validator<T>> validators;

    public PojoValidator() {
        pojoAuxObjectPool = new PojoAuxPool();
        validators = new ArrayList<>();
    }

    public boolean validate(T pojo) {
        return false;
    }

    private boolean validate(T pojo, PojoAux pojoAux) {
        return validators.stream().reduce(());
    }

    public static class PojoAux implements PoolObject {

        @Override
        public void clear() {

        }
    }

    private static class PojoAuxPool extends ObjectPool<PojoAux> {

        @Override
        protected PojoAux newObject() {
            return new PojoAux();
        }
    }


}
