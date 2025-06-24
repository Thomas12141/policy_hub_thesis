package org.example.access_policy;

import org.eclipse.edc.connector.controlplane.contract.spi.policy.ContractNegotiationPolicyContext;
import org.eclipse.edc.participant.spi.ParticipantAgent;
import org.eclipse.edc.policy.model.Operator;
import org.eclipse.edc.spi.monitor.Monitor;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class AccessPolicyFunctionTest {

    private final AccessPolicyFunction accessPolicyFunction = new AccessPolicyFunction(new Monitor() {});

    @Test
    void Validate_EQ_Identity_Success() {
        //Arrange
        String identity = "123456789";
        ParticipantAgent participantAgent =
                new ParticipantAgent(Collections.emptyMap(), Map.of("edc:identity", identity));

        ContractNegotiationPolicyContext context = new ContractNegotiationPolicyContext(participantAgent);

        //Act
        boolean result = accessPolicyFunction.evaluate(Operator.EQ, identity, null, context);

        //Assert
        assertThat(result).isTrue();
    }

    @Test
    void Validate_EQ_Identity_Failure() {
        //Arrange
        String identity = "123456789";
        String otherIdentity = "987654321";
        ParticipantAgent participantAgent =
                new ParticipantAgent(Collections.emptyMap(), Map.of("edc:identity", otherIdentity));

        ContractNegotiationPolicyContext context = new ContractNegotiationPolicyContext(participantAgent);

        //Act
        boolean result = accessPolicyFunction.evaluate(Operator.EQ, identity, null, context);

        //Assert
        assertThat(result).isFalse();
    }

    @Test
    void Validate_NEQ_Identity_Success() {
        //Arrange
        String identity = "123456789";
        ParticipantAgent participantAgent =
                new ParticipantAgent(Collections.emptyMap(), Map.of("edc:identity", identity));

        ContractNegotiationPolicyContext context = new ContractNegotiationPolicyContext(participantAgent);

        //Act
        boolean result = accessPolicyFunction.evaluate(Operator.NEQ, identity, null, context);

        //Assert
        assertThat(result).isFalse();
    }

    @Test
    void Validate_NEQ_Identity_Failure() {
        //Arrange
        String identity = "123456789";
        String otherIdentity = "987654321";
        ParticipantAgent participantAgent =
                new ParticipantAgent(Collections.emptyMap(), Map.of("edc:identity", otherIdentity));

        ContractNegotiationPolicyContext context = new ContractNegotiationPolicyContext(participantAgent);

        //Act
        boolean result = accessPolicyFunction.evaluate(Operator.NEQ, identity, null, context);

        //Assert
        assertThat(result).isTrue();
    }

    @Test
    void Validate_IN_Identity_Success() {
        //Arrange
        String identity = "123456789";
        String otherIdentity = "987654321";
        ParticipantAgent participantAgent =
                new ParticipantAgent(Collections.emptyMap(), Map.of("edc:identity", identity));

        ContractNegotiationPolicyContext context = new ContractNegotiationPolicyContext(participantAgent);

        //Act
        boolean result = accessPolicyFunction.evaluate(Operator.IN, List.of(identity, otherIdentity), null, context);

        //Assert
        assertThat(result).isTrue();
    }

    @Test
    void Validate_IN_Identity_Failure() {
        //Arrange
        String identity = "123456789";
        String otherIdentity = "987654321";
        ParticipantAgent participantAgent =
                new ParticipantAgent(Collections.emptyMap(), Map.of("edc:identity", identity));

        ContractNegotiationPolicyContext context = new ContractNegotiationPolicyContext(participantAgent);

        //Act
        boolean result = accessPolicyFunction.evaluate(Operator.IN, List.of("identity", otherIdentity), null, context);

        //Assert
        assertThat(result).isFalse();
    }

    @Test
    void Validate_NotSupportedOperator_Identity_Failure() {
        //Arrange
        String identity = "123456789";
        ParticipantAgent participantAgent =
                new ParticipantAgent(Collections.emptyMap(), Map.of("edc:identity", identity));
        ContractNegotiationPolicyContext context = new ContractNegotiationPolicyContext(participantAgent);
        List<Operator> notSupportedOperators = List.of(Operator.GT, Operator.GEQ, Operator.LT, Operator.LEQ,
                Operator.HAS_PART, Operator.IS_A, Operator.IS_ALL_OF, Operator.IS_ANY_OF, Operator.IS_NONE_OF);

        //Act
        boolean result = notSupportedOperators.stream().
                map(operator -> accessPolicyFunction.evaluate(operator, identity, null, context)).
                reduce(false, (a, b) -> a || b);

        //Assert
        assertThat(result).isFalse();
    }
}
