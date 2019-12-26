package br.com.patterncommons.concretes.validator;

import br.com.patterncommons.interfaces.PoolObject;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ValidatorObject implements PoolObject {

    private boolean valid;
    private List<String> errors;
    private List<String> warnings;

    public ValidatorObject() {
        this.valid = true;
        this.errors = new ArrayList<>();
        this.warnings = new ArrayList<>();
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
        if (!this.valid) {
            return false;
        } else {
            return this.errors.isEmpty();
        }
    }

    public void setInvalid() {
        this.valid = false;
    }

    public String getMessage() {
        var errorMessage = getErrorMessage();
        var warningMessage = getWarningMessage();
        var errorMessageIsNotBlank = StringUtils.isNotBlank(errorMessage);
        var warningMessageIsNotBlank = StringUtils.isNotBlank(warningMessage);
        if (errorMessageIsNotBlank && warningMessageIsNotBlank) {
            return String.join("\n", errorMessage, warningMessage);
        } else if (errorMessageIsNotBlank) {
            return errorMessage;
        } else if (warningMessageIsNotBlank) {
            return warningMessage;
        } else {
            return "";
        }
    }

    public String getErrorMessage() {
        return String.join("\n", errors);
    }

    public String getWarningMessage() {
        return String.join("\n", warnings);
    }

    @Override
    public void clear() {
        this.valid = true;
        this.errors.clear();
        this.warnings.clear();
    }
}
