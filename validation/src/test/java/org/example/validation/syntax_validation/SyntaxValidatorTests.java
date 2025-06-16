package org.example.validation.syntax_validation;

import org.example.utils.TestPolicies;
import org.example.validation.Type;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;


import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
@Timeout(value = 1, unit = TimeUnit.SECONDS)
class SyntaxValidatorTests {

    private static final SyntaxValidator validator = new SyntaxValidator();

    @Test
    void EmptyString() {
        //Arrange
        String policy = TestPolicies.EMPTY_STRING;

        //Act
        List<String> result = validator.validate(policy, Type.JSON);

        //Assert
        assertThat(result).isNotEmpty();
    }

    @Test
    void NullPointer() {
        //Arrange
        String policy = TestPolicies.NULL_POINTER;

        //Act
        List<String> result = validator.validate(policy, Type.JSON);

        //Assert
        assertThat(result).anyMatch(s -> s.contains("Policy is null")).hasSize(1);
    }

    @Test
    void Empty_JSON() {
        //Arrange
        String policy = TestPolicies.EMPTY_JSON;


        //Act
        List<String> result = validator.validate(policy, Type.JSON);

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
        List<String> result = validator.validate(policy, Type.JSON);

        //Assert
        assertThat(result).hasSize(1).anyMatch(s -> s.contains("context"));
    }

    @Test
    void ValidPolicy_JSON() {
        //Arrange
        String policy = TestPolicies.VALID_POLICY;

        //Act
        List<String> result = validator.validate(policy, Type.JSON);

        //Assert
        assertThat(result).isEmpty();
    }

    @Test
    void MissingRequiredUid_JSON() {
        //Arrange
        String policy = TestPolicies.MISSING_REQUIRED_UID;

        //Act
        List<String> result = validator.validate(policy, Type.JSON);

        //Assert
        assertThat(result).hasSize(1).anyMatch(s -> s.contains("uid"));
    }

    @Test
    void ODRLExample1_JSON() {
        //Arrange
        String policy = TestPolicies.ODRL_EXAMPLE_1;

        //Act
        List<String> result = validator.validate(policy, Type.JSON);

        //Assert
        assertThat(result).isEmpty();
    }


    @Test
    void ODRLExample2_JSON() {
        //Arrange
        String policy = TestPolicies.ODRL_EXAMPLE_2;

        //Act
        List<String> result = validator.validate(policy, Type.JSON);

        //Assert
        assertThat(result).isEmpty();
    }

    @Test
    void ODRLExample3_JSON() {
        //Arrange
        String policy = TestPolicies.ODRL_EXAMPLE_3;

        //Act
        List<String> result = validator.validate(policy, Type.JSON);

        //Assert
        assertThat(result).isEmpty();
    }

    @Test
    void ODRLExample4_JSON() {
        //Arrange
        String policy = TestPolicies.ODRL_EXAMPLE_4;

        //Act
        List<String> result = validator.validate(policy, Type.JSON);

        //Assert
        assertThat(result).isEmpty();
    }

    @Test
    void ODRLExample5_JSON() {
        //Arrange
        String policy = TestPolicies.ODRL_EXAMPLE_5;

        //Act
        List<String> result = validator.validate(policy, Type.JSON);

        //Assert
        assertThat(result).isEmpty();
    }

    @Test
    void ODRLExample8_JSON() {
        //Arrange
        String policy = TestPolicies.ODRL_EXAMPLE_8;

        //Act
        List<String> result = validator.validate(policy, Type.JSON);

        //Assert
        assertThat(result).isEmpty();
    }

    @Test
    void ODRLExample9_JSON() {
        //Arrange
        String policy = TestPolicies.ODRL_EXAMPLE_9;

        //Act
        List<String> result = validator.validate(policy, Type.JSON);

        //Assert
        assertThat(result).isEmpty();
    }

    @Test
    void ODRLExample12_JSON() {
        //Arrange
        String policy = TestPolicies.ODRL_EXAMPLE_12;

        //Act
        List<String> result = validator.validate(policy, Type.JSON);

        //Assert
        assertThat(result).isEmpty();
    }

