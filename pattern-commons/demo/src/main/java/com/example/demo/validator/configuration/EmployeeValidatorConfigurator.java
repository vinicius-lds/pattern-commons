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

                .failingWhenNull(validatorObject -> validatorObject.addErrorMessage("Employee não pode ser nulo."))


                /* GenericValidators */

                // Adiciona uma configuração de validador genérico
                .addGenericValidators()

                // Define que os validadores serão acionado quando o employee.getSubscriptionType() retornar CNPJ, ou quando não for possivel retornar nada, nesse caso, se o employee for nulo.
                .when(Employee::getSubscriptionType)
                .in(SubscriptionType.CNPJ)
                .rejectingNonReachable()

                // Define que ao se ter um valor nulo fornecido para o validador, será falhado a validação e se adicionada uma mensagem de erro
                .failingNullValue((employee, validatorObject) -> validatorObject.addErrorMessage("ValidatorValue não pode ser nulo, nesse caso o subscriptionNumber."))
                // Define que quando o valor não for alcancavel, será falhada a validação, e será adicionada uma mensagem de erro
                .failingNonReachebleValue((employee, validatorObject) -> validatorObject.addErrorMessage("ValidatorValue não é alcancavel, nesse caso a chamada employee.getSubscriptionNumber() ta dando NPE."))

                // Se define os validadores que serão chamados nessa configuração, e como devem ser buscados os valores que serão fornecido para eles
                .withValidatorSuppliedBy(null, null)
                .withValidatorSuppliedBy(null, null)
                .withValidatorSuppliedBy(null, null)
                .withValidatorSuppliedBy(null, null)

                .submitGenericValidators()

                /* FieldValidators */

                // Adiciona uma configuração de validador de campo
                .addFieldValidators()

                // Define que que os validadore serão acionados somente quando o employee.getSubscriptionType() retorna CNPJ, ou quando não for possivel retornar nada, nesse caso, se o employee for nulo.
                .when(Employee::getSubscriptionType)
                .in(SubscriptionType.CNPJ)
                .orIsNonReachable()

                // Define os validadores que serão acionados nessa configuração
                .withValidator(this.fieldValidator1)
                .withValidator(this.fieldValidator2)
                .withValidator(this.fieldValidator3)

                /* InnerValidators */
                // É adiciona um novo validador, e logo depois é feita uma configuração para ele, tipo a configuração de cima
                // Dessa forma são feitas configurações de uma forma quase recursiva
                .addValidator(this.validator1)
                .suppliedBy(Employee::getCostCenter)
                .failingWhenNonReacheble((employee, validatorObject) -> validatorObject.addErrorMessage("Não foi possível retornar o costCenter do employee"))
                .withConfiguration(validator ->
                        validator
                                .failingWhenNull(validatorObject -> validatorObject.addErrorMessage("Employee não pode ser nulo."))
                                .addGenericValidators()
                                .when(CostCenter::getCode)
                                .in(CostCenterCode.NUM_01, CostCenterCode.NUM_02, CostCenterCode.NUM_03)
                                .acceptingNullValue()
                                .acceptingNonReachebleValue()
                                .withValidatorSuppliedBy(this.genericValidator5, CostCenter::getDescription)
                                .addFieldValidators()
                                .withValidator(this.fieldValidator4)
                                .addValidator(this.validator2)
                                .suppliedBy(CostCenter::getJobPosition)
                                .acceptingWhenNonReacheble()
                                .withConfiguration(jobPositionValidator ->
                                        jobPositionValidator.acceptingWhenNull()
                                )
                )


    }

}
