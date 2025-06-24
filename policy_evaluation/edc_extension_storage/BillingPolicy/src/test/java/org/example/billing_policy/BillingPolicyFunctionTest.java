package org.example.billing_policy;

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
class BillingPolicyFunctionTest {
    private final BillingPolicyFunction billingPolicyFunction = new BillingPolicyFunction(new Monitor() {});
    private final InMemoryPaymentBook inMemoryPaymentBook = InMemoryPaymentBook.getInstance();

    @Mock
    private ContractAgreement contractAgreement;

    @Mock
    private ParticipantAgent participantAgent;

    @AfterEach
    void clearPaymentBook() {
        InMemoryPaymentBook.getInstance().clear();
    }

    @Test
    void Validate_EQ_Billing_Success() {
        //Arrange
        String assetId = "assetId";
        String identity = "identity";
        double amount = 10.0;
        inMemoryPaymentBook.addPayment(new PaymentKey(identity, assetId), amount);
        when(participantAgent.getIdentity()).thenReturn(identity);
        when(contractAgreement.getAssetId()).thenReturn(assetId);
        TransferProcessPolicyContext context = new TransferProcessPolicyContext(participantAgent, contractAgreement,
                Instant.now());

        //Act
        boolean result = billingPolicyFunction.evaluate(Operator.EQ, String.valueOf(amount), null, context);

        //Assert
        assertThat(result).isTrue();
    }

    @Test
    void Validate_EQ_Billing_Failure() {
        //Arrange
        String assetId = "assetId";
        String identity = "identity";
        double amount = 10.0;
        inMemoryPaymentBook.addPayment(new PaymentKey(identity, assetId), amount + 1);
        when(participantAgent.getIdentity()).thenReturn(identity);
        when(contractAgreement.getAssetId()).thenReturn(assetId);
        TransferProcessPolicyContext context = new TransferProcessPolicyContext(participantAgent, contractAgreement,
                Instant.now());

        //Act
        boolean result = billingPolicyFunction.evaluate(Operator.EQ, String.valueOf(amount), null, context);

        //Assert
        assertThat(result).isFalse();
    }

    @Test
    void Validate_EQ_Billing_NotString_Failure() {
        //Arrange
        String assetId = "assetId";
        String identity = "identity";
        double amount = 10.0;
        inMemoryPaymentBook.addPayment(new PaymentKey(identity, assetId), amount + 1);
        TransferProcessPolicyContext context = new TransferProcessPolicyContext(participantAgent, contractAgreement,
                Instant.now());

        //Act
        boolean result = billingPolicyFunction.evaluate(Operator.EQ, amount, null, context);

        //Assert
        assertThat(result).isFalse();
    }

    @Test
    void Validate_EQ_Billing_NegativeAmount_Failure() {
        //Arrange
        String assetId = "assetId";
        String identity = "identity";
        double amount = 10.0;
        inMemoryPaymentBook.addPayment(new PaymentKey(identity, assetId), amount + 1);
        TransferProcessPolicyContext context = new TransferProcessPolicyContext(participantAgent, contractAgreement,
                Instant.now());

        //Act
        boolean result = billingPolicyFunction.evaluate(Operator.EQ, String.valueOf(-amount), null, context);

        //Assert
        assertThat(result).isFalse();
    }

    @Test
    void Validate_GT_Billing_Success() {
        //Arrange
        String assetId = "assetId";
        String identity = "identity";
        double amount = 10.0;
        inMemoryPaymentBook.addPayment(new PaymentKey(identity, assetId), amount + 0.1);
        when(participantAgent.getIdentity()).thenReturn(identity);
        when(contractAgreement.getAssetId()).thenReturn(assetId);
        TransferProcessPolicyContext context = new TransferProcessPolicyContext(participantAgent, contractAgreement,
                Instant.now());

        //Act
        boolean result = billingPolicyFunction.evaluate(Operator.GT, String.valueOf(amount), null, context);

        //Assert
        assertThat(result).isTrue();
    }

