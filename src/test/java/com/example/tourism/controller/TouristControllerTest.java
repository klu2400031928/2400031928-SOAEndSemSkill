package com.example.tourism.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TouristControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String VALID_TOKEN = "tourism-analytics-secret-token-2026";
    private static final String INVALID_TOKEN = "invalid-secret-key-9999";

    @Test
    @DisplayName("Scenario 1: Missing Token -> HTTP 401 Unauthorized")
    void testGetAllTourists_MissingToken_ReturnsUnauthorized() throws Exception {
        mockMvc.perform(get("/api/tourists")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.status", is(401)))
                .andExpect(jsonPath("$.error", is("Unauthorized")))
                .andExpect(jsonPath("$.message", is("Missing or invalid Authorization token. Access denied.")))
                .andExpect(jsonPath("$.path", is("/api/tourists")));
    }

    @Test
    @DisplayName("Scenario 2: Invalid Token -> HTTP 401 Unauthorized")
    void testGetAllTourists_InvalidToken_ReturnsUnauthorized() throws Exception {
        mockMvc.perform(get("/api/tourists")
                        .header("Authorization", INVALID_TOKEN)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.status", is(401)))
                .andExpect(jsonPath("$.error", is("Unauthorized")))
                .andExpect(jsonPath("$.message", is("Missing or invalid Authorization token. Access denied.")));
    }

    @Test
    @DisplayName("Scenario 3: Valid Direct Token -> HTTP 200 OK and retrieve all tourist records")
    void testGetAllTourists_ValidToken_ReturnsOkAndTourists() throws Exception {
        mockMvc.perform(get("/api/tourists")
                        .header("Authorization", VALID_TOKEN)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(5))))
                .andExpect(jsonPath("$[0].name", is("Alice Johnson")))
                .andExpect(jsonPath("$[0].destination", is("Bali, Indonesia")));
    }

    @Test
    @DisplayName("Scenario 4: Valid Bearer Prefixed Token -> HTTP 200 OK and retrieve all tourist records")
    void testGetAllTourists_ValidBearerToken_ReturnsOkAndTourists() throws Exception {
        mockMvc.perform(get("/api/tourists")
                        .header("Authorization", "Bearer " + VALID_TOKEN)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(5))));
    }
}

