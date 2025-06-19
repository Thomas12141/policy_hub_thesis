package org.example.is_dataspace_member_extension;

import org.eclipse.edc.connector.controlplane.contract.spi.policy.ContractNegotiationPolicyContext;
import org.eclipse.edc.iam.verifiablecredentials.spi.model.VerifiableCredential;
import org.eclipse.edc.participant.spi.ParticipantAgent;
import org.eclipse.edc.policy.engine.spi.AtomicConstraintRuleFunction;
import org.eclipse.edc.policy.model.Operator;
import org.eclipse.edc.policy.model.Permission;
import org.eclipse.edc.spi.monitor.Monitor;

import java.util.List;

public class IsDataspaceMemberFunction implements AtomicConstraintRuleFunction<Permission, ContractNegotiationPolicyContext> {
    private static final String ACTIVE = "active";
    private final Monitor monitor;

    public IsDataspaceMemberFunction(Monitor monitor) {
        this.monitor = monitor;
    }

    @Override
    public boolean evaluate(Operator operator, Object rightValue, Permission rule, ContractNegotiationPolicyContext context) {

        monitor.info("Evaluating membership constraint.");

        ParticipantAgent participantAgent = context.participantAgent();

        Object returnValue = participantAgent.getClaims().get("vc");

        if (!(returnValue instanceof List<?>) || ((List<?>) returnValue).isEmpty() ||
                !(((List<?>) returnValue).getFirst() instanceof VerifiableCredential) ) {
            return false;
        }

        List<VerifiableCredential> verifiableCredentialsList = (List<VerifiableCredential>) returnValue;

        return ACTIVE.equalsIgnoreCase(rightValue.toString()) && operator == Operator.EQ &&
                verifiableCredentialsList.stream().anyMatch(verifiableCredential ->
                        verifiableCredential.getType().contains("MembershipCredential") ||
                                verifiableCredential.getType().contains("https://example.org/dataspace/credentials/MembershipCredential")
                );
    }

}
