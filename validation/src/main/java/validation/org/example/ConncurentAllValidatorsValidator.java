package validation.org.example;

import validation.org.example.semantic_validation.SemanticValidation;
import validation.org.example.syntax_validation.SyntaxValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

public class ConncurentAllValidatorsValidator implements Validator {
    private static final Logger logger = LoggerFactory.getLogger(ConncurentAllValidatorsValidator.class);

    private static final Executor executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private static final Set<Validator> validators = Set.of(new SyntaxValidator(), new SemanticValidation());

    @Override
    public List<String> validate(String policy, Type type) {
        CountDownLatch latch = new CountDownLatch(validators.size());
        List<String> errors = Collections.synchronizedList(new ArrayList<>());
        for (Validator validator : validators) {
            executor.execute(() -> {
                errors.addAll(validator.validate(policy, type));
                latch.countDown();
            });
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            logger.error("Interrupted while waiting for validation", e);
            return List.of("Internal server error");
        }
        return errors;
    }
}
