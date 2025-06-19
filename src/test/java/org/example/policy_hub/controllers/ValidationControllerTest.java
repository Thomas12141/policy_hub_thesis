package org.example.policy_hub.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
public class ValidationControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @Test
  void invalidPolicy() throws Exception {
    //Arrange
    String url = "/api/validation";
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
}
