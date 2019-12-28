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
    private Action nonReachableAction;


    EnumConditional(Function<T, ? extends Enum> enumProvider, P caller) {
        this.enumProvider = enumProvider;
        this.caller = caller;
        this.notIn = false;
    }

    public EnumConditional<T, P> in(Enum enumeration, Enum... enumerations) {
        if (this.providedEnums == null) {
            this.providedEnums = new ArrayList<>(enumerations.length + 1);
        }
        this.providedEnums.add(enumeration);
        this.providedEnums.addAll(Arrays.asList(enumerations));
        this.caller.addEnumConditional(this);
        return this;
    }

    public EnumConditional<T, P> notIn(Enum enumeration, Enum... enumerations) {
        this.notIn = true;
        return this.in(enumeration, enumerations);
    }

    public P orIsNonReachable() {
        this.nonReachableAction = Action.ACCEPT;
        return this.caller;
    }

    public P rejectingNonReachable() {
        this.nonReachableAction = Action.FAIL;
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