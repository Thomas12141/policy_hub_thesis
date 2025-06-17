package org.example.validation.semantic_validation;

import org.example.validation.Type;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SemanticValidationTest {

    private final SemanticValidator validator = new SemanticValidator();


    @Test
    void testIsDataspaceMemberPolicy_Invalid_InDuty() {
        //Arrange
        String policy = """
            {
              "@context": "http://www.w3.org/ns/odrl.jsonld",
              "@type": "Set",
              "uid": "http://example.com/policy:1234",
              "duty": [{
                "action": "use",
                "target": "http://example.com/resource:1234",
                "constraint": [{
                  "leftOperand": "Membership",
                  "operator": "eq",
                  "rightOperand": "ACTIVE"
                }]
              }]
            }""";

        //Act
        List<String> errors = validator.validate(policy, Type.JSON);

        //Assert
        assertThat(errors).hasSize(1).contains("Membership must not appear in duty");
    }

    @Test
    void testIsDataspaceMemberPolicy_Invalid_InProhibition() {
        //Arrange
        String policy = """
            {
              "@context": "http://www.w3.org/ns/odrl.jsonld",
              "@type": "Set",
              "uid": "http://example.com/policy:1234",
              "prohibition": [{
                "action": "use",
                "target": "http://example.com/resource:1234",
                "constraint": [{
                  "leftOperand": "Membership",
                  "operator": "eq",
                  "rightOperand": "ACTIVE"
                }]
              }]
            }""";

        //Act
        List<String> errors = validator.validate(policy, Type.JSON);

        //Assert
        assertThat(errors).hasSize(1).contains("Membership must not appear in prohibition");
    }

    @Test
    void testAccessPolicy_Valid() {
        //Arrange
        String policy = """
            {
              "@context": "http://www.w3.org/ns/odrl.jsonld",
              "@type": "Set",
              "uid": "http://example.com/policy:1234",
              "assignee": "http://example.com/user:1234",
              "permission": [{
                "action": "use",
                "target": "http://example.com/resource:1234"
              }]
            }""";

        //Act
        List<String> errors = validator.validate(policy, Type.JSON);

        //Assert
        assertThat(errors).isEmpty();
    }

    @Test
    void testAccessPolicy_Invalid() {
        //Arrange
        String policy = """
            {
              "@context": "http://www.w3.org/ns/odrl.jsonld",
              "@type": "Set",
              "uid": "http://example.com/policy:1234",
              "assignee": "invalid-uri",
              "permission": [{
                "action": "use",
                "target": "http://example.com/resource:1234"
              }]
            }""";

        List<String> errors = validator.validate(policy, Type.JSON);

        //Assert
        assertThat(errors).hasSize(1).contains("AccessPolicy: Assignee must be a valid URI");
    }

    @Test
    void testCountPolicy_Valid() {
        //Arrange
        String policy = """
            {
              "@context": "http://www.w3.org/ns/odrl.jsonld",
              "@type": "Set",
              "uid": "http://example.com/policy:1234",
              "permission": [{
                "action": "use",
                "target": "http://example.com/resource:1234",
                "constraint": [{
                  "leftOperand": "NumberOfTransfers",
                  "operator": "lteq",
                  "rightOperand": "3"
                }]
              }]
            }""";

        //Act
        List<String> errors = validator.validate(policy, Type.JSON);

        //Assert
        assertThat(errors).isEmpty();
    }

    @Test
    void testCountPolicy_Invalid() {
        //Arrange
        String policy = """
            {
              "@context": "http://www.w3.org/ns/odrl.jsonld",
              "@type": "Set",
              "uid": "http://example.com/policy:1234",
              "permission": [{
                "action": "use",
                "target": "http://example.com/resource:1234",
                "constraint": [{
                  "leftOperand": "NumberOfTransfers",
                  "operator": "lteq",
                  "rightOperand": "invalid"
                }]
              }]
            }""";

        //Act
        List<String> errors = validator.validate(policy, Type.JSON);

        //Assert
        assertThat(errors).hasSize(1).contains("CountPolicy: NumberOfTransfers must be a positive integer");
    }

    @Test
    void testTimeFramePolicy_Valid() {
        //Arrange
        String policy = """
            {
              "@context": "http://www.w3.org/ns/odrl.jsonld",
              "@type": "Set",
              "uid": "http://example.com/policy:1234",
              "permission": [{
                "action": "use",
                "target": "http://example.com/resource:1234",
                "constraint": [{
                  "leftOperand": "DateTime",
                  "operator": "lteq",
                  "rightOperand": "2024-12-31T23:59:59Z"
                }]
              }]
            }""";

        //Act
        List<String> errors = validator.validate(policy, Type.JSON);

        //Assert
        assertThat(errors).isEmpty();
    }

    @Test
    void testTimeFramePolicy_Invalid() {
        //Arrange
        String policy = """
            {
              "@context": "http://www.w3.org/ns/odrl.jsonld",
              "@type": "Set",
              "uid": "http://example.com/policy:1234",
              "permission": [{
                "action": "use",
                "target": "http://example.com/resource:1234",
                "constraint": [{
                  "leftOperand": "DateTime",
                  "operator": "lteq",
                  "rightOperand": "2024-12-31T23:59:59Z"
                },
                {
                  "leftOperand": "DateTime",
                  "operator": "gt",
                  "rightOperand": "2025-12-31T23:59:59Z"
                }]
              }]
            }""";

        //Act
        List<String> errors = validator.validate(policy, Type.JSON);

        //Assert
        assertThat(errors).hasSize(1).contains("TimeFramePolicy: Start date must be before end date");
    }

    @Test
    void testLocationPolicy_Valid() {
        //Arrange
        String policy = """
            {
              "@context": "http://www.w3.org/ns/odrl.jsonld",
              "@type": "Set",
              "uid": "http://example.com/policy:1234",
              "permission": [{
                "action": "use",
                "target": "http://example.com/resource:1234",
                "constraint": [{
                  "leftOperand": "spatial",
                  "operator": "eq",
                  "rightOperand": "DE"
                }]
              }]
            }""";

        //Act
        List<String> errors = validator.validate(policy, Type.JSON);

        //Assert
        assertThat(errors).isEmpty();
    }

    @Test
    void testLocationPolicy_Invalid() {
        //Arrange
        String policy = """
            {
              "@context": "http://www.w3.org/ns/odrl.jsonld",
              "@type": "Set",
              "uid": "http://example.com/policy:1234",
              "permission": [{
                "action": "use",
                "target": "http://example.com/resource:1234",
                "constraint": [{
                  "leftOperand": "location",
                  "operator": "eq",
                  "rightOperand": "INVALID"
                }]
              }]
            }""";

        //Act
        List<String> errors = validator.validate(policy, Type.JSON);

        //Assert
        assertThat(errors).hasSize(1).contains("LocationPolicy: Location must be a valid ISO country code");
    }

    @Test
    void testBillingPolicy_Valid() {
        //Arrange
        String policy = """
            {
              "@context": "http://www.w3.org/ns/odrl.jsonld",
              "@type": "Set",
              "uid": "http://example.com/policy:1234",
              "permission": [{
                "action": "use",
                "target": "http://example.com/resource:1234",
                "constraint": [{
                  "leftOperand": "payment",
                  "operator": "eq",
                  "rightOperand": "99.99"
                }]
              }]
            }""";

        //Act
        List<String> errors = validator.validate(policy, Type.JSON);

        //Assert
        assertThat(errors).isEmpty();
    }

    @Test
    void testBillingPolicy_Invalid_NegativeAmount() {
        //Arrange
        String policy = """
            {
              "@context": "http://www.w3.org/ns/odrl.jsonld",
              "@type": "Set",
              "uid": "http://example.com/policy:1234",
              "permission": [{
                "action": "use",
                "target": "http://example.com/resource:1234",
                "constraint": [{
                  "leftOperand": "payment",
                  "operator": "eq",
                  "rightOperand": "-10.00"
                }]
              }]
            }""";

        //Act
        List<String> errors = validator.validate(policy, Type.JSON);

        //Assert
        assertThat(errors).hasSize(1).contains("BillingPolicy: Payment amount must be greater than 0");
    }

    @Test
    void testBillingPolicy_Invalid_Format() {
        //Arrange
        String policy = """
            {
              "@context": "http://www.w3.org/ns/odrl.jsonld",
              "@type": "Set",
              "uid": "http://example.com/policy:1234",
              "permission": [{
                "action": "use",
                "target": "http://example.com/resource:1234",
                "constraint": [{
                  "leftOperand": "payment",
                  "operator": "eq",
                  "rightOperand": "invalid"
                }]
              }]
            }""";

        //Act
        List<String> errors = validator.validate(policy, Type.JSON);

        //Assert
        assertThat(errors).hasSize(1).contains("BillingPolicy: Invalid payment amount format");
    }
}