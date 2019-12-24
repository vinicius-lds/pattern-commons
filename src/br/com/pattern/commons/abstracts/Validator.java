package br.com.pattern.commons.interfaces;

import br.com.pattern.commons.concretes.ObjectPool;
import br.com.pattern.commons.concretes.ValidatorObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

public abstract class Validator<T, K extends ValidatorObject> implements Initializable {

    private List<InnerValidator> innerValidators;
    private List<FieldValidator<T, K>> fieldValidators;

    public Validator() {
        this.innerValidators = new ArrayList<>();
        this.fieldValidators = new ArrayList<>();
        this.initialize();
    }

    public abstract ObjectPool<K> getValidatorObjectPool();

    public boolean validate(T object) {
        return getValidatorObjectPool().with(validatorObject -> {
            return validate(object, validatorObject);
        });
    }

    public boolean validate(T object, K validatorObject) {
        var result = new AtomicBoolean(true);
        fieldValidators.forEach(fieldValidator -> {
            result.set(result.get() & fieldValidator.validate(object, validatorObject));
        });
        innerValidators.forEach(innerValidator -> {
            result.set(result.get() & innerValidator.validateObjectFrom(object, validatorObject));
        });
        return result.get();
    }

    public <P> InnerValidator<P> addInnerEntityProvider(Function<T, P> innerEntitySupplier) {
        var aux = new InnerValidator<P>(innerEntitySupplier);
        innerValidators.add(aux);
        return aux;
    }

    public void addFieldValidator(FieldValidator<T, K> fieldValidator) {
        fieldValidators.add(fieldValidator);
    }

    public class InnerValidator<U> {

        private Function<T, U> object;
        private Validator<U, K> validator;

        public InnerValidator(Function<T, U> object) {
            this.object = object;
        }

        public void using(Validator<U, K> validator) {
            this.validator = validator;
        }

        public boolean validateObjectFrom(T anyEntity, K validatorObject) {
            return validator.validate(object.apply(anyEntity), validatorObject);
        }

    }

}
