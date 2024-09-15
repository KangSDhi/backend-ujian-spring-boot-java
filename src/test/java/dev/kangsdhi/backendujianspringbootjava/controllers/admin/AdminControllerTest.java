package dev.kangsdhi.backendujianspringbootjava.controllers.admin;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.File;
import java.io.FileReader;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
//@WebMvcTest(controllers = AdminController.class)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String ENDPOINT_ADMIN_PING = "/api/admin/ping";

    @Test
    @Order(1)
    void testAdminPingEndpointWithJWTShouldReturnOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_ADMIN_PING)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer " + readJWTAdminFromLocalStorage()))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Order(2)
    void testAdminPingEndpointWithEmptyJWTShouldReturnUnauthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_ADMIN_PING)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    private String readJWTAdminFromLocalStorage() throws Exception {
        FileReader fileReader = new FileReader(new File("src/main/resources/data/json/auth_jwt.json"));
        Map<String, String> jsonMap = objectMapper.readValue(fileReader, new TypeReference<Map<String, String>>() {
        });
        return jsonMap.get("jwt_admin");
    }


}