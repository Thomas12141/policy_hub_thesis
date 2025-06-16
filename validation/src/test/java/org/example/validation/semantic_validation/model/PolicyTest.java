package org.example.validation.semantic_validation.model;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

class PolicyTest {

    @Test
    public void testToString() throws JSONException {
        //Arrange
        String policyString = """
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
        Policy policyObject = Policy.ofJSON(policyString);
        JSONObject jsonObject = new JSONObject(policyObject.toString());

        //Assert
        JSONAssert.assertEquals(policyString, jsonObject, true);
    }
}
