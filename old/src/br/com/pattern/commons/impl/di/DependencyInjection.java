package br.com.pattern.commons.impl.di;

import br.com.pattern.commons.impl.validation.AnyValidator;
import br.com.pattern.commons.impl.validation.InnerAnyValidator;
import br.com.pattern.commons.impl.validation.fieldvalidator.InnerNameFieldValidator;
import br.com.pattern.commons.impl.validation.fieldvalidator.NameFieldValidator;

import java.util.HashMap;
import java.util.Map;

public class DependencyInjection {

    private static DependencyInjection di;

    public static DependencyInjection di() {
        if (di == null) {
            di = new DependencyInjection();
            di.initializeContainer();
        }
        return di;
    }

    private Map<Class, Object> container;

    private DependencyInjection() {
        this.container = new HashMap<>();
    }

    private void initializeContainer() {
        // Validador Principal
        this.addDependecy(AnyValidator.class, new AnyValidator());

        // Validador do campo nome do validador principal
        this.addDependecy(NameFieldValidator.class, new NameFieldValidator());

        // Validador interno do validador principal
        this.addDependecy(InnerAnyValidator.class, new InnerAnyValidator());

        // Validador do campo nome do validador interno
        this.addDependecy(InnerNameFieldValidator.class, new InnerNameFieldValidator());
    }

    private <T, K extends T> void addDependecy(Class<T> clazz, K object) {
        this.container.put(clazz, object);
    }

    public <T, K extends T> K get(Class<T> clazz) {
        return (K) this.container.get(clazz);
    }
}
