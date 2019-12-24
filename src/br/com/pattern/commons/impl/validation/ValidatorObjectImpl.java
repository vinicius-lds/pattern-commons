package br.com.pattern.commons.impl.validation;

import br.com.pattern.commons.concretes.ValidatorObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ValidatorObjectImpl extends ValidatorObject {

    private List<String> irrelevantMessages;

    public ValidatorObjectImpl() {
        super();
        this.irrelevantMessages = new ArrayList<>();
    }

    public void addIrrelevantMessage(String irrelevantMessage, String... irrelevantMessages) {
        this.irrelevantMessages.add(irrelevantMessage);
        this.irrelevantMessages.addAll(Arrays.asList(irrelevantMessages));
    }

}
