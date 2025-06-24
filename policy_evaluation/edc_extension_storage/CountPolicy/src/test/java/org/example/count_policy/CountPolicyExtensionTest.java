package org.example.count_policy;

import org.eclipse.edc.connector.controlplane.contract.spi.policy.TransferProcessPolicyContext;
import org.eclipse.edc.connector.controlplane.contract.spi.types.agreement.ContractAgreement;
import org.eclipse.edc.participant.spi.ParticipantAgent;
import org.eclipse.edc.policy.model.Operator;
import org.eclipse.edc.spi.monitor.Monitor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CountPolicyExtensionTest {
    private final InMemoryCounter inMemoryCounter = InMemoryCounter.getInstance();

    private final CountPolicyFunction countPolicyFunction = new CountPolicyFunction(new Monitor() {});

    @Mock
    private ContractAgreement contractAgreement;

    @Mock
    private ParticipantAgent participantAgent;

    @AfterEach
    void clearTransferCounter() {
        inMemoryCounter.clear();
    }

    @Test
    void Validate_LT_Count_Success() {
        //Arrange
        String assetId = "assetId";
        String identity = "identity";

        int count = 10;
        for (int i = 0; i < count-1; i++) {
            inMemoryCounter.increment(new CounterKey(identity, assetId));
        }

        when(contractAgreement.getAssetId()).thenReturn(assetId);
        when(participantAgent.getIdentity()).thenReturn(identity);
        TransferProcessPolicyContext context = new TransferProcessPolicyContext(participantAgent, contractAgreement,
                Instant.now());

        //Act
        boolean result = countPolicyFunction.evaluate(Operator.LT, String.valueOf(count), null, context);

        //Assert
        assertThat(result).isTrue();
    }

    @Test
    void Validate_LT_Count_Failure() {
        //Arrange
        String assetId = "assetId";
        String identity = "identity";

        int count = 10;
        for (int i = 0; i < count; i++) {
            inMemoryCounter.increment(new CounterKey(identity, assetId));
        }

        when(contractAgreement.getAssetId()).thenReturn(assetId);
        when(participantAgent.getIdentity()).thenReturn(identity);
        TransferProcessPolicyContext context = new TransferProcessPolicyContext(participantAgent, contractAgreement,
                Instant.now());

        //Act
        boolean result = countPolicyFunction.evaluate(Operator.LT, String.valueOf(count), null, context);

        //Assert
        assertThat(result).isFalse();
    }

    @Test
    void Validate_LT_Count_NotString_Failure() {
        //Arrange
        String assetId = "assetId";
        String identity = "identity";

        int count = 10;
        for (int i = 0; i < count; i++) {
            inMemoryCounter.increment(new CounterKey(identity, assetId));
        }

        TransferProcessPolicyContext context = new TransferProcessPolicyContext(participantAgent, contractAgreement,
                Instant.now());

        //Act
        boolean result = countPolicyFunction.evaluate(Operator.LT, count, null, context);

        //Assert
        assertThat(result).isFalse();
    }

    @Test
    void Validate_LT_Count_NegativeCount_Failure() {
        //Arrange
        String assetId = "assetId";
        String identity = "identity";

        int count = 10;
        for (int i = 0; i < count; i++) {
            inMemoryCounter.increment(new CounterKey(identity, assetId));
        }

        TransferProcessPolicyContext context = new TransferProcessPolicyContext(participantAgent, contractAgreement,
                Instant.now());

        //Act
        boolean result = countPolicyFunction.evaluate(Operator.LT, String.valueOf(-count), null, context);

        //Assert
        assertThat(result).isFalse();
    }

    @Test
    void Validate_LEQ_Count_Success() {
        //Arrange
        String assetId = "assetId";
        String identity = "identity";

        int count = 10;
        for (int i = 0; i < count - 1; i++) {
            inMemoryCounter.increment(new CounterKey(identity, assetId));
        }

        when(contractAgreement.getAssetId()).thenReturn(assetId);
        when(participantAgent.getIdentity()).thenReturn(identity);
        TransferProcessPolicyContext context = new TransferProcessPolicyContext(participantAgent, contractAgreement,
                Instant.now());

        //Act
        boolean result = countPolicyFunction.evaluate(Operator.LEQ, String.valueOf(count), null, context);

        //Assert
        assertThat(result).isTrue();
    }

    @Test
    void Validate_LEQ_Count_Success_Equal() {
        //Arrange
        String assetId = "assetId";
        String identity = "identity";

        int count = 10;
        for (int i = 0; i < count; i++) {
            inMemoryCounter.increment(new CounterKey(identity, assetId));
        }

        when(contractAgreement.getAssetId()).thenReturn(assetId);
        when(participantAgent.getIdentity()).thenReturn(identity);
        TransferProcessPolicyContext context = new TransferProcessPolicyContext(participantAgent, contractAgreement,
                Instant.now());

        //Act
        boolean result = countPolicyFunction.evaluate(Operator.LEQ, String.valueOf(count), null, context);

        //Assert
        assertThat(result).isTrue();
    }

    @Test
    void Validate_LEQ_Count_Failure() {
        //Arrange
        String assetId = "assetId";
        String identity = "identity";

        int count = 10;
        for (int i = 0; i < count + 1; i++) {
            inMemoryCounter.increment(new CounterKey(identity, assetId));
        }

        when(contractAgreement.getAssetId()).thenReturn(assetId);
        when(participantAgent.getIdentity()).thenReturn(identity);
        TransferProcessPolicyContext context = new TransferProcessPolicyContext(participantAgent, contractAgreement,
                Instant.now());

        //Act
        boolean result = countPolicyFunction.evaluate(Operator.LEQ, String.valueOf(count), null, context);

        //Assert
        assertThat(result).isFalse();
    }

    @Test
    void Validate_NotSupportedOperators_Count_Failure() {
        //Arrange
        String assetId = "assetId";
        String identity = "identity";

        int count = 10;

        when(contractAgreement.getAssetId()).thenReturn(assetId);
        when(participantAgent.getIdentity()).thenReturn(identity);
        TransferProcessPolicyContext context = new TransferProcessPolicyContext(participantAgent, contractAgreement,
                Instant.now());


        List<Operator> notSupportedOperators = Arrays.stream(Operator.values()).
                filter(operator -> operator != Operator.LT && operator != Operator.LEQ).
                toList();

        //Act
        boolean result = notSupportedOperators.stream().
                map(operator -> countPolicyFunction.evaluate(operator, String.valueOf(count), null, context)).
                reduce(false, (a, b) -> a || b);

        //Assert
        assertThat(result).isFalse();
    }
}
