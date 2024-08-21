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

    private static final String ENDPOINT_SIGNIN = "/api/auth/signin";
    private static final String ENDPOINT_ADMIN_PING = "/api/admin/ping";
    private static final String ENDPOINT_SISWA_PING = "/api/siswa/ping";
    private static final String EXPECTED_EMAIL_ERROR = "Email atau ID Siswa Kosong!";
    private static final String EXPECTED_PASSWORD_ERROR = "Password Kosong!";
    private static final String EMAIL_ADMIN = "kangadmin@gmail.com";
    private static final String PASSWORD_ADMIN = "qwerty";

    private static final StringBuilder JWT_ADMIN = new StringBuilder();

    private static final String JWT_INVALID_FORMAT = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJLYW5nIEFkbWluIiwiaWF0IjoxNzIwNjEzN2OTk5OTJ9.KGykxpia76MOoqp_g7H0Nph4wJDurMiA";
    private static final String JWT_EXPIRED = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJLYW5nIEFkbWluIiwiaWF0IjoxNzIwNjEzNTkyLCJleHAiOjE3MjA2OTk5OTJ9.KGykxpiaoVTDY9oql-776MOoqp_g7H0Nph4wJDurMiA";


    @Test
    @Order(1)
    void testLoginAdminBodyNoneShouldReturnBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT_SIGNIN)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @Order(2)
    void testLoginAdminEmailNullShouldReturnBadRequest() throws Exception {
        SignInRequest signInRequest = new SignInRequest();
        signInRequest.setEmailOrIdSiswa(null);
        assertBadRequest(signInRequest, EXPECTED_EMAIL_ERROR, EXPECTED_PASSWORD_ERROR);
    }

    @Test
    @Order(3)
    void testLoginAdminPasswordNullShouldReturnBadRequest() throws Exception {
        SignInRequest signInRequest = new SignInRequest();
        signInRequest.setPassword(null);
        assertBadRequest(signInRequest, EXPECTED_EMAIL_ERROR, EXPECTED_PASSWORD_ERROR);
    }

    @Test
    @Order(4)
    void testLoginAdminBodyEmailAndPasswordNullShouldReturnBadRequest() throws Exception {
        SignInRequest signInRequest = createSignInRequest(null, null);
        assertBadRequest(signInRequest, EXPECTED_EMAIL_ERROR, EXPECTED_PASSWORD_ERROR);
    }

    @Test
    @Order(5)
    void testLoginAdminBodyEmailNotNullAndPasswordNullShouldReturnBadRequest() throws Exception {
        SignInRequest signInRequest = createSignInRequest(EMAIL_ADMIN, null);
        assertBadRequest(signInRequest, null, EXPECTED_PASSWORD_ERROR);
    }

    @Test
    @Order(6)
    void testLoginAdminBodyEmailNullAndPasswordNotNullShouldReturnBadRequest() throws Exception {
        SignInRequest signInRequest = createSignInRequest(null, PASSWORD_ADMIN);
        assertBadRequest(signInRequest, EXPECTED_EMAIL_ERROR, null);
    }

    @Test
    @Order(7)
    void testLoginAdminBodyEmailEmptyShouldReturnBadRequest() throws Exception {
        SignInRequest signInRequest = new SignInRequest();
        signInRequest.setEmailOrIdSiswa("");
        assertBadRequest(signInRequest, EXPECTED_EMAIL_ERROR, EXPECTED_PASSWORD_ERROR);
    }

    @Test
    @Order(8)
    void testLoginAdminBodyPasswordEmptyShouldReturnBadRequest() throws Exception {
        SignInRequest signInRequest = new SignInRequest();
        signInRequest.setPassword("");
        assertBadRequest(signInRequest, EXPECTED_EMAIL_ERROR, EXPECTED_PASSWORD_ERROR);
    }

    @Test
    @Order(9)
    void testLoginAdminBodyEmailNotEmptyAndPasswordEmptyShouldReturnBadRequest() throws Exception {
        SignInRequest signInRequest = createSignInRequest(EMAIL_ADMIN, "");
        assertBadRequest(signInRequest, null, EXPECTED_PASSWORD_ERROR);
    }

    @Test
    @Order(10)
    void testLoginAdminBodyEmailEmptyAndPasswordNotEmptyShouldReturnBadRequest() throws Exception {
        SignInRequest signInRequest = createSignInRequest("", PASSWORD_ADMIN);
        assertBadRequest(signInRequest, EXPECTED_EMAIL_ERROR, null);
    }

    @Test
    @Order(11)
    void testLoginAdminBodyEmailAndPasswordEmptyShouldReturnBadRequest() throws Exception {
        SignInRequest signInRequest = createSignInRequest("", "");
        assertBadRequest(signInRequest, EXPECTED_EMAIL_ERROR, EXPECTED_PASSWORD_ERROR);
    }

    @Test
    @Order(12)
    void testLoginAdminShouldReturnOk() throws Exception {
        SignInRequest signInRequest = new SignInRequest();
        signInRequest.setEmailOrIdSiswa(EMAIL_ADMIN);
        signInRequest.setPassword(PASSWORD_ADMIN);

        String requestBody = objectMapper.writeValueAsString(signInRequest);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT_SIGNIN)
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
    @Order(13)
    void testSessionLoginAdminShouldReturnOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_ADMIN_PING)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer " + JWT_ADMIN.toString()))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Order(14)
    void testSessionLoginAdminToWrongEndpointShouldReturnForbidden() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_SISWA_PING)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer " + JWT_ADMIN.toString()))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    private SignInRequest createSignInRequest(String emailOrIdSiswa, String password) {
        SignInRequest signInRequest = new SignInRequest();
        signInRequest.setEmailOrIdSiswa(emailOrIdSiswa);
        signInRequest.setPassword(password);
        return signInRequest;
    }

    private void assertBadRequest(SignInRequest signInRequest, String expectedEmailError, String expectedPasswordError) throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT_SIGNIN)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(signInRequest)))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        ResponseError<Map<String, String>> responseError = objectMapper.readValue(responseBody, new TypeReference<ResponseError<Map<String, String>>>() {
        });

        assertEquals(expectedEmailError, responseError.getErrors().get("emailOrIdSiswa"));
        assertEquals(expectedPasswordError, responseError.getErrors().get("password"));
    }

    private void writeLocalStorage() throws IOException, JSONException {
        FileWriter fileWriter = new FileWriter(new File("src/main/resources/data/json/auth_jwt.json"));

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("jwt_admin", JWT_ADMIN.toString());

        JSONArray jsonArray = new JSONArray();
        jsonArray.put(jsonObject);

        fileWriter.write(jsonArray.toString(4));
        fileWriter.flush();
        fileWriter.close();
    }
}