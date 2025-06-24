package org.example.time_frame_policy;

import org.eclipse.edc.connector.controlplane.contract.spi.policy.TransferProcessPolicyContext;
import org.eclipse.edc.policy.engine.spi.AtomicConstraintRuleFunction;
import org.eclipse.edc.policy.model.Operator;
import org.eclipse.edc.policy.model.Rule;
import org.eclipse.edc.spi.monitor.Monitor;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class TimeFramePolicyFunction implements AtomicConstraintRuleFunction<Rule, TransferProcessPolicyContext> {

    private final Monitor monitor;

    public TimeFramePolicyFunction(Monitor monitor) {
        this.monitor = monitor;
    }

    @Override
    public boolean evaluate(Operator operator, Object rightValue, Rule rule, TransferProcessPolicyContext context) {
        monitor.info("Evaluating time constraint.");
        ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC).withNano(0);
        Instant instant = Instant.parse(rightValue.toString());
        ZonedDateTime value = instant.atZone(ZoneOffset.UTC);
        return switch (operator) {
            case Operator.EQ -> now.isEqual(value);
            case Operator.GT -> now.isAfter(value);
            case Operator.LT -> now.isBefore(value);
            case Operator.NEQ -> !now.isEqual(value);
            case Operator.LEQ -> now.isEqual(value) || now.isBefore(value);
            case Operator.GEQ -> now.isEqual(value) || now.isAfter(value);
            default -> false;
        };

    }
}
