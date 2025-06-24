package org.example.billing_policy;

import org.eclipse.edc.connector.controlplane.contract.spi.policy.TransferProcessPolicyContext;
import org.eclipse.edc.policy.engine.spi.AtomicConstraintRuleFunction;
import org.eclipse.edc.policy.model.Duty;
import org.eclipse.edc.policy.model.Operator;
import org.eclipse.edc.spi.monitor.Monitor;

public class BillingPolicyFunction implements AtomicConstraintRuleFunction<Duty, TransferProcessPolicyContext> {

    private final Monitor monitor;

    private final PaymentBook paymentBook = InMemoryPaymentBook.getInstance();

    public BillingPolicyFunction(Monitor monitor) {
        this.monitor = monitor;
    }


    @Override
    public boolean evaluate(Operator operator, Object rightValue, Duty rule, TransferProcessPolicyContext context) {
        monitor.info("Evaluating billing constraint.");
        if (!(rightValue instanceof String) || Double.parseDouble((String) rightValue) < 0) {
            return false;
        }
        double payed = paymentBook.getPayment(new PaymentKey(context.participantAgent().getIdentity(),
                context.contractAgreement().getAssetId()));

        return switch (operator) {
            case EQ -> payed == Double.parseDouble((String) rightValue);
            case GT -> payed > Double.parseDouble((String) rightValue);
            case GEQ -> payed >= Double.parseDouble((String) rightValue);
            default -> false;
        };
    };
}
