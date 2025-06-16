package org.example.validation.semantic_validation;

import org.example.validation.Type;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SemanticValidationTest {

    private final SemanticValidator validator = new SemanticValidator();


    @Test
    void testIsDataspaceMemberPolicy_Valid() {
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

        List<String> errors = validator.validate(policy, Type.JSON);
        assertThat(errors).hasSize(1).contains("Membership must not appear in duty");
    }

    @Test
    void testAccessPolicy_Valid() {
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

        List<String> errors = validator.validate(policy, Type.JSON);
        assertThat(errors).isEmpty();
    }

    @Test
    void testAccessPolicy_Invalid() {
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
        assertThat(errors).contains("AccessPolicy: Assignee must be a valid URI");
    }

    @Test
    void testCountPolicy_Valid() {
        String policy = """
            {
              "@context": "http://www.w3.org/ns/odrl.jsonld",
              "@type": "Set",
              "uid": "http://example.com/policy:1234",
              "permission": [{
                "action": "use",
                "target": "http://example.com/resource:1234",
                "constraint": [{
                  "leftOperand": "count",
                  "operator": "lteq",
                  "rightOperand": "3"
                }]
              }]
            }""";

        List<String> errors = validator.validate(policy, Type.JSON);
        assertThat(errors).isEmpty();
    }

    @Test
    void testCountPolicy_Invalid() {
        String policy = """
            {
              "@context": "http://www.w3.org/ns/odrl.jsonld",
              "@type": "Set",
              "uid": "http://example.com/policy:1234",
              "permission": [{
                "action": "use",
                "target": "http://example.com/resource:1234",
                "constraint": [{
                  "leftOperand": "count",
                  "operator": "lteq",
                  "rightOperand": "invalid"
                }]
              }]
            }""";

        List<String> errors = validator.validate(policy, Type.JSON);
        assertThat(errors).contains("CountPolicy: Count value must be a number");
    }

    @Test
    void testTimeFramePolicy_Valid() {
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

        List<String> errors = validator.validate(policy, Type.JSON);
        assertThat(errors).isEmpty();
    }

    @Test
    void testTimeFramePolicy_Invalid() {
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
                  "rightOperand": "2024-13-32"
                }]
              }]
            }""";

        List<String> errors = validator.validate(policy, Type.JSON);
        assertThat(errors).contains("TimeFramePolicy: Invalid datetime format. Use ISO 8601 format");
    }

    @Test
    void testLocationPolicy_Valid() {
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

        List<String> errors = validator.validate(policy, Type.JSON);
        assertThat(errors).isEmpty();
    }

    @Test
    void testLocationPolicy_Invalid() {
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
                  "rightOperand": "INVALID"
                }]
              }]
            }""";

        List<String> errors = validator.validate(policy, Type.JSON);
        assertThat(errors).contains("LocationPolicy: Location must be a valid ISO country code");
    }

    @Test
    void testBillingPolicy_Valid() {
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

        List<String> errors = validator.validate(policy, Type.JSON);
        assertThat(errors).isEmpty();
    }

    @Test
    void testBillingPolicy_Invalid_NegativeAmount() {
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

        List<String> errors = validator.validate(policy, Type.JSON);
        assertThat(errors).contains("BillingPolicy: Payment amount must be greater than 0");
    }

    @Test
    void testBillingPolicy_Invalid_Format() {
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

        List<String> errors = validator.validate(policy, Type.JSON);
        assertThat(errors).contains("BillingPolicy: Invalid payment amount format");
    }
}