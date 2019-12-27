package br.com.patterncommons.validationapi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class EnumConditional<T, P extends EnumConditionalBehavior> {
    private Function<T, ? extends Enum> enumProvider;
    private List<Enum> providedEnums;
    private P caller;
    private boolean notIn;

    EnumConditional(Function<T, ? extends Enum> enumProvider, P caller) {
        this.enumProvider = enumProvider;
        this.caller = caller;
        this.notIn = false;
    }

    public P in(Enum enumeration, Enum... enumerations) {
        if (this.providedEnums == null) {
            this.providedEnums = new ArrayList<>(enumerations.length + 1);
        }
        this.providedEnums.add(enumeration);
        this.providedEnums.addAll(Arrays.asList(enumerations));
        this.caller.addEnumConditional(this);
        return this.caller;
    }

    public P notIn(Enum enumeration, Enum... enumerations) {
        this.notIn = true;
        this.in(enumeration, enumerations);
        return this.caller;
    }

    boolean hasValidEnum(T object) {
        if (this.notIn) {
            return !this.providedEnums.contains(this.enumProvider.apply(object));
        } else {
            return this.providedEnums.contains(this.enumProvider.apply(object));
        }
    }

}