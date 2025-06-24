package org.example.location_policy;

import org.eclipse.edc.connector.controlplane.contract.spi.policy.ContractNegotiationPolicyContext;
import org.eclipse.edc.participant.spi.ParticipantAgent;
import org.eclipse.edc.policy.model.Operator;
import org.eclipse.edc.spi.monitor.Monitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LocationPolicyFunctionTest {
    private final LocationConstraintFunction locationConstraintFunction = new LocationConstraintFunction(new Monitor() {});

    private final String myLocation = "EU";

    private final String otherLocation = "US";

    private ContractNegotiationPolicyContext context;

    @BeforeEach
    void setup() {
        String regionKey = "region";
        context = new ContractNegotiationPolicyContext(new ParticipantAgent(Map.of(regionKey, myLocation), Collections.emptyMap()));
    }

    @Test
    void Validate_EQ_Location_Success() {
        //Arrange

        //Act
        boolean result = locationConstraintFunction.evaluate(Operator.EQ, myLocation, null, context);

        //Assert
        assertThat(result).isTrue();
    }

    @Test
    void Validate_EQ_Location_Failure() {
        //Arrange

        //Act
        boolean result = locationConstraintFunction.evaluate(Operator.EQ, otherLocation, null, context);

        //Assert
        assertThat(result).isFalse();
    }

    @Test
    void Validate_NEQ_Location_Success() {
        //Arrange

        //Act
        boolean result = locationConstraintFunction.evaluate(Operator.NEQ, otherLocation, null, context);

        //Assert
        assertThat(result).isTrue();
    }

    @Test
    void Validate_NEQ_Location_Failure() {
        //Arrange

        //Act
        boolean result = locationConstraintFunction.evaluate(Operator.NEQ, myLocation, null, context);

        //Assert
        assertThat(result).isFalse();
    }

    @Test
    void Validate_IN_Location_Success() {
        //Arrange

        //Act
        boolean result = locationConstraintFunction.evaluate(Operator.IN, List.of(myLocation), null, context);

        //Assert
        assertThat(result).isTrue();
    }

    @Test
    void Validate_IN_Location_Failure() {
        //Arrange

        //Act
        boolean result = locationConstraintFunction.evaluate(Operator.IN, List.of(otherLocation), null, context);

        //Assert
        assertThat(result).isFalse();
    }

    @Test
    void Validate_UnsupportedOperators_Location_Failure() {
        //Arrange
        List<Operator> notSupportedOperators = Arrays.stream(Operator.values()).
                filter(operator -> operator != Operator.EQ && operator != Operator.NEQ && operator != Operator.IN).
                toList();

        //Act
        boolean result = notSupportedOperators.stream().
                map(operator -> locationConstraintFunction.evaluate(operator, List.of(otherLocation), null, context)).
                reduce(false, (a, b) -> a || b);

        //Assert
        assertThat(result).isFalse();
    }
}