    @Test
    void ODRLExample13_JSON() {
        //Arrange
        String policy = TestPolicies.ODRL_EXAMPLE_13;

        //Act
        List<String> result = validator.validate(policy, Type.JSON);

        //Assert
        assertThat(result).isEmpty();
    }

    @Test
    void ODRLExample14_JSON() {
        //Arrange
        String policy = TestPolicies.ODRL_EXAMPLE_14;

        //Act
        List<String> result = validator.validate(policy, Type.YAML);

        //Assert
        assertThat(result).isEmpty();
    }

    @Test
    void ODRLExample16_JSON() {
        //Arrange
        String policy = TestPolicies.ODRL_EXAMPLE_16;

        //Act
        List<String> result = validator.validate(policy, Type.YAML);

        //Assert
        assertThat(result).isEmpty();
    }

    @Test
    void ODRLExample17_JSON() {
        //Arrange
        String policy = TestPolicies.ODRL_EXAMPLE_17;

        //Act
        List<String> result = validator.validate(policy, Type.YAML);

        //Assert
        assertThat(result).isEmpty();
    }

    @Test
    void ODRLExample18_JSON() {
        //Arrange
        String policy = TestPolicies.ODRL_EXAMPLE_18;

        //Act
        List<String> result = validator.validate(policy, Type.YAML);

        //Assert
        assertThat(result).isEmpty();
    }

    @Test
    void ODRLExample19_JSON() {
        //Arrange
        String policy = TestPolicies.ODRL_EXAMPLE_19;

        //Act
        List<String> result = validator.validate(policy, Type.YAML);

        //Assert
        assertThat(result).isEmpty();
    }

    @Test
    void ODRLExample20_JSON() {
        //Arrange
        String policy = TestPolicies.ODRL_EXAMPLE_20;

        //Act
        List<String> result = validator.validate(policy, Type.YAML);

        //Assert
        assertThat(result).isEmpty();
    }

    @Test
    void ODRLExample21_JSON() {
        //Arrange
        String policy = TestPolicies.ODRL_EXAMPLE_21;

        //Act
        List<String> result = validator.validate(policy, Type.YAML);

        //Assert
        assertThat(result).isEmpty();
    }

    @Test
    void ODRLExample22_JSON() {
        //Arrange
        String policy = TestPolicies.ODRL_EXAMPLE_22;

        //Act
        List<String> result = validator.validate(policy, Type.YAML);

        //Assert
        assertThat(result).isEmpty();
    }

    @Test
    void ODRLExample23_JSON() {
        //Arrange
        String policy = TestPolicies.ODRL_EXAMPLE_23;

        //Act
        List<String> result = validator.validate(policy, Type.YAML);

        //Assert
        assertThat(result).isEmpty();
    }

    @Test
    void ODRLExample24_JSON() {
        //Arrange
        String policy = TestPolicies.ODRL_EXAMPLE_24;

        //Act
        List<String> result = validator.validate(policy, Type.YAML);

        //Assert
        assertThat(result).isEmpty();
    }

    @Test
    void ODRLExample25_JSON() {
        //Arrange
        String policy = TestPolicies.ODRL_EXAMPLE_25;

        //Act
        List<String> result = validator.validate(policy, Type.YAML);

        //Assert
        assertThat(result).isEmpty();
    }

    @Test
    void ODRLExample26_JSON() {
        //Arrange
        String policy = TestPolicies.ODRL_EXAMPLE_26;

        //Act
        List<String> result = validator.validate(policy, Type.YAML);

        //Assert
        assertThat(result).isEmpty();
    }

    @Test
    void ODRLExample27_JSON() {
        //Arrange
        String policy = TestPolicies.ODRL_EXAMPLE_27;

        //Act
        List<String> result = validator.validate(policy, Type.YAML);

        //Assert
        assertThat(result).isEmpty();
    }

    @Test
    void ODRLExample28_JSON() {
        //Arrange
        String policy = TestPolicies.ODRL_EXAMPLE_28;

        //Act
        List<String> result = validator.validate(policy, Type.YAML);

        //Assert
        assertThat(result).isEmpty();
    }

