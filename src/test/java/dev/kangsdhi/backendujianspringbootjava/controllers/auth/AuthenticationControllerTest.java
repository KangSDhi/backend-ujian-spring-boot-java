package dev.kangsdhi.backendujianspringbootjava.controllers.auth;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.kangsdhi.backendujianspringbootjava.dto.request.SignInRequest;
import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseError;
import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseWithMessageAndData;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

    private static final StringBuilder JWT_ADMIN = new StringBuilder();

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
        String responseBody = result.getResponse().getContentAsString();
        ResponseWithMessageAndData<Map<String, String>> signInResponse = objectMapper.readValue(
                responseBody,
                new TypeReference<ResponseWithMessageAndData<Map<String, String>>>() {
                });
        JWT_ADMIN.append(signInResponse.getData().get("token"));

        this.writeLocalStorage();
    }

    @Test
    @Order(2)
    void testLoginAdminBodyNoneShouldReturnBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @Order(3)
    void testLoginAdminEmailNullShouldReturnBadRequest() throws Exception {
        SignInRequest signInRequest = new SignInRequest();
        signInRequest.setEmailOrIdSiswa(null);
        String requestBody = objectMapper.writeValueAsString(signInRequest);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andReturn();
        String responseBody = result.getResponse().getContentAsString();
        ResponseError<Map<String, String>> responseError = objectMapper.readValue(responseBody, new TypeReference<ResponseError<Map<String, String>>>() {
        });

        String actualEmailError = responseError.getErrors().get("emailOrIdSiswa");
        String actualPasswordError = responseError.getErrors().get("password");
        String expectedEmailError = "Email atau ID Siswa Kosong!";
        String expectedPasswordError = "Password Kosong!";
        assertEquals(expectedEmailError, actualEmailError);
        assertEquals(expectedPasswordError, actualPasswordError);
    }

    @Test
    @Order(4)
    void testLoginAdminPasswordNullShouldReturnBadRequest() throws Exception {
        SignInRequest signInRequest = new SignInRequest();
        signInRequest.setEmailOrIdSiswa(null);
        String requestBody = objectMapper.writeValueAsString(signInRequest);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andReturn();
        String responseBody = result.getResponse().getContentAsString();
        ResponseError<Map<String, String>> responseError = objectMapper.readValue(responseBody, new TypeReference<ResponseError<Map<String, String>>>() {
        });

        String actualEmailError = responseError.getErrors().get("emailOrIdSiswa");
        String actualPasswordError = responseError.getErrors().get("password");
        String expectedEmailError = "Email atau ID Siswa Kosong!";
        String expectedPasswordError = "Password Kosong!";
        assertEquals(expectedEmailError, actualEmailError);
        assertEquals(expectedPasswordError, actualPasswordError);
    }

    @Test
    @Order(5)
    void testLoginAdminBodyEmailAndPasswordNullShouldReturnBadRequest() throws Exception {
        SignInRequest signInRequest = new SignInRequest();
        signInRequest.setEmailOrIdSiswa(null);
        signInRequest.setPassword(null);
        String requestBody = objectMapper.writeValueAsString(signInRequest);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andReturn();
        String responseBody = result.getResponse().getContentAsString();
        ResponseError<Map<String, String>> responseError = objectMapper.readValue(responseBody, new TypeReference<ResponseError<Map<String, String>>>() {
        });

        String actualEmailError = responseError.getErrors().get("emailOrIdSiswa");
        String actualPasswordError = responseError.getErrors().get("password");
        String expectedEmailError = "Email atau ID Siswa Kosong!";
        String expectedPasswordError = "Password Kosong!";
        assertEquals(expectedEmailError, actualEmailError);
        assertEquals(expectedPasswordError, actualPasswordError);
    }

    @Test
    @Order(6)
    void testLoginAdminBodyEmailAndPasswordEmptyShouldReturnBadRequest() throws Exception {
        SignInRequest signInRequest = new SignInRequest();
        signInRequest.setEmailOrIdSiswa("");
        signInRequest.setPassword("");
        String requestBody = objectMapper.writeValueAsString(signInRequest);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andReturn();
        String responseBody = result.getResponse().getContentAsString();
        ResponseError<Map<String, String>> responseError = objectMapper.readValue(responseBody, new TypeReference<ResponseError<Map<String, String>>>() {
        });

        String actualEmailError = responseError.getErrors().get("emailOrIdSiswa");
        String actualPasswordError = responseError.getErrors().get("password");
        String expectedEmailError = "Email atau ID Siswa Kosong!";
        String expectedPasswordError = "Password Kosong!";
        assertEquals(expectedEmailError, actualEmailError);
        assertEquals(expectedPasswordError, actualPasswordError);
    }

    private void writeLocalStorage() throws IOException, JSONException {
        File currentDir = new File("");
        String helper = currentDir.getAbsolutePath();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("jwt_admin", JWT_ADMIN.toString());

        JSONArray jsonArray = new JSONArray();
        jsonArray.put(jsonObject);

        FileWriter fileWriter = new FileWriter(helper + "/src/main/resources/data/json/auth_jwt.json");
        fileWriter.write(jsonArray.toString(4));

        fileWriter.flush();
        fileWriter.close();
    }
}