package com.example.demo.validator.object;

import br.com.patterncommons.abstracts.Validator;
import br.com.patterncommons.concretes.ValidatorObject;
import com.example.demo.model.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeValidator extends Validator<Employee, ValidatorObject> {

    @Override
    public boolean validate(Employee employee) {
        return validatorObjectPool.with(validatorObject -> {
            var result = validate(employee, validatorObject);
            employee.setValidationMessage(validatorObject.getMessage());
            return result;
        });
    }

    @Override
    public void setBeanName(String s) {
    }
}
