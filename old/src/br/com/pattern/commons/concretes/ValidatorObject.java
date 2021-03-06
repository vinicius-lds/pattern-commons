package br.com.pattern.commons.concretes;

import br.com.pattern.commons.interfaces.PoolObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ValidatorObject implements PoolObject {

    private List<String> errors;
    private List<String> warnings;

    public ValidatorObject() {
        errors = new ArrayList<>();
        warnings = new ArrayList<>();
    }

    public void addErrorMessage(String error, String... errors) {
        this.errors.add(error);
        this.errors.addAll(Arrays.asList(errors));
    }

    public void addWarningMessage(String warning, String... warnings) {
        this.warnings.add(warning);
        this.warnings.addAll(Arrays.asList(warnings));
    }

    public boolean isValid() {
        return errors.isEmpty();
    }

    public String getMessage() {
        return String.join("\n", getErrorMessage(), getWarningMessage());
    }

    public String getErrorMessage() {
        return String.join("\n", errors);
    }

    public String getWarningMessage() {
        return String.join("\n", warnings);
    }

    @Override
    public void clear() {
        errors.clear();
        warnings.clear();
    }
}
