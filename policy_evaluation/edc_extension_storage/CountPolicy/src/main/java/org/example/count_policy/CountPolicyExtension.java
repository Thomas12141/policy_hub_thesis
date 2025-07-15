package org.example.count_policy;

import de.example.policy_dataplane.PolicyEvaluator;
import de.example.policy_dataplane.ProxyPipelineService;
import org.eclipse.edc.connector.controlplane.contract.spi.policy.TransferProcessPolicyContext;
import org.eclipse.edc.connector.controlplane.services.spi.contractagreement.ContractAgreementService;
import org.eclipse.edc.connector.controlplane.transfer.spi.observe.TransferProcessObservable;
import org.eclipse.edc.connector.dataplane.spi.pipeline.PipelineService;
import org.eclipse.edc.participant.spi.ParticipantAgentService;
import org.eclipse.edc.policy.engine.spi.PolicyEngine;
import org.eclipse.edc.policy.engine.spi.RuleBindingRegistry;
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
    private PipelineService pipelineService;

    @Inject
    private PolicyEngine policyEngine;

    @Inject
    private RuleBindingRegistry bindingRegistry;

    @Inject
    private TransferProcessObservable transferProcessObservable;

    @Inject
    ContractAgreementService contractAgreementService;

    @Inject
    private ParticipantAgentService participantAgentService;

    @Override
    public void initialize(ServiceExtensionContext context) {
        Monitor monitor = context.getMonitor();

        var function = new CountPolicyFunction(monitor);

        bindingRegistry.bind(ODRL_USE_ACTION_ATTRIBUTE, ALL_SCOPES);
        bindingRegistry.bind(COUNT_CONSTRAINT_KEY, TransferProcessPolicyContext.TRANSFER_SCOPE);
        policyEngine.registerFunction(TransferProcessPolicyContext.class, Permission.class,
            COUNT_CONSTRAINT_KEY, function);

        PolicyEvaluator policyEvaluator = new CountPolicyEvaluator(context, contractAgreementService);
        if (pipelineService instanceof ProxyPipelineService) {
            ((ProxyPipelineService) pipelineService).addPolicyValidator(policyEvaluator);
        } else {
            monitor.severe("Pipeline service is not an instance of ProxyPipelineService. Policy validation will not work.");
        }
    }
}