    @Test
    void Validate_GT_Billing_Failure() {
        //Arrange
        String assetId = "assetId";
        String identity = "identity";
        double amount = 10.0;
        inMemoryPaymentBook.addPayment(new PaymentKey(identity, assetId), amount);
        when(participantAgent.getIdentity()).thenReturn(identity);
        when(contractAgreement.getAssetId()).thenReturn(assetId);
        TransferProcessPolicyContext context = new TransferProcessPolicyContext(participantAgent, contractAgreement,
                Instant.now());

        //Act
        boolean result = billingPolicyFunction.evaluate(Operator.GT, String.valueOf(amount), null, context);

        //Assert
        assertThat(result).isFalse();
    }

    @Test
    void Validate_GEQ_Billing_AmountGreater_Success() {
        //Arrange
        String assetId = "assetId";
        String identity = "identity";
        double amount = 10.0;
        inMemoryPaymentBook.addPayment(new PaymentKey(identity, assetId), amount + 0.1);
        when(participantAgent.getIdentity()).thenReturn(identity);
        when(contractAgreement.getAssetId()).thenReturn(assetId);
        TransferProcessPolicyContext context = new TransferProcessPolicyContext(participantAgent, contractAgreement,
                Instant.now());

        //Act
        boolean result = billingPolicyFunction.evaluate(Operator.GEQ, String.valueOf(amount), null, context);

        //Assert
        assertThat(result).isTrue();
    }

    @Test
    void Validate_GEQ_Billing_AmountGreater_Failure() {
        //Arrange
        String assetId = "assetId";
        String identity = "identity";
        double amount = 10.0;
        inMemoryPaymentBook.addPayment(new PaymentKey(identity, assetId), amount);
        when(participantAgent.getIdentity()).thenReturn(identity);
        when(contractAgreement.getAssetId()).thenReturn(assetId);
        TransferProcessPolicyContext context = new TransferProcessPolicyContext(participantAgent, contractAgreement,
                Instant.now());

        //Act
        boolean result = billingPolicyFunction.evaluate(Operator.GEQ, String.valueOf(amount+ 0.1), null, context);

        //Assert
        assertThat(result).isFalse();
    }

    @Test
    void Validate_GEQ_Billing_AmountEqual_Success() {
        //Arrange
        String assetId = "assetId";
        String identity = "identity";
        double amount = 10.0;
        inMemoryPaymentBook.addPayment(new PaymentKey(identity, assetId), amount);
        when(participantAgent.getIdentity()).thenReturn(identity);
        when(contractAgreement.getAssetId()).thenReturn(assetId);
        TransferProcessPolicyContext context = new TransferProcessPolicyContext(participantAgent, contractAgreement,
                Instant.now());

        //Act
        boolean result = billingPolicyFunction.evaluate(Operator.GEQ, String.valueOf(amount), null, context);

        //Assert
        assertThat(result).isTrue();
    }

    @Test
    void Validate_NotSupportedOperator_Billing_AmountEqual_Failure() {
        //Arrange
        String assetId = "assetId";
        String identity = "identity";
        double amount = 10.0;
        inMemoryPaymentBook.addPayment(new PaymentKey(identity, assetId), amount);
        when(participantAgent.getIdentity()).thenReturn(identity);
        when(contractAgreement.getAssetId()).thenReturn(assetId);
        TransferProcessPolicyContext context = new TransferProcessPolicyContext(participantAgent, contractAgreement,
                Instant.now());
        List<Operator> notSupportedOperators = Arrays.stream(Operator.values()).
                filter(operator -> operator != Operator.EQ && operator != Operator.GT && operator != Operator.GEQ).
                toList();

        //Act
        boolean result = notSupportedOperators.stream().
                map(operator -> billingPolicyFunction.evaluate(operator, String.valueOf(amount), null, context)).
                reduce(false, (a, b) -> a || b);

        //Assert
        assertThat(result).isFalse();
    }
}
