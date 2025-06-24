package org.example.count_policy;

import org.eclipse.edc.connector.controlplane.contract.spi.policy.TransferProcessPolicyContext;
import org.eclipse.edc.policy.engine.spi.AtomicConstraintRuleFunction;
import org.eclipse.edc.policy.model.Duty;
import org.eclipse.edc.policy.model.Operator;
import org.eclipse.edc.spi.monitor.Monitor;

public class CountPolicyFunction implements AtomicConstraintRuleFunction<Duty, TransferProcessPolicyContext> {
    private final Monitor monitor;

    public CountPolicyFunction(Monitor monitor) {
        this.monitor = monitor;
    }

    @Override
    public boolean evaluate(Operator operator, Object rightValue, Duty rule, TransferProcessPolicyContext context) {
        monitor.info("Evaluating count constraint.");
        return false;
    }
}
