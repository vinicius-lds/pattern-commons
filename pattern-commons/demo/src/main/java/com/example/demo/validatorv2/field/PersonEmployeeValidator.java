package com.example.demo.validatorv2.field;

import br.com.patterncommons.validationapiv2.FieldValidator;
import br.com.patterncommons.validationapiv2.ValidatorObject;
import com.example.demo.model.Employee;
import org.springframework.stereotype.Component;

@Component
public class PersonEmployeeValidator extends FieldValidator<Employee, ValidatorObject> {


}
