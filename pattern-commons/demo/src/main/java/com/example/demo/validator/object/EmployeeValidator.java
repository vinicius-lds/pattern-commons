package com.example.demo.validator.object;

import br.com.patterncommons.validationapi.Validator;
import br.com.patterncommons.validationapi.ValidatorObject;
import com.example.demo.model.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeValidator extends Validator<Employee, ValidatorObject> {

    @Override
    public void initialize() {
    }

    @Override
    public boolean validate(Employee employee) {
        return validatorObjectPool.getWith(validatorObject -> {
            var result = validate(employee, validatorObject);
            employee.setValidationMessage(validatorObject.getMessage());
            return result;
        });
    }

}
