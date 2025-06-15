package org.example.validation;

import org.junit.jupiter.api.Test;


import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

class ConcurrentAllValidatorsValidatorTests {

    private final static ConcurrentAllValidatorsValidator validator = new ConcurrentAllValidatorsValidator();
    
    @Test
    public void EmptyString() {
        //Arrange
        String policy = "";

        //Act
        List<String> result = validator.validate(policy, Type.JSON);

        //Assert
        assertThat(result).isNotEmpty();
    }

    @Test
    public void NullPointer() {
        //Arrange
        String policy = null;

        //Act
        List<String> result = validator.validate(policy, Type.JSON);

        //Assert
        assertThat(result).anyMatch(s -> s.contains("Policy is null")).hasSize(1);
    }

    @Test
    public void Empty_JSON() throws Exception{
        //Arrange
        String policy = """
                {
                }""";


        //Act
        List<String> result = validator.validate(policy, Type.JSON);

        //Assert
        assertThat(result).anyMatch(
                s-> s.contains("uid") ||
                        s.contains("context") ||
                        s.contains("permission") ||
                        s.contains("obligation") ||
                        s.contains("prohibition")
        ).hasSize(5);
    }

    @Test
    public void WithoutContext_JSON() throws Exception{
        //Arrange
        String policy = """
                {
                    "@type": "Set",
                    "uid": "http://example.com/policy:1010",
                    "permission": [{
                        "target": "http://example.com/asset:9898.movie",
                        "action": "use"
                    }]
                }""";


        //Act
        List<String> result = validator.validate(policy, Type.JSON);

        //Assert
        assertThat(result).hasSize(1).anyMatch(s -> s.contains("context"));
    }
    
    @Test
    public void ValidPolicy_JSON() throws Exception {
        //Arrange
        String policy = """
            {
              "@context": "http://www.w3.org/ns/odrl.jsonld",
              "@type": "Policy",
              "uid": "http://example.com/policy:2024",
              "permission": [{
                "action": ["use"],
                "target": "http://example.com/asset:1900"
              }]
            }
        """;

        //Act
        List<String> result = validator.validate(policy, Type.JSON);
        
        assertThat(result).isEmpty();
    }
    
    /*
    @Test
    public void MissingRequiredUid_JSON() throws Exception {
        //Arrange
        String policy = """
            {
              "@context": "http://www.w3.org/ns/odrl.jsonld",
              "@type": "Policy",
              "permission": [{
                "action": ["use"],
                "target": "http://example.com/asset:0000"
              }]
            }
        """;

        List<String> result = validator.validate(policy, Type.JSON);
        assertThat(result).isFalse();
    }

    @Test
    public void ODRLExample1_JSON() throws Exception {
        //Arrange
        String policy = """
            {
              "@context": "http://www.w3.org/ns/odrl.jsonld",
              "@type": "Set",
              "uid": "http://example.com/policy:1010",
              "permission": [{
                "target": "http://example.com/asset:9898.movie",
                "action": "use"
                }]
            }
        """;

        boolean result = Validator.validatePolicy(policy, Type.YAML);
        assertThat(result).isTrue();
    }

    @Test
    public void ODRLExample2_JSON() throws Exception {
        //Arrange
        String policy = """
            {
              "@context": "http://www.w3.org/ns/odrl.jsonld",
              "@type": "Offer",
              "uid": "http://example.com/policy:1011",
              "profile": "http://example.com/odrl:profile:01",
              "permission": [{
                "target": "http://example.com/asset:9898.movie",
                "assigner": "http://example.com/party:org:abc",
                "action": "play"
              }]
            }
        """;

        boolean result = Validator.validatePolicy(policy, Type.YAML);
        assertThat(result).isTrue();
    }

    @Test
    public void ODRLExample3_JSON() throws Exception {
        //Arrange
        String policy = """
            {
              "@context": "http://www.w3.org/ns/odrl.jsonld",
              "@type": "Agreement",
              "uid": "http://example.com/policy:1012",
              "profile": "http://example.com/odrl:profile:01",
              "permission": [{
                "target": "http://example.com/asset:9898.movie",
                "assigner": "http://example.com/party:org:abc",
                "assignee": "http://example.com/party:person:billie",
                "action": "play"
              }]
            }
        """;

        boolean result = Validator.validatePolicy(policy, Type.YAML);
        assertThat(result).isTrue();
    }

    @Test
    public void ODRLExample4_JSON() throws Exception {
        //Arrange
        String policy = """
            {
              "@context": "http://www.w3.org/ns/odrl.jsonld",
              "@type": "Offer",
              "uid": "http://example.com/policy:3333",
              "profile": "http://example.com/odrl:profile:02",
              "permission": [{
                "target": "http://example.com/asset:3333",
                "action": "display",
                "assigner": "http://example.com/party:0001"
              }]
            }
        """;

        boolean result = Validator.validatePolicy(policy, Type.YAML);
        assertThat(result).isTrue();
    }

    @Test
    public void ODRLExample5_JSON() throws Exception {
        //Arrange
        String policy = """
            {
              "@context": "http://www.w3.org/ns/odrl.jsonld",
              "@type": "Policy",
              "uid": "http://example.com/policy:1011",
              "profile": "http://example.com/odrl:profile:03",
              "permission": [{
                "target": {
                  "@type": "AssetCollection",
                  "uid":  "http://example.com/archive1011" },
                "action": "index",
                "summary": "http://example.com/x/database"
              }]
            }
        """;

        boolean result = Validator.validatePolicy(policy, Type.YAML);
        assertThat(result).isTrue();
    }

    @Test
    public void ODRLExample8_JSON() throws Exception { // 6 and 7 are not policy examples
        //Arrange
        String policy = """
            {
              "@context": "http://www.w3.org/ns/odrl.jsonld",
              "@type": "Agreement",
              "uid": "http://example.com/policy:8888",
              "profile": "http://example.com/odrl:profile:04",
              "permission": [{
                "target": "http://example.com/music/1999.mp3",
                "assigner": "http://example.com/org/sony-music",
                "assignee": "http://example.com/people/billie",
                "action": "play"
              }]
            }
        """;

        boolean result = Validator.validatePolicy(policy, Type.YAML);
        assertThat(result).isTrue();
    }

    @Test
    public void ValidAgreement_YAML() throws Exception {
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

        boolean result = Validator.validatePolicy(policy, Type.YAML);
        assertThat(result).isTrue();
    }

    @Test
    public void InvalidType_YAML() throws Exception {
        //Arrange
        String policy = """
            "@context": "http://www.w3.org/ns/odrl.jsonld"
            "@type": "License"
            uid: "http://example.com/policy:abc"
            permission:
              - action: [ "play" ]
                target: "http://example.com/asset:abcd"
        """;

        boolean result = Validator.validatePolicy(policy, Type.YAML);
        assertThat(result).isFalse();
    }

    @Test
    public void PolicyWithProhibition_JSON() throws Exception {
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
        List<String> result = validator.validate(policy, Type.JSON);
        assertThat(result).isTrue();
    }

    @Test
    public void PolicyWithConstraint_YAML() throws Exception {
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
        List<String> result = validator.validate(policy, Type.JSON);
        assertThat(result).isTrue();
    }
    */

    @Test
    public void PolicyWithDateTime_JSON() throws Exception {
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
        assertThat(result).isEmpty();
    }
}
