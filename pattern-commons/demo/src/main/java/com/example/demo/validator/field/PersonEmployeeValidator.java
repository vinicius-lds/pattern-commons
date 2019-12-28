package com.example.demo.validator.field;

import br.com.patterncommons.validationapi.FieldValidator;
import br.com.patterncommons.validationapi.ValidatorObject;
import com.example.demo.model.Employee;
import com.example.demo.validator.object.EmployeeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersonEmployeeValidator extends FieldValidator<Employee, ValidatorObject> {

    @Autowired
    private EmployeeValidator employeeValidator;

    @Override
    public boolean validate(Employee employee, ValidatorObject validatorObject) {
        if (employee.getPerson() == null) {
            validatorObject.setInvalid();
            return false;
        }
        return true;
    }

    @Override
    public void initialize() {
        employeeValidator.addFieldValidator(this);
    }
}
