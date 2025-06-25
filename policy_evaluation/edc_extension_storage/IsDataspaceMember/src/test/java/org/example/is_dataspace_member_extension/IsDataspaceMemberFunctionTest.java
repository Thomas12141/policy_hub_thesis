package org.example.is_dataspace_member_extension;

import org.eclipse.edc.connector.controlplane.contract.spi.policy.ContractNegotiationPolicyContext;
import org.eclipse.edc.iam.verifiablecredentials.spi.model.CredentialSubject;
import org.eclipse.edc.iam.verifiablecredentials.spi.model.Issuer;
import org.eclipse.edc.iam.verifiablecredentials.spi.model.VerifiableCredential;
import org.eclipse.edc.participant.spi.ParticipantAgent;
import org.eclipse.edc.policy.model.Operator;
import org.eclipse.edc.spi.monitor.Monitor;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.time.Instant.now;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class IsDataspaceMemberFunctionTest {
    private final IsDataspaceMemberFunction isDataspaceMemberFunction = new IsDataspaceMemberFunction(new Monitor() {});

    @Test
    void Validate_EQ_Vc_MembershipCredentialWithoutURL_Success() {
        //Arrange
        var verifiableCredential = createCredentialBuilder().type("MembershipCredential").build();
        ParticipantAgent participantAgent =
                new ParticipantAgent(Map.of("vc", List.of(verifiableCredential)), Collections.emptyMap());

        ContractNegotiationPolicyContext context = new ContractNegotiationPolicyContext(participantAgent);

        //Act
        boolean result = isDataspaceMemberFunction.evaluate(Operator.EQ, "ACTIVE", null, context);

        //Assert
        assertThat(result).isTrue();
    }

    @Test
    void Validate_EQ_Vc_MembershipCredentialWithURL_Success() {
        //Arrange
        var verifiableCredential = createCredentialBuilder().type("https://example.org/dataspace/credentials/MembershipCredential").build();
        ParticipantAgent participantAgent =
                new ParticipantAgent(Map.of("vc", List.of(verifiableCredential)), Collections.emptyMap());

        ContractNegotiationPolicyContext context = new ContractNegotiationPolicyContext(participantAgent);

        //Act
        boolean result = isDataspaceMemberFunction.evaluate(Operator.EQ, "ACTIVE", null, context);

        //Assert
        assertThat(result).isTrue();
    }

    @Test
    void Validate_EQ_Vc_Failure() {
        //Arrange
        var verifiableCredential = createCredentialBuilder().type("MembershipCredentialg").build();
        ParticipantAgent participantAgent =
                new ParticipantAgent(Map.of("vc", List.of(verifiableCredential)), Collections.emptyMap());

        ContractNegotiationPolicyContext context = new ContractNegotiationPolicyContext(participantAgent);

        //Act
        boolean result = isDataspaceMemberFunction.evaluate(Operator.EQ, "ACTIVE", null, context);

        //Assert
        assertThat(result).isFalse();
    }

    @Test
    void Validate_EQ_Vc_RightValueIsNotActive_Failure() {
        //Arrange
        var verifiableCredential = createCredentialBuilder().type("MembershipCredential").build();
        ParticipantAgent participantAgent =
                new ParticipantAgent(Map.of("vc", List.of(verifiableCredential)), Collections.emptyMap());

        ContractNegotiationPolicyContext context = new ContractNegotiationPolicyContext(participantAgent);

        //Act
        boolean result = isDataspaceMemberFunction.evaluate(Operator.EQ, "SomeValue", null, context);

        //Assert
        assertThat(result).isFalse();
    }

    @Test
    void Validate_EQ_VcIsNotAVcType_Failure() {
        //Arrange
        ParticipantAgent participantAgent =
                new ParticipantAgent(Map.of("vc", List.of("")), Collections.emptyMap());

        ContractNegotiationPolicyContext context = new ContractNegotiationPolicyContext(participantAgent);

        //Act
        boolean result = isDataspaceMemberFunction.evaluate(Operator.EQ, "ACTIVE", null, context);

        //Assert
        assertThat(result).isFalse();
    }

    @Test
    void Validate_EQ_VcIsNotAList_Failure() {
        //Arrange
        ParticipantAgent participantAgent =
                new ParticipantAgent(Map.of("vc", Collections.emptyMap()), Collections.emptyMap());

        ContractNegotiationPolicyContext context = new ContractNegotiationPolicyContext(participantAgent);

        //Act
        boolean result = isDataspaceMemberFunction.evaluate(Operator.EQ, "ACTIVE", null, context);

        //Assert
        assertThat(result).isFalse();
    }

    @Test
    void Validate_EQ_VcIsEmptyList_Failure() {
        //Arrange
        ParticipantAgent participantAgent =
                new ParticipantAgent(Map.of("vc", Collections.emptyList()), Collections.emptyMap());

        ContractNegotiationPolicyContext context = new ContractNegotiationPolicyContext(participantAgent);

        //Act
        boolean result = isDataspaceMemberFunction.evaluate(Operator.EQ, "ACTIVE", null, context);

        //Assert
        assertThat(result).isFalse();
    }

    @Test
    void Validate_NotSupportedOperator_Identity_Failure() {
        //Arrange
        var verifiableCredential = createCredentialBuilder().type("MembershipCredential").build();
        ParticipantAgent participantAgent =
                new ParticipantAgent(Map.of("vc", List.of(verifiableCredential)), Collections.emptyMap());
        List<Operator> notSupportedOperators = Arrays.stream(Operator.values()).
                filter(operator -> operator != Operator.EQ).toList();

        ContractNegotiationPolicyContext context = new ContractNegotiationPolicyContext(participantAgent);

        //Act
        boolean result = notSupportedOperators.stream().
                map(operator -> isDataspaceMemberFunction.evaluate(operator, "ACTIVE", null, context)).
                reduce(false, (a, b) -> a || b);

        //Assert
        assertThat(result).isFalse();
    }

    private VerifiableCredential.Builder createCredentialBuilder() {
        return VerifiableCredential.Builder.newInstance()
                .credentialSubject(CredentialSubject.Builder.newInstance()
                        .id("test-subject-id")
                        .claim("test-claim", "test-value")
                        .build())
                .type("test-type")
                .issuer(new Issuer("http://test.issuer", Collections.emptyMap()))
                .issuanceDate(now());
    }
}
