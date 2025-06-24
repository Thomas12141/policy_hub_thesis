package org.example.billing_policy;

import org.eclipse.edc.connector.controlplane.contract.spi.policy.TransferProcessPolicyContext;
import org.eclipse.edc.policy.engine.spi.PolicyEngine;
import org.eclipse.edc.policy.engine.spi.RuleBindingRegistry;
import org.eclipse.edc.policy.model.Duty;
import org.eclipse.edc.runtime.metamodel.annotation.Inject;
import org.eclipse.edc.spi.monitor.Monitor;
import org.eclipse.edc.spi.system.ServiceExtension;
import org.eclipse.edc.spi.system.ServiceExtensionContext;

import static org.eclipse.edc.policy.engine.spi.PolicyEngine.ALL_SCOPES;
import static org.eclipse.edc.policy.model.OdrlNamespace.ODRL_SCHEMA;
import static org.eclipse.edc.spi.constants.CoreConstants.EDC_NAMESPACE;

public class BillingPolicyExtension implements ServiceExtension {

    private static final String PAYMENT_CONSTRAINT_KEY = EDC_NAMESPACE + "Payment";

    @Inject
    private PolicyEngine policyEngine;

    @Inject
    private RuleBindingRegistry bindingRegistry;

    @Override
    public void initialize(ServiceExtensionContext context) {
        Monitor monitor = context.getMonitor();

        var function = new BillingPolicyFunction(monitor);

        bindingRegistry.bind(ODRL_SCHEMA + "pay", ALL_SCOPES);
        bindingRegistry.bind(PAYMENT_CONSTRAINT_KEY, TransferProcessPolicyContext.TRANSFER_SCOPE);
        policyEngine.registerFunction(TransferProcessPolicyContext.class, Duty.class, PAYMENT_CONSTRAINT_KEY, function);

    }
}
