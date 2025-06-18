package org.example.policy_hub.configuration;

import org.example.validation.ConcurrentAllValidatorsValidator;
import org.example.validation.Validator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidatorConfiguration {

    @Bean
    public Validator validator() {
        return new ConcurrentAllValidatorsValidator();
    }
}