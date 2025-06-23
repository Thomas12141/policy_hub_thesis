package org.example.billing_policy;

import org.eclipse.edc.connector.controlplane.contract.spi.policy.TransferProcessPolicyContext;
import org.eclipse.edc.policy.engine.spi.AtomicConstraintRuleFunction;
import org.eclipse.edc.policy.model.Duty;
import org.eclipse.edc.policy.model.Operator;
import org.eclipse.edc.spi.monitor.Monitor;

public class BillingPolicyFunction implements AtomicConstraintRuleFunction<Duty, TransferProcessPolicyContext> {

    private final Monitor monitor;

    public BillingPolicyFunction(Monitor monitor) {
        this.monitor = monitor;
    }


    @Override
    public boolean evaluate(Operator operator, Object rightValue, Duty rule, TransferProcessPolicyContext context) {
        //TODO: implement
        return false;
    }
}