    @Test
    void ODRLExample29_JSON() {
        //Arrange
        String policy = TestPolicies.ODRL_EXAMPLE_29;

        //Act
        List<String> result = validator.validate(policy, Type.YAML);

        //Assert
        assertThat(result).isEmpty();
    }

    @Test
    void ODRLExample30_JSON() {
        //Arrange
        String policy = TestPolicies.ODRL_EXAMPLE_30;

        //Act
        List<String> result = validator.validate(policy, Type.YAML);

        //Assert
        assertThat(result).isEmpty();
    }

    @Test
    void ODRLExample31_JSON() {
        //Arrange
        String policy = TestPolicies.ODRL_EXAMPLE_31;

        //Act
        List<String> result = validator.validate(policy, Type.YAML);

        //Assert
        assertThat(result).isEmpty();
    }

    @Test
    void ODRLExample32_JSON() {
        //Arrange
        String policy = TestPolicies.ODRL_EXAMPLE_32;

        //Act
        List<String> result = validator.validate(policy, Type.YAML);

        //Assert
        assertThat(result).isEmpty();
    }

    @Test
    void ODRLExample33_JSON() {
        //Arrange
        String policy = TestPolicies.ODRL_EXAMPLE_33;

        //Act
        List<String> result = validator.validate(policy, Type.YAML);

        //Assert
        assertThat(result).isEmpty();
    }

    @Test
    void ODRLExample34_JSON() {
        //Arrange
        String policy = TestPolicies.ODRL_EXAMPLE_34;

        //Act
        List<String> result = validator.validate(policy, Type.YAML);

        //Assert
        assertThat(result).isEmpty();
    }

    @Test
    void ODRLExample35_JSON() {
        //Arrange
        String policy = TestPolicies.ODRL_EXAMPLE_35;

        //Act
        List<String> result = validator.validate(policy, Type.YAML);

        //Assert
        assertThat(result).isEmpty();
    }

    /*
    @Test
    void ValidAgreement_YAML() {
        //Arrange
        String policy = """
            "@context": "http://www.w3.org/ns/odrl.jsonld"
            "@type": "Agreement"
            uid: "http://example.com/policy:3333"
            permission:
              - action: [ "use", "sell" ]
                target: "http://example.com/asset:8888"
            obligation:
              - action: [ "attribute" ]
                target: "http://example.com/asset:8888"
        """;

        //Act
        List<String> result = validator.validate(policy, Type.YAML);

        //Assert
        assertThat(result).isEmpty();
    }

    @Test
    void InvalidType_YAML() {
        //Arrange
        String policy = """
            "@context": "http://www.w3.org/ns/odrl.jsonld"
            "@type": "License"
            uid: "http://example.com/policy:abc"
            permission:
              - action: [ "play" ]
                target: "http://example.com/asset:abcd"
        """;

        //Act
        List<String> result = validator.validate(policy, Type.YAML);

        //Assert
        assertThat(result).isEmpty();
    }

    @Test
    void PolicyWithProhibition_JSON() {
        //Arrange
        String policy = """
            {
              "@context": "http://www.w3.org/ns/odrl.jsonld",
              "@type": "Set",
              "uid": "http://example.com/policy:9090",
              "permission": [{
                "action": ["use"],
                "target": "http://example.com/asset:2002"
              }],
              "prohibition": [{
                "action": ["delete"],
                "target": "http://example.com/asset:2002"
              }]
            }
        """;

        //Act
        List<String> result = validator.validate(policy, Type.YAML);

        //Assert
        assertThat(result).isEmpty();
    }

    @Test
    void PolicyWithConstraint_YAML() {
        //Arrange
        String policy = """
            "@context": "http://www.w3.org/ns/odrl.jsonld"
            "@type": "Policy"
            uid: "http://example.com/policy:with-constraint"
            permission:
              - action: [ "display" ]
                target: "http://example.com/asset:img"
                constraint:
                  - leftOperand: "spatial"
                    operator: "eq"
                    rightOperand: "eu"
        """;

        //Act
        List<String> result = validator.validate(policy, Type.YAML);

        //Assert
        assertThat(result).isEmpty();
    }
    */

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
        List<String> result = validator.validate(policy, Type.JSON);

        //Assert
        assertThat(result).isEmpty();
    }
}
