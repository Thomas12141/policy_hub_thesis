package org.example.policy_hub.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers(disabledWithoutDocker = true)
@Transactional
class JarControllerIT {

    @Container
    @ServiceConnection
    private static final PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>("postgres:latest");

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllJars() throws Exception {
        //Arrange
        String url = "/api/jars";

        //Act
        ResultActions resultActions = mockMvc.perform(get(url));

        //Assert
        resultActions.andExpect(status().isOk());
    }

    @Test
    void getJar() throws Exception {
        //Arrange
        String url = "/api/jars/download/AccessPolicy";

        //Act
        ResultActions resultActions = mockMvc.perform(get(url));

        //Assert
        resultActions.andExpect(status().isOk());
    }
}
