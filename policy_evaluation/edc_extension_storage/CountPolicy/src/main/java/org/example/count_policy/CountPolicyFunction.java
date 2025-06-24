package org.example.count_policy;

import org.eclipse.edc.connector.controlplane.contract.spi.policy.TransferProcessPolicyContext;
import org.eclipse.edc.policy.engine.spi.AtomicConstraintRuleFunction;
import org.eclipse.edc.policy.model.Operator;
import org.eclipse.edc.policy.model.Permission;
import org.eclipse.edc.spi.monitor.Monitor;

public class CountPolicyFunction implements AtomicConstraintRuleFunction<Permission, TransferProcessPolicyContext> {
    private final Monitor monitor;

    public CountPolicyFunction(Monitor monitor) {
        this.monitor = monitor;
    }

    @Override
    public boolean evaluate(Operator operator, Object rightValue, Permission rule, TransferProcessPolicyContext context) {
        monitor.info("Evaluating count constraint.");
        if(!(rightValue instanceof String) || Integer.parseInt(rightValue) < 1) {
            return false;
        }

        int limit = Integer.parseInt(rightValue);
        int timesTransfered = InMemoryCounter.getInstance().get(new CounterKey(
            context.participantAgent().getIdentity(), context.contractAgreement().getAssetId()
        ));
        return switch (Operator) {
            case Operator.LT -> timesTransfered < limit;
            case Operator.LEQ ->  timesTransfered <= limit;
            default -> false;
        };
    }
}
