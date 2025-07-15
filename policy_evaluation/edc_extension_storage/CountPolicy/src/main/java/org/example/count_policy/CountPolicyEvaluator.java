package org.example.count_policy;

import de.example.policy_dataplane.PolicyEvaluator;
import org.eclipse.edc.policy.model.Permission;
import org.eclipse.edc.connector.controlplane.services.spi.contractagreement.ContractAgreementService;
import org.eclipse.edc.connector.dataplane.spi.pipeline.DataSink;
import org.eclipse.edc.spi.types.domain.transfer.DataFlowStartMessage;
import org.eclipse.edc.spi.system.ServiceExtensionContext;

public class CountPolicyEvaluator implements PolicyEvaluator {
    private final ServiceExtensionContext context;

    private final ContractAgreementService contractAgreementService;

    public CountPolicyEvaluator(ServiceExtensionContext context, ContractAgreementService contractAgreementService) {
        this.context = context;
        this.contractAgreementService = contractAgreementService;
    }

    @Override
    public boolean evaluate(DataFlowStartMessage dataFlowStartMessage) {
        var assetId = dataFlowStartMessage.getAssetId();
        var participantId = context.getParticipantId();
        var counter = InMemoryCounter.getInstance();
        var timesTransferred = counter.get(new CounterKey(participantId, assetId));
        var agreement = contractAgreementService.findById(dataFlowStartMessage.getAgreementId());
        var policy = agreement.getPolicy();
        var visitor = new CountVisitor(timesTransferred);
        boolean result;
        for (Permission permission : policy.getPermissions()) {
            result = permission.accept(visitor);
            if (!result) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean evaluate(DataFlowStartMessage dataFlowStartMessage, DataSink sink) {
        return false;
    }

    @Override
    public void updateValidationStatus(DataFlowStartMessage dataFlowStartMessage) {
        var assetId = dataFlowStartMessage.getAssetId();
        var participantId = context.getParticipantId();
        var counter = InMemoryCounter.getInstance();
        counter.increment(new CounterKey(participantId, assetId));
    }
}
