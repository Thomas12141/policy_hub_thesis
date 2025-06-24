package org.example.time_frame_policy;

import org.eclipse.edc.policy.model.Operator;
import org.eclipse.edc.spi.monitor.Monitor;
import org.junit.jupiter.api.Test;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TimeFramePolicyFunctionTest {

    private final TimeFramePolicyFunction timeFramePolicyFunction = new TimeFramePolicyFunction(new Monitor() {});

    @Test
    void Validate_EQ_TimeFrame_Success() {
        //Arrange
        ZonedDateTime rightValue = ZonedDateTime.now(ZoneOffset.UTC).withNano(0);

        //Act
        boolean result = timeFramePolicyFunction.evaluate(Operator.EQ, rightValue, null, null);

        //Assert
        assertThat(result).isTrue();
    }

    @Test
    void Validate_EQ_TimeFrame_Failure() {
        //Arrange
        ZonedDateTime rightValue = ZonedDateTime.now(ZoneOffset.UTC).withNano(2);

        //Act
        boolean result = timeFramePolicyFunction.evaluate(Operator.EQ, rightValue, null, null);

        //Assert
        assertThat(result).isFalse();
    }

    @Test
    void Validate_GT_TimeFrame_Success() {
        //Arrange
        ZonedDateTime rightValue = ZonedDateTime.now(ZoneOffset.UTC).minusSeconds(1);

        //Act
        boolean result = timeFramePolicyFunction.evaluate(Operator.GT, rightValue, null, null);

        //Assert
        assertThat(result).isTrue();
    }

    @Test
    void Validate_GT_TimeFrame_Failure() {
        //Arrange
        ZonedDateTime rightValue = ZonedDateTime.now(ZoneOffset.UTC).withNano(0);

        //Act
        boolean result = timeFramePolicyFunction.evaluate(Operator.GT, rightValue, null, null);

        //Assert
        assertThat(result).isFalse();
    }

    @Test
    void Validate_LT_TimeFrame_Success() {
        //Arrange
        ZonedDateTime rightValue = ZonedDateTime.now(ZoneOffset.UTC).minusNanos(1);

        //Act
        boolean result = timeFramePolicyFunction.evaluate(Operator.LT, rightValue, null, null);

        //Assert
        assertThat(result).isTrue();
    }

    @Test
    void Validate_LT_TimeFrame_Failure() {
        //Arrange
        ZonedDateTime rightValue = ZonedDateTime.now(ZoneOffset.UTC).withNano(0);

        //Act
        boolean result = timeFramePolicyFunction.evaluate(Operator.LT, rightValue, null, null);

        //Assert
        assertThat(result).isFalse();
    }

    @Test
    void Validate_NEQ_TimeFrame_Success() {
        //Arrange
        ZonedDateTime rightValue = ZonedDateTime.now(ZoneOffset.UTC).withNano(0);

        //Act
        boolean result = timeFramePolicyFunction.evaluate(Operator.NEQ, rightValue, null, null);

        //Assert
        assertThat(result).isFalse();
    }

    @Test
    void Validate_NEQ_TimeFrame_Failure() {
        //Arrange
        ZonedDateTime rightValue = ZonedDateTime.now(ZoneOffset.UTC).withNano(2);

        //Act
        boolean result = timeFramePolicyFunction.evaluate(Operator.NEQ, rightValue, null, null);

        //Assert
        assertThat(result).isTrue();
    }

    @Test
    void Validate_LEQ_Less_TimeFrame_Success() {
        //Arrange
        ZonedDateTime rightValue = ZonedDateTime.now(ZoneOffset.UTC).minusNanos(1);

        //Act
        boolean result = timeFramePolicyFunction.evaluate(Operator.LEQ, rightValue, null, null);

        //Assert
        assertThat(result).isTrue();
    }

    @Test
    void Validate_LEQ_Equal_TimeFrame_Success() {
        //Arrange
        ZonedDateTime rightValue = ZonedDateTime.now(ZoneOffset.UTC).withNano(0);

        //Act
        boolean result = timeFramePolicyFunction.evaluate(Operator.LEQ, rightValue, null, null);

        //Assert
        assertThat(result).isTrue();
    }

    @Test
    void Validate_LEQ_Less_TimeFrame_Failure() {
        //Arrange
        ZonedDateTime rightValue = ZonedDateTime.now(ZoneOffset.UTC).minusSeconds(1);

        //Act
        boolean result = timeFramePolicyFunction.evaluate(Operator.LEQ, rightValue, null, null);

        //Assert
        assertThat(result).isFalse();
    }

    @Test
    void Validate_GEQ_Greater_TimeFrame_Success() {
        //Arrange
        ZonedDateTime rightValue = ZonedDateTime.now(ZoneOffset.UTC).minusSeconds(1);

        //Act
        boolean result = timeFramePolicyFunction.evaluate(Operator.GEQ, rightValue, null, null);

        //Assert
        assertThat(result).isTrue();
    }

    @Test
    void Validate_GEQ_Equal_TimeFrame_Success() {
        //Arrange
        ZonedDateTime rightValue = ZonedDateTime.now(ZoneOffset.UTC).withNano(0);

        //Act
        boolean result = timeFramePolicyFunction.evaluate(Operator.GEQ, rightValue, null, null);

        //Assert
        assertThat(result).isTrue();
    }

    @Test
    void Validate_GEQ_Greater_TimeFrame_Failure() {
        //Arrange
        ZonedDateTime rightValue = ZonedDateTime.now(ZoneOffset.UTC).plusSeconds(1);

        //Act
        boolean result = timeFramePolicyFunction.evaluate(Operator.GEQ, rightValue, null, null);

        //Assert
        assertThat(result).isFalse();
    }

    @Test
    void Validate_UnsupportedOperators_TimeFrame_Failure() {
        //Arrange
        ZonedDateTime rightValue = ZonedDateTime.now(ZoneOffset.UTC).plusSeconds(1);
        List<Operator> notSupportedOperators = Arrays.stream(Operator.values()).
                filter(operator -> operator != Operator.EQ &&
                        operator != Operator.GT &&
                        operator != Operator.LT &&
                        operator != Operator.NEQ &&
                        operator != Operator.LEQ &&
                        operator != Operator.GEQ).toList();

        //Act
        boolean result = notSupportedOperators.stream().
                map(operator -> timeFramePolicyFunction.evaluate(operator, rightValue, null, null)).
                reduce(false, (a, b) -> a || b);

        //Assert
        assertThat(result).isFalse();
    }
}
