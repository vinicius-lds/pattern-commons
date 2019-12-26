package br.com.patterncommons.concretes.validator;

import br.com.patterncommons.abstracts.FieldValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class InnerFieldValidator<T, K extends ValidatorObject> implements EnumConditionalBehavior<T> {

    private FieldValidator<T, K> fieldValidator;
    private List<EnumConditional<T, ? extends EnumConditionalBehavior<T>>> enumConditionals;

    public InnerFieldValidator(FieldValidator<T, K> fieldValidator) {
        this.fieldValidator = fieldValidator;
    }

    public boolean validateObject(T object, K validatorObject) {
        if (this.enumConditionals == null || this.enumConditionals.stream().allMatch(enumConditional -> enumConditional.hasValidEnum(object))) {
            return this.fieldValidator.validate(object, validatorObject);
        } else {
            return true;
        }
    }

    public EnumConditional<T, InnerFieldValidator<T, K>> when(Function<T, ? extends Enum> enumProvider) {
        return new EnumConditional<>(enumProvider, this);
    }

    @Override
    public void addEnumConditional(EnumConditional<T, ? extends EnumConditionalBehavior<T>> enumConditional) {
        if (this.enumConditionals == null) {
            this.enumConditionals = new ArrayList<>(1);
        }
        this.enumConditionals.add(enumConditional);
    }
}
