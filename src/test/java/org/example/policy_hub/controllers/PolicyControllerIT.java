package org.example.policy_hub.controllers;

import org.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers(disabledWithoutDocker = true)
@Transactional
public class PolicyControllerIT {

    @Container
    @ServiceConnection
    private static final PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>("postgres:latest");

    @Autowired
    private MockMvc mockMvc;

    @Test
    void invalidPolicy_Semantic_EmptyRightOperand() throws Exception {
        //Arrange
        String url = "/api/policies";
        String policy = """
            {
              "@context": "http://www.w3.org/ns/odrl.jsonld",
              "@type": "Set",
              "uid": "http://example.com/policy:1234",
              "permission": [{
                "action": "use",
                "target": "http://example.com/resource:1234",
                "constraint": [{
                  "leftOperand": "Membership",
                  "operator": "eq",
                  "rightOperand": ""
                }]
              }]
            }""";

        //Act
        String body = mockMvc.perform(
                post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(policy)).andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();

        //Assert
        assertThat(body).contains("Membership rightOperand must be ACTIVE");
    }

    @Test
    void invalidPolicy_Syntax_NoUid() throws Exception {
        //Arrange
        String url = "/api/policies";
        String policy = """
            {
              "@context": "http://www.w3.org/ns/odrl.jsonld",
              "@type": "Set",
              "permission": [{
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
        String body = mockMvc.perform(
                post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(policy)).andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();

        //Assert
        assertThat(body).contains("uid");
    }

    @Test
    void validPolicy() throws Exception {
        //Arrange
        String url = "/api/policies";
        String policy = """
            {
              "@context": "http://www.w3.org/ns/odrl.jsonld",
              "@type": "Set",
              "uid": "http://example.com/policy:1234",
              "permission": [{
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
        ResultActions resultActionsPost = mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(policy));
        ResultActions resultActionsGet = mockMvc.perform(get("/api/policies?uid=http://example.com/policy:1234"));

        //Assert
        resultActionsPost.andExpect(status().isOk());
        String response = resultActionsGet.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        assertThat(response).isEqualTo(policy);
    }

    @Test
    void validPolicy_TowPolicies_DifferentPolicies() throws Exception {
        //Arrange
        String url = "/api/policies";
        String firstPolicy = """
            {
              "@context": "http://www.w3.org/ns/odrl.jsonld",
              "@type": "Set",
              "uid": "http://example.com/policy:1234",
              "permission": [{
                "action": "use",
                "target": "http://example.com/resource:1234",
                "constraint": [{
                  "leftOperand": "Membership",
                  "operator": "eq",
                  "rightOperand": "ACTIVE"
                }]
              }]
            }""";
        String secondPolicy = """
            {
              "@context": "http://www.w3.org/ns/odrl.jsonld",
              "@type": "Set",
              "uid": "http://example.com/policy:1235",
              "permission": [{
                "action": "use",
                "target": "http://example.com/resource:1235",
                "constraint": [{
                  "leftOperand": "Membership",
                  "operator": "eq",
                  "rightOperand": "ACTIVE"
                }]
              }]
            }""";

        //Act
        ResultActions firstResultActionsPost = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(firstPolicy));
        ResultActions secondResultActionsPost = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(secondPolicy));
        ResultActions getAllPolicies = mockMvc.perform(get("/api/policies/all"));
        JSONArray jsonArray = new JSONArray(getAllPolicies.andReturn().getResponse().getContentAsString());

        //Assert
        firstResultActionsPost.andExpect(status().isOk());
        secondResultActionsPost.andExpect(status().isOk());
        getAllPolicies.andExpect(status().isOk());
        JSONAssert.assertEquals(jsonArray.getJSONObject(0).toString(), firstPolicy, false);
        JSONAssert.assertEquals(jsonArray.getJSONObject(1).toString(), secondPolicy, false);
        assertThat(jsonArray.length()).isEqualTo(2);
    }

    @Test
    void validPolicy_TowPolicies_SamePolicies() throws Exception {
        //Arrange
        String url = "/api/policies";
        String firstPolicy = """
            {
              "@context": "http://www.w3.org/ns/odrl.jsonld",
              "@type": "Set",
              "uid": "http://example.com/policy:1234",
              "permission": [{
                "action": "use",
                "target": "http://example.com/resource:1234",
                "constraint": [{
                  "leftOperand": "Membership",
                  "operator": "eq",
                  "rightOperand": "ACTIVE"
                }]
              }]
            }""";
        String secondPolicy = """
            {
              "@context": "http://www.w3.org/ns/odrl.jsonld",
              "@type": "Set",
              "uid": "http://example.com/policy:1234",
              "permission": [{
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
        ResultActions firstResultActionsPost = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(firstPolicy));
        ResultActions secondResultActionsPost = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(secondPolicy));
        ResultActions getAllPolicies = mockMvc.perform(get("/api/policies/all"));
        JSONArray jsonArray = new JSONArray(getAllPolicies.andReturn().getResponse().getContentAsString());

        //Assert
        firstResultActionsPost.andExpect(status().isOk());
        assertThat(secondResultActionsPost.andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString().
                contains("already exists")).isTrue();
        getAllPolicies.andExpect(status().isOk());
        JSONAssert.assertEquals(jsonArray.getJSONObject(0).toString(), firstPolicy, false);
        assertThat(jsonArray.length()).isEqualTo(1);
    }
}
