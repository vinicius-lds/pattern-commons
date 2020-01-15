package br.com.patterncommons.validationapiv2;

import br.com.patterncommons.interfaces.PoolObject;
import br.com.patterncommons.validationapi.Validator;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Validation helper object, it implements the {@link PoolObject} interface, so it can be used in a
 * {@link br.com.patterncommons.concretes.ObjectPool}, to save memory and instantiate less object's
 * <p>
 * This object is used to set error and warning message's, and also can be used just as a flag to verify if a certain
 * object used in a {@link Validator} is valid or not.
 */
public class ValidatorObject implements PoolObject {

    private boolean valid;
    private List<String> errors;
    private List<String> warnings;

    public ValidatorObject() {
        this.valid = true;
        this.errors = new ArrayList<>();
        this.warnings = new ArrayList<>();
    }

    /**
     * Add's a error message to be to the object. When called, the object by default assumes that the current validation
     * already failed, and when the method {@link ValidatorObject#isValid()} is called the next time, it's going to
     * return true.
     *
     * @param error  error message to be added.
     * @param errors other error messages to be added on top of the first one.
     */
    public void addErrorMessage(String error, String... errors) {
        this.errors.add(error);
        this.errors.addAll(Arrays.asList(errors));
    }

    /**
     * Add's a warning message to the validation process.
     *
     * @param warning  warning message to be added.
     * @param warnings other warning messages to be added on top of the first one.
     */
    public void addWarningMessage(String warning, String... warnings) {
        this.warnings.add(warning);
        this.warnings.addAll(Arrays.asList(warnings));
    }

    /**
     * Check if the object being validated is valid or not.
     *
     * @return true if the object being validated is valid, otherwise returns false.
     */
    public boolean isValid() {
        if (!this.valid) {
            return false;
        } else {
            return this.errors.isEmpty();
        }
    }

    /**
     * Check if the object being validated is valid or not.
     *
     * @return false if the object being validated is valid, otherwise returns true.
     */
    public boolean isInvalid() {
        return !this.isValid();
    }

    /**
     * Sets the object being validated as invalid.
     */
    public void setInvalid() {
        this.valid = false;
    }

    /**
     * Compiles all error and warning message's into different lines of a same string and returns.
     *
     * @return all error and warning message's.
     */
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

    /**
     * Compiles all error message's into different lines of a same string and returns.
     *
     * @return all error message's.
     */
    public String getErrorMessage() {
        return String.join("\n", errors);
    }

    /**
     * Compiles all warning message's into different lines of a same string and returns.
     *
     * @return all warning message's.
     */
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
