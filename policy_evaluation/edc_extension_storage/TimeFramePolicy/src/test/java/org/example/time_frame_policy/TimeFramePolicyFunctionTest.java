package org.example.time_frame_policy;

import org.eclipse.edc.policy.model.Operator;
import org.eclipse.edc.spi.monitor.Monitor;
import org.junit.jupiter.api.Test;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TimeFramePolicyFunctionTest {

    private final TimeFramePolicyFunction timeFramePolicyFunction = new TimeFramePolicyFunction(new Monitor() {});

    @Test
    void Validate_EQ_Identity_Success() {
        //Arrange
        ZonedDateTime rightValue = ZonedDateTime.now(ZoneOffset.UTC).withNano(0);

        //Act
        boolean result = timeFramePolicyFunction.evaluate(Operator.EQ, rightValue, null, null);

        //Assert
        assertThat(result).isTrue();
    }

    @Test
    void Validate_EQ_Identity_Failure() {
        //Arrange
        ZonedDateTime rightValue = ZonedDateTime.now(ZoneOffset.UTC).withNano(2);

        //Act
        boolean result = timeFramePolicyFunction.evaluate(Operator.EQ, rightValue, null, null);

        //Assert
        assertThat(result).isFalse();
    }

    @Test
    void Validate_GT_Identity_Success() {
        //Arrange
        ZonedDateTime rightValue = ZonedDateTime.now(ZoneOffset.UTC).minusSeconds(1);

        //Act
        boolean result = timeFramePolicyFunction.evaluate(Operator.GT, rightValue, null, null);

        //Assert
        assertThat(result).isTrue();
    }

    @Test
    void Validate_GT_Identity_Failure() {
        //Arrange
        ZonedDateTime rightValue = ZonedDateTime.now(ZoneOffset.UTC).withNano(0);

        //Act
        boolean result = timeFramePolicyFunction.evaluate(Operator.GT, rightValue, null, null);

        //Assert
        assertThat(result).isFalse();
    }

    @Test
    void Validate_LT_Identity_Success() {
        //Arrange
        ZonedDateTime rightValue = ZonedDateTime.now(ZoneOffset.UTC).minusNanos(1);

        //Act
        boolean result = timeFramePolicyFunction.evaluate(Operator.LT, rightValue, null, null);

        //Assert
        assertThat(result).isTrue();
    }

    @Test
    void Validate_LT_Identity_Failure() {
        //Arrange
        ZonedDateTime rightValue = ZonedDateTime.now(ZoneOffset.UTC).withNano(0);

        //Act
        boolean result = timeFramePolicyFunction.evaluate(Operator.LT, rightValue, null, null);

        //Assert
        assertThat(result).isFalse();
    }
}
