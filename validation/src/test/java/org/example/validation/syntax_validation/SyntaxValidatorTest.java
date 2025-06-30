package org.example.validation.syntax_validation;

import org.example.utils.TestPolicies;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;


import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
@Timeout(value = 1, unit = TimeUnit.SECONDS)
class SyntaxValidatorTest {

    private static final SyntaxValidator validator = new SyntaxValidator();

    @Test
    void EmptyString() {
        //Arrange
        String policy = TestPolicies.EMPTY_STRING;

        //Act
        List<String> result = validator.validate(policy);

        //Assert
        assertThat(result).isNotEmpty();
    }

    @Test
    void NullPointer() {
        //Arrange
        String policy = TestPolicies.NULL_POINTER;

        //Act
        List<String> result = validator.validate(policy);

        //Assert
        assertThat(result).anyMatch(s -> s.contains("Policy is null")).hasSize(1);
    }

    @Test
    void Empty_JSON() {
        //Arrange
        String policy = TestPolicies.EMPTY_JSON;


        //Act
        List<String> result = validator.validate(policy);

        //Assert
        assertThat(result).anyMatch(
                s -> s.contains("uid") ||
                        s.contains("context") ||
                        s.contains("permission") ||
                        s.contains("obligation") ||
                        s.contains("prohibition")
        ).hasSize(5);
    }

    @Test
    void WithoutContext_JSON() {
        //Arrange
        String policy = TestPolicies.WITHOUT_CONTEXT;


        //Act
        List<String> result = validator.validate(policy);

        //Assert
        assertThat(result).hasSize(1).anyMatch(s -> s.contains("context"));
    }

    @Test
    void ValidPolicy_JSON() {
        //Arrange
        String policy = TestPolicies.VALID_POLICY;

        //Act
        List<String> result = validator.validate(policy);

        //Assert
        assertThat(result).isEmpty();
    }

    @Test
    void MissingRequiredUid_JSON() {
        //Arrange
        String policy = TestPolicies.MISSING_REQUIRED_UID;

        //Act
        List<String> result = validator.validate(policy);

        //Assert
        assertThat(result).hasSize(1).anyMatch(s -> s.contains("uid"));
    }

    @Test
    void ODRLExample1_JSON() {
        //Arrange
        String policy = TestPolicies.ODRL_EXAMPLE_1;

        //Act
        List<String> result = validator.validate(policy);

        //Assert
        assertThat(result).isEmpty();
    }


    @Test
    void ODRLExample2_JSON() {
        //Arrange
        String policy = TestPolicies.ODRL_EXAMPLE_2;

        //Act
        List<String> result = validator.validate(policy);

        //Assert
        assertThat(result).isEmpty();
    }

    @Test
    void ODRLExample3_JSON() {
        //Arrange
        String policy = TestPolicies.ODRL_EXAMPLE_3;

        //Act
        List<String> result = validator.validate(policy);

        //Assert
        assertThat(result).isEmpty();
    }

    @Test
    void ODRLExample4_JSON() {
        //Arrange
        String policy = TestPolicies.ODRL_EXAMPLE_4;

        //Act
        List<String> result = validator.validate(policy);

        //Assert
        assertThat(result).isEmpty();
    }

    @Test
    void ODRLExample5_JSON() {
        //Arrange
        String policy = TestPolicies.ODRL_EXAMPLE_5;

        //Act
        List<String> result = validator.validate(policy);

        //Assert
        assertThat(result).isEmpty();
    }

    @Test
    void ODRLExample8_JSON() {
        //Arrange
        String policy = TestPolicies.ODRL_EXAMPLE_8;

        //Act
        List<String> result = validator.validate(policy);

        //Assert
        assertThat(result).isEmpty();
    }

    @Test
    void ODRLExample9_JSON() {
        //Arrange
        String policy = TestPolicies.ODRL_EXAMPLE_9;

        //Act
        List<String> result = validator.validate(policy);

        //Assert
        assertThat(result).isEmpty();
    }

    @Test
    void ODRLExample12_JSON() {
        //Arrange
        String policy = TestPolicies.ODRL_EXAMPLE_12;

        //Act
        List<String> result = validator.validate(policy);

        //Assert
        assertThat(result).isEmpty();
    }

    @Test
    void ODRLExample13_JSON() {
        //Arrange
        String policy = TestPolicies.ODRL_EXAMPLE_13;

        //Act
        List<String> result = validator.validate(policy);

        //Assert
        assertThat(result).isEmpty();
    }

    @Test
    void PolicyWithDateTime_JSON() {
        //Arrange
        String policy = """
                    {
                      "@context": "http://www.w3.org/ns/odrl.jsonld",
                      "@type": "Set",
                      "uid": "http://example.com/policy:9090",
                      "permission": [{
                        "action": ["use"],
                        "target": "http://example.com/asset:2002",
                        "constraint": [{
                          "leftOperand": "DateTime",
                          "operator": "eq",
                          "rightOperand": "2023-01-01T00:00:00Z"
                        }]
                      }]
                    }
                """;

        //Act
        List<String> result = validator.validate(policy);

        //Assert
        assertThat(result).isEmpty();
    }
}
