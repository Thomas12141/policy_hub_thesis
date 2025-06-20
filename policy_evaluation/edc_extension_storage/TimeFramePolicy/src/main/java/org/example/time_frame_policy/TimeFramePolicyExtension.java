package org.example.time_frame_policy;

import org.eclipse.edc.connector.controlplane.contract.spi.policy.TransferProcessPolicyContext;
import org.eclipse.edc.policy.engine.spi.PolicyEngine;
import org.eclipse.edc.policy.engine.spi.RuleBindingRegistry;
import org.eclipse.edc.policy.model.Rule;
import org.eclipse.edc.runtime.metamodel.annotation.Inject;
import org.eclipse.edc.spi.monitor.Monitor;
import org.eclipse.edc.spi.system.ServiceExtension;
import org.eclipse.edc.spi.system.ServiceExtensionContext;

import static org.eclipse.edc.jsonld.spi.PropertyAndTypeNames.ODRL_USE_ACTION_ATTRIBUTE;
import static org.eclipse.edc.policy.engine.spi.PolicyEngine.ALL_SCOPES;
import static org.eclipse.edc.spi.constants.CoreConstants.EDC_NAMESPACE;

public class TimeFramePolicyExtension implements ServiceExtension {
    private static final String Time_CONSTRAINT_KEY = EDC_NAMESPACE + "DateTime";

    @Inject
    private RuleBindingRegistry bindingRegistry;

    @Inject
    private PolicyEngine policyEngine;

    @Override
    public void initialize(ServiceExtensionContext context) {
        Monitor monitor = context.getMonitor();

        var function = new TimeFramePolicyConstraintFunction(monitor);

        bindingRegistry.bind(ODRL_USE_ACTION_ATTRIBUTE, ALL_SCOPES);
        bindingRegistry.bind(Time_CONSTRAINT_KEY, TransferProcessPolicyContext.TRANSFER_SCOPE);
        policyEngine.registerFunction(TransferProcessPolicyContext.class, Rule.class, Time_CONSTRAINT_KEY, function);

    }
}
