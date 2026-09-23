package com.revworkforce.user_service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revworkforce.user_service.entity.User;
import com.revworkforce.user_service.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserServiceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void cleanDatabase() {
        userRepository.deleteAll();
    }

    @Test
    void registration_shouldCreateUser() throws Exception {

        String request = """
                {
                    "username": "integrationuser",
                    "email": "integration@example.com",
                    "password": "password123",
                    "firstName": "Integration",
                    "lastName": "User",
                    "phone": "9876543210"
                }
                """;

        mockMvc.perform(
                post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request)
        ).andExpect(status().isCreated());
    }

    @Test
    void registration_shouldRejectInvalidEmail() throws Exception {

        String request = """
                {
                    "username": "integrationuser",
                    "email": "invalid-email",
                    "password": "password123",
                    "firstName": "Integration",
                    "lastName": "User"
                }
                """;

        mockMvc.perform(
                post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void login_shouldReturnJwtToken() throws Exception {

        User user = new User();
        user.setUsername("integrationuser");
        user.setEmail("integration@example.com");
        user.setPassword(
                new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder()
                        .encode("password123")
        );
        user.setRole("EMPLOYEE");
        user.setFirstName("Integration");
        user.setLastName("User");
        user.setActive(true);

        userRepository.save(user);

        String loginRequest = """
                {
                    "username": "integrationuser",
                    "password": "password123"
                }
                """;

        String response = mockMvc.perform(
                        post("/api/users/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(loginRequest)
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonNode json = objectMapper.readTree(response);

        assert json.has("token");
        assert !json.get("token").asText().isEmpty();
    }

    @Test
    void protectedProfile_shouldRejectUnauthenticatedRequest()
            throws Exception {

        mockMvc.perform(
                get("/api/users/profile")
        ).andExpect(status().isForbidden());
    }
}