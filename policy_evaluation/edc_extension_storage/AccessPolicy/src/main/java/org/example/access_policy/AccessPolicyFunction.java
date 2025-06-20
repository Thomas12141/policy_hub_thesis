package org.example.access_policy;

import java.util.Collection;
import java.util.Objects;
import org.eclipse.edc.connector.controlplane.contract.spi.policy.ContractNegotiationPolicyContext;
import org.eclipse.edc.policy.engine.spi.AtomicConstraintRuleFunction;
import org.eclipse.edc.policy.model.Operator;
import org.eclipse.edc.policy.model.Permission;
import org.eclipse.edc.spi.monitor.Monitor;

public class AccessPolicyFunction implements AtomicConstraintRuleFunction<Permission, ContractNegotiationPolicyContext> {

    private final Monitor monitor;

    public AccessPolicyFunction(Monitor monitor) {
        this.monitor = monitor;
    }

    @Override
    public boolean evaluate(Operator operator, Object rightValue, Permission rule,
                            ContractNegotiationPolicyContext context) {
        monitor.info("Evaluating access constraint.");
        var id = context.participantAgent().getIdentity();

        return switch (operator) {
            case EQ -> Objects.equals(id, rightValue);
            case NEQ -> !Objects.equals(id, rightValue);
            case IN -> ((Collection<?>) rightValue).contains(id);
            default -> false;
        };
    }
}
