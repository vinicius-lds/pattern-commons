package br.com.patterncommons.concretes;

import br.com.patterncommons.interfaces.PoolObject;
import org.apache.commons.lang3.StringUtils;

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
        errors.clear();
        warnings.clear();
    }
}
