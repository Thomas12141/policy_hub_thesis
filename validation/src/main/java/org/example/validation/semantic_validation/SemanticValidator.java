package org.example.validation.semantic_validation;

import java.io.FileNotFoundException;
import java.io.InputStream;
import org.example.validation.Validator;
import org.example.validation.semantic_validation.model.Policy;
import org.example.validation.semantic_validation.model.PolicyValidation;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import java.io.IOException;
import java.util.*;

public class SemanticValidator implements Validator {
    private final KieContainer kieContainer;

    public SemanticValidator() {
        this.kieContainer = initializeKieContainer();
    }

    @Override
    public List<String> validate(String policyContent) {

        PolicyValidation policyValidation = new PolicyValidation();
        policyValidation.setErrors(new ArrayList<>());

        try {
            Policy policy = Policy.ofJSON(policyContent);
            policyValidation.setPolicy(policy);
            try (KieSession kieSession = kieContainer.newKieSession()) {

                kieSession.insert(policyValidation);


                kieSession.fireAllRules();


                kieSession.dispose();
            }

        } catch (Exception e) {
            policyValidation.getErrors().add("Failed to parse policy: " + e.getMessage());
        }

        return policyValidation.getErrors();
    }



    private KieContainer initializeKieContainer() {
        KieServices ks = KieServices.Factory.get();
        KieFileSystem kfs = ks.newKieFileSystem();

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("rules/policy-rules.drl")) {
            if (inputStream == null) {
                throw new FileNotFoundException("rules/policy-rules.drl not found in classpath");
            }
            String rule = new String(inputStream.readAllBytes());
            kfs.write("src/main/resources/rules/policy-rules.drl", rule);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        KieBuilder kieBuilder = ks.newKieBuilder(kfs);
        kieBuilder.buildAll();

        if (kieBuilder.getResults().hasMessages(Message.Level.ERROR)) {
            throw new RuntimeException("Errors building rules: " +
                    kieBuilder.getResults().getMessages(Message.Level.ERROR));
        }

        return ks.newKieContainer(kieBuilder.getKieModule().getReleaseId());
    }
}