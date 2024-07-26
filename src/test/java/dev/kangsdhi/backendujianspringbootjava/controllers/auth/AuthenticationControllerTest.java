package dev.kangsdhi.backendujianspringbootjava.controllers.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.kangsdhi.backendujianspringbootjava.dto.request.SignInRequest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String JWT_INVALID_FORMAT = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJLYW5nIEFkbWluIiwiaWF0IjoxNzIwNjEzN2OTk5OTJ9.KGykxpia76MOoqp_g7H0Nph4wJDurMiA";

    private static final String JWT_EXPIRED = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJLYW5nIEFkbWluIiwiaWF0IjoxNzIwNjEzNTkyLCJleHAiOjE3MjA2OTk5OTJ9.KGykxpiaoVTDY9oql-776MOoqp_g7H0Nph4wJDurMiA";

    @Test
    @Order(1)
    void testLoginAdminShouldReturnOk() throws Exception {
        SignInRequest signInRequest = new SignInRequest();
        signInRequest.setEmailOrIdSiswa("kangadmin@gmail.com");
        signInRequest.setPassword("qwerty");

        String requestBody = objectMapper.writeValueAsString(signInRequest);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();


    }
}