package org.example.count_policy;

import org.eclipse.edc.connector.controlplane.contract.spi.policy.TransferProcessPolicyContext;
import org.eclipse.edc.connector.controlplane.transfer.spi.observe.TransferProcessListener;
import org.eclipse.edc.connector.controlplane.transfer.spi.types.TransferProcess;
import org.eclipse.edc.connector.controlplane.transfer.spi.types.TransferProcess.Type;
import org.eclipse.edc.policy.engine.spi.PolicyEngine;
import org.eclipse.edc.policy.engine.spi.RuleBindingRegistry;
import org.eclipse.edc.policy.model.Duty;
import org.eclipse.edc.policy.model.Permission;
import org.eclipse.edc.runtime.metamodel.annotation.Inject;
import org.eclipse.edc.spi.monitor.Monitor;
import org.eclipse.edc.spi.system.ServiceExtension;
import org.eclipse.edc.spi.system.ServiceExtensionContext;

import static org.eclipse.edc.jsonld.spi.PropertyAndTypeNames.ODRL_USE_ACTION_ATTRIBUTE;
import static org.eclipse.edc.policy.engine.spi.PolicyEngine.ALL_SCOPES;
import static org.eclipse.edc.spi.constants.CoreConstants.EDC_NAMESPACE;

public class CountPolicyExtension implements ServiceExtension {

    private static final String COUNT_CONSTRAINT_KEY = EDC_NAMESPACE + "NumberOfTransfers";

    @Inject
    private PolicyEngine policyEngine;

    @Inject
    private RuleBindingRegistry bindingRegistry;

    @Override
    public void initialize(ServiceExtensionContext context) {
        Monitor monitor = context.getMonitor();

        var function = new CountPolicyFunction(monitor);

        bindingRegistry.bind(ODRL_USE_ACTION_ATTRIBUTE, ALL_SCOPES);
        bindingRegistry.bind(COUNT_CONSTRAINT_KEY, TransferProcessPolicyContext.TRANSFER_SCOPE);
        policyEngine.registerFunction(TransferProcessPolicyContext.class, Permission.class,
            COUNT_CONSTRAINT_KEY, function);
        registerTransferProcessListener(InMemoryCounter.getInstance(), context);
    }

    private void registerTransferProcessListener(
        TransferCounter transferCounter, ServiceExtensionContext context) {
        transferProcessObservable.registerListener(
            new TransferProcessListener() {
                @Override
                public void preTerminated(TransferProcess process) {
                    var assetId = process.getAssetId();
                    var participantId = context.getParticipantId();
                    if (process.getType().equals(Type.PROVIDER)) {
                        transferCounter.increment(new CounterKey(participantId, assetId));
                    }
                }
            });
    }
}
