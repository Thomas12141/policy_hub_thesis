package org.example.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.example.validation.semantic_validation.SemanticValidator;
import org.example.validation.syntax_validation.SyntaxValidator;

import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

public class ParallelPolicyValidator implements Validator {
    private static final Logger logger = LoggerFactory.getLogger(ParallelPolicyValidator.class);

    private static final Set<Validator> validators = Set.of(new SyntaxValidator(), new SemanticValidator());

    @Override
    public List<String> validate(String policy) {
        if (policy == null) {
            return List.of("Policy is null");
        }
        List<CompletableFuture<List<String>>> futures = validators.stream()
                .map(v -> CompletableFuture.supplyAsync(() -> v.validate(policy)).exceptionally(ex -> {
                    logger.error("Error while validating policy", ex);
                    return List.of("Internal server error");
                }))
                .toList();

        return futures.stream()
                .map(CompletableFuture::join)
                .flatMap(List::stream)
                .toList();
    }
}
