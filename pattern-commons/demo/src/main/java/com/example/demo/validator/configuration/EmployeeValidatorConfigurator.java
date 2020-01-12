package com.example.demo.validator.configuration;

import br.com.patterncommons.validationapi.ValidatorConfigurator;
import com.example.demo.model.Employee;
import com.example.demo.model.SubscriptionType;
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
                .addGenericValidators((genericValidatorDecoratorCollectionBuilder) ->
                        genericValidatorDecoratorCollectionBuilder
                                .when(Employee::getSubscriptionType)
                                .in(SubscriptionType.CNPJ)
                                .orIsNonReachable()

                                .failingNullValue((validatorValue, validatorObject) -> validatorObject.addErrorMessage("ValidatorValue não pode ser nulo, nesse caso o subscriptionNumber."))
                                .failingNonReachebleValue((employee, validatorObject) -> validatorObject.addErrorMessage("ValidatorValue não é alcancavel, nesse caso a chamada employee.getSubscriptionNumber() ta dando NPE."))


                                .withGenericValidator(this.notBlankValidator, (genericValidatorDecoratorBuilder) ->
                                        genericValidatorDecoratorBuilder
                                                .suppliedBy(Employee::getEmployeeId)
                                                .withFailHandler((employee, fieldValue, validatorObject) -> validatorObject.addErrorMessage("MessageType.ERROR_MESSAGE"))
                                )

                                .withGenericValidator(this.notBlankValidator, (genericValidatorDecoratorBuilder) ->
                                        genericValidatorDecoratorBuilder
                                                .suppliedBy(Employee::getEmployeeId)
                                                .withFailHandler((employee, fieldValue, validatorObject) -> validatorObject.addErrorMessage("MessageType.ERROR_MESSAGE"))
                                )

                                .withGenericValidator(this.notBlankValidator, (genericValidatorDecoratorBuilder) ->
                                        genericValidatorDecoratorBuilder
                                                .suppliedBy(Employee::getEmployeeId)
                                                .withFailHandler((employee, fieldValue, validatorObject) -> validatorObject.addErrorMessage("MessageType.ERROR_MESSAGE"))
                                )

                )

                .addGenericValidator((genericValidatorDecoratorBuilder) ->
                        genericValidatorDecoratorBuilder
                                .when(Employee::getSubscriptionType)
                                .in(SubscriptionType.CNPJ)
                                .orIsNonReachable()

                                .failingNullValue((validatorValue, validatorObject) -> validatorObject.addErrorMessage("ValidatorValue não pode ser nulo, nesse caso o subscriptionNumber."))
                                .failingNonReachebleValue((employee, validatorObject) -> validatorObject.addErrorMessage("ValidatorValue não é alcancavel, nesse caso a chamada employee.getSubscriptionNumber() ta dando NPE."))

                                .withValidator(this.genericValidator1)
                                .suppliedBy(Employee::getSubscriptionNumber)
                                .withFailHandler((employee, fieldValue, validatorObject) -> validatorObject.addErrorMessage("MessageType.ERROR_MESSAGE"))
                )


                .addFieldValidators((fieldValidatorsDecoratorCollectionBuilder) ->
                        fieldValidatorsDecoratorCollectionBuilder
                                .when(Employee::getSubscriptionType)
                                .in(SubscriptionType.CNPJ)
                                .orIsNonReachable()
                                .withValidator(this.fieldValidator1)
                                .withValidator(this.fieldValidator2)
                                .withValidator(this.fieldValidator3)
                )

                .addFieldValidators((fieldValidatorsDecorator) ->
                        fieldValidatorsDecorator
                                .when(Employee::getSubscriptionType)
                                .in(SubscriptionType.CNPJ)
                                .orIsNonReachable()
                                .withValidator(this.fieldValidator1)
                )

                .addValidators((validatorDecoratorCollectionBuilder) ->
                        validatorDecoratorCollectionBuilder
                                .when(Employee::getSubscriptionType)
                                .in(SubscriptionType.CNPJ)
                                .orIsNonReachable()

                                .withValidator((validatorDecoratorBuilder) ->
                                        validatorDecoratorBuilder
                                                .withValidator(this.genericValidator1)
                                                .suppliedBy(Employee::getSubscriptionNumber)
                                )

                                .withValidator((validatorDecoratorBuilder) ->
                                        validatorDecoratorBuilder
                                                .withValidator(this.genericValidator1)
                                                .suppliedBy(Employee::getSubscriptionNumber)
                                )

                                .withValidator((validatorDecoratorBuilder) ->
                                        validatorDecoratorBuilder
                                                .withValidator(this.genericValidator1)
                                                .suppliedBy(Employee::getSubscriptionNumber)
                                )
                )

                .addValidator((validatorDecoratorBuilder) ->
                        validatorDecoratorBuilder
                                .when(Employee::getSubscriptionType)
                                .in(SubscriptionType.CNPJ)
                                .orIsNonReachable()
                                .withValidator(this.genericValidator1)
                                .suppliedBy(Employee::getSubscriptionNumber)
                )

    }

}
