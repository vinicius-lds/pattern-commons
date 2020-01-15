package com.example.demo.validator.configuration;

import br.com.patterncommons.validationapi.ValidatorConfigurator;
import com.example.demo.model.Employee;
import com.example.demo.validator.field.PersonEmployeeValidator;
import com.example.demo.validator.field.PersonLastNameValidator;
import com.example.demo.validator.generic.NotBlankValidator;
import com.example.demo.validator.generic.NotNullValidator;
import com.example.demo.validator.object.AddressValidator;
import com.example.demo.validator.object.EmployeeValidator;
import com.example.demo.validator.object.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmployeeValidatorConfigurator extends ValidatorConfigurator {

    /* Validators */
    @Autowired
    private EmployeeValidator employeeValidator;
    @Autowired
    private PersonValidator personValidator;
    @Autowired
    private AddressValidator addressValidator;

    /* FieldValidators */
    @Autowired
    private PersonEmployeeValidator personEmployeeValidator;
    @Autowired
    private PersonLastNameValidator personLastNameValidator;

    /* GenericValidators */
    @Autowired
    private NotNullValidator notNullValidator;
    @Autowired
    private NotBlankValidator notBlankValidator;


    @Override
    public void configure() {
        this.employeeValidator
                .<String>addGenericValidators(configuration -> configuration
                        .onNullTransform((employee, validatorObject) -> System.out.println("Objeto resultante da tranformação é nulo"))
                        .onTransformNPE((employee, validatorObject) -> System.out.println("NullPointer ao tranformar objeto"))
                        .addValidator(validatorConfiguration -> validatorConfiguration
                                .validator(this.notBlankValidator)
                                .usingValue(Employee::getEmployeeId)
                                .usingValidationErrorHandler((fieldValue, validatorObject) -> System.out.println("Erro de validação"))
                        )

                );

    }

}
