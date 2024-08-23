package dev.kangsdhi.backendujianspringbootjava.controllers.auth;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.kangsdhi.backendujianspringbootjava.dto.request.SignInRequest;
import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseError;
import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseWithMessageAndData;
import org.json.JSONException;
import org.json.JSONObject;
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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
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
    private static final String ENDPOINT_GURU_PING = "/api/guru/ping";
    private static final String ENDPOINT_SISWA_PING = "/api/siswa/ping";
    private static final String EXPECTED_EMAIL_ERROR = "Email atau ID Siswa Kosong!";
    private static final String EXPECTED_PASSWORD_ERROR = "Password Kosong!";
    private static final String EXPECTED_USER_NOT_FOUND = "Pengguna Tidak Ditemukan!";
    private static final String EXPECTED_STUDENT_NOT_FOUND = "Siswa Tidak Ditemukan!";
    private static final String EMAIL_ADMIN = "kangadmin@gmail.com";
    private static final String PASSWORD_ADMIN = "qwerty";
    private static final String EMAIL_GURU = "kangguru@gmail.com";
    private static final String PASSWORD_GURU = "123456";
    private static final String ID_SISWA = "X-TKJ-1-2343";
    private static final String PASSWORD_SISWA = "qwerty";

    private static final StringBuilder JWT_ADMIN = new StringBuilder();
    private static final StringBuilder JWT_GURU = new StringBuilder();
    private static final StringBuilder JWT_SISWA = new StringBuilder();

    private static final String JWT_INVALID_FORMAT = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJLYW5nIEFkbWluIiwiaWF0IjoxNzIwNjEzN2OTk5OTJ9.KGykxpia76MOoqp_g7H0Nph4wJDurMiA";
    private static final String JWT_EXPIRED = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJLYW5nIEFkbWluIiwiaWF0IjoxNzIwNjEzNTkyLCJleHAiOjE3MjA2OTk5OTJ9.KGykxpiaoVTDY9oql-776MOoqp_g7H0Nph4wJDurMiA";


    @Test
    @Order(1)
    void testLoginBodyNoneShouldReturnBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT_SIGNIN)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @Order(2)
    void testLoginEmailNullShouldReturnBadRequest() throws Exception {
        SignInRequest signInRequest = new SignInRequest();
        signInRequest.setEmailOrIdSiswa(null);
        assertBadRequest(signInRequest, EXPECTED_EMAIL_ERROR, EXPECTED_PASSWORD_ERROR);
    }

    @Test
    @Order(3)
    void testLoginPasswordNullShouldReturnBadRequest() throws Exception {
        SignInRequest signInRequest = new SignInRequest();
        signInRequest.setPassword(null);
        assertBadRequest(signInRequest, EXPECTED_EMAIL_ERROR, EXPECTED_PASSWORD_ERROR);
    }

    @Test
    @Order(4)
    void testLoginBodyEmailAndPasswordNullShouldReturnBadRequest() throws Exception {
        SignInRequest signInRequest = createSignInRequest(null, null);
        assertBadRequest(signInRequest, EXPECTED_EMAIL_ERROR, EXPECTED_PASSWORD_ERROR);
    }

    @Test
    @Order(5)
    void testLoginBodyEmailNotNullAndPasswordNullShouldReturnBadRequest() throws Exception {
        SignInRequest signInRequest = createSignInRequest(EMAIL_ADMIN, null);
        assertBadRequest(signInRequest, null, EXPECTED_PASSWORD_ERROR);
    }

    @Test
    @Order(6)
    void testLoginBodyEmailNullAndPasswordNotNullShouldReturnBadRequest() throws Exception {
        SignInRequest signInRequest = createSignInRequest(null, PASSWORD_ADMIN);
        assertBadRequest(signInRequest, EXPECTED_EMAIL_ERROR, null);
    }

    @Test
    @Order(7)
    void testLoginBodyEmailEmptyShouldReturnBadRequest() throws Exception {
        SignInRequest signInRequest = new SignInRequest();
        signInRequest.setEmailOrIdSiswa("");
        assertBadRequest(signInRequest, EXPECTED_EMAIL_ERROR, EXPECTED_PASSWORD_ERROR);
    }

    @Test
    @Order(8)
    void testLoginBodyPasswordEmptyShouldReturnBadRequest() throws Exception {
        SignInRequest signInRequest = new SignInRequest();
        signInRequest.setPassword("");
        assertBadRequest(signInRequest, EXPECTED_EMAIL_ERROR, EXPECTED_PASSWORD_ERROR);
    }

    @Test
    @Order(9)
    void testLoginBodyEmailNotEmptyAndPasswordEmptyShouldReturnBadRequest() throws Exception {
        SignInRequest signInRequest = createSignInRequest(EMAIL_ADMIN, "");
        assertBadRequest(signInRequest, null, EXPECTED_PASSWORD_ERROR);
    }

    @Test
    @Order(10)
    void testLoginBodyEmailEmptyAndPasswordNotEmptyShouldReturnBadRequest() throws Exception {
        SignInRequest signInRequest = createSignInRequest("", PASSWORD_ADMIN);
        assertBadRequest(signInRequest, EXPECTED_EMAIL_ERROR, null);
    }

    @Test
    @Order(11)
    void testLoginBodyEmailAndPasswordEmptyShouldReturnBadRequest() throws Exception {
        SignInRequest signInRequest = createSignInRequest("", "");
        assertBadRequest(signInRequest, EXPECTED_EMAIL_ERROR, EXPECTED_PASSWORD_ERROR);
    }

    @Test
    @Order(12)
    void testLoginAdminWithWrongEmailShouldReturnNotFound() throws Exception {
        SignInRequest signInRequest = createSignInRequest("ekoadmin@gmail.com", PASSWORD_ADMIN);
        assertNotFound(signInRequest, EXPECTED_USER_NOT_FOUND);
    }

    @Test
    @Order(13)
    void testLoginAdminWithWrongPasswordShouldReturnNotFound() throws Exception {
        SignInRequest signInRequest = createSignInRequest(EMAIL_ADMIN, "akuh");
        assertNotFound(signInRequest, EXPECTED_USER_NOT_FOUND);
    }

    @Test
    @Order(14)
    void testLoginAdminWithWrongEmailAndPasswordShouldReturnNotFound() throws Exception {
        SignInRequest signInRequest = createSignInRequest("ekoadmin@gmail.com", "haha");
        assertNotFound(signInRequest, EXPECTED_USER_NOT_FOUND);
    }

    @Test
    @Order(15)
    void testLoginAdminShouldReturnOk() throws Exception {
        SignInRequest signInRequest = createSignInRequest(EMAIL_ADMIN, PASSWORD_ADMIN);

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
    @Order(16)
    void testSessionLoginAdminShouldReturnOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_ADMIN_PING)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer " + JWT_ADMIN.toString()))
                .andExpect(status().isOk())
                .andDo(print());
    }


    @Test
    @Order(17)
    void testSessionLoginAdminToStudentEndpointShouldReturnForbidden() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_SISWA_PING)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer " + JWT_ADMIN.toString()))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @Test()
    @Order(18)
    void testSessionLoginAdminToTeacherEndpointShouldReturnForbidden() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_GURU_PING)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer " + JWT_ADMIN.toString()))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @Test
    @Order(19)
    void testLoginTeacherWithWrongEmailShouldReturnNotFound() throws Exception {
        SignInRequest signInRequest = createSignInRequest("kingguru@gmail.com", PASSWORD_GURU);
        assertNotFound(signInRequest, EXPECTED_USER_NOT_FOUND);
    }

    @Test
    @Order(20)
    void testLoginTeacherWithWrongPasswordShouldReturnNotFound() throws Exception {
        SignInRequest signInRequest = createSignInRequest(EMAIL_GURU, "12we");
        assertNotFound(signInRequest, EXPECTED_USER_NOT_FOUND);
    }

    @Test
    @Order(21)
    void testLoginTeacherWithWrongEmailAndPasswordShouldReturnNotFound() throws Exception {
        SignInRequest signInRequest = createSignInRequest("kingguru@gmail.com", "12we");
        assertNotFound(signInRequest, EXPECTED_USER_NOT_FOUND);
    }

    @Test
    @Order(22)
    void testLoginTeacherShouldReturnOk() throws Exception {
        SignInRequest signInRequest = createSignInRequest(EMAIL_GURU, PASSWORD_GURU);

        String requestBody = objectMapper.writeValueAsString(signInRequest);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT_SIGNIN)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        ResponseWithMessageAndData<Map<String, String>> signInResponse = objectMapper.readValue(responseBody, new TypeReference<ResponseWithMessageAndData<Map<String, String>>>() {
        });
        JWT_GURU.append(signInResponse.getData().get("token"));
        this.writeLocalStorage();
    }

    @Test
    @Order(23)
    void testSessionLoginTeacherShouldReturnOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_GURU_PING)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer " + JWT_GURU.toString()))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Order(24)
    void testSessionLoginTeacherToAdminEndpointShouldReturnForbidden() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_ADMIN_PING)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer " + JWT_GURU.toString()))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @Test
    @Order(25)
    void testSessionLoginTeacherToStudentEndpointShouldReturnForbidden() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_SISWA_PING)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer " + JWT_GURU.toString()))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @Test
    @Order(26)
    void testLoginStudentWithWrongEmailShouldReturnNotFound() throws Exception {
        SignInRequest signInRequest = createSignInRequest("X-THR-1-2333", PASSWORD_SISWA);
        assertNotFound(signInRequest, EXPECTED_STUDENT_NOT_FOUND);
    }

    @Test
    @Order(27)
    void testLoginStudentWithWrongPasswordShouldReturnNotFound() throws Exception {
        SignInRequest signInRequest = createSignInRequest(ID_SISWA, "3554te");
        assertNotFound(signInRequest, EXPECTED_STUDENT_NOT_FOUND);
    }

    @Test
    @Order(28)
    void testLoginStudentWithWrongEmailAndPasswordShouldReturnNotFound() throws Exception {
        SignInRequest signInRequest = createSignInRequest("X-THR-1-2333", "3554te");
        assertNotFound(signInRequest, EXPECTED_STUDENT_NOT_FOUND);
    }

    @Test
    @Order(29)
    void testLoginStudentShouldReturnOk() throws Exception {
        SignInRequest signInRequest = createSignInRequest(ID_SISWA, PASSWORD_SISWA);
        String requestBody = objectMapper.writeValueAsString(signInRequest);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT_SIGNIN)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();
        ResponseWithMessageAndData<Map<String, String>> signInResponse = objectMapper.readValue(responseBody, new TypeReference<ResponseWithMessageAndData<Map<String, String>>>() {
        });
        JWT_SISWA.append(signInResponse.getData().get("token"));
        this.writeLocalStorage();
    }

    @Test
    @Order(30)
    void testSessionLoginStudentShouldReturnOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_SISWA_PING)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer " + JWT_SISWA.toString()))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Order(31)
    void testSessionLoginStudentToAdminEndpointShouldReturnForbidden() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_ADMIN_PING)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer " + JWT_SISWA.toString()))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @Test
    @Order(32)
    void testSessionLoginStudentToTeacherEndpointShouldReturnForbidden() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_GURU_PING)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header("Authorization", "Bearer " + JWT_SISWA.toString()))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @Test
    @Order(33)
    void testJWTInvalidShouldReturnUnauthorized() throws Exception {
        Exception exception = assertThrows(JWTDecodeException.class, () -> {
            mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_ADMIN_PING)
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .header("Authorization", "Bearer " + JWT_INVALID_FORMAT))
                    .andExpect(status().isUnauthorized())
                    .andDo(print());
        });

        assertTrue(exception.getMessage().contains("doesn't have a valid JSON format"));
    }

    @Test
    @Order(34)
    void testJWTExpiredShouldReturnUnauthorized() throws Exception {
        Exception exception = assertThrows(TokenExpiredException.class, () -> {
            mockMvc.perform(MockMvcRequestBuilders.get(ENDPOINT_ADMIN_PING)
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .header("Authorization", "Bearer " + JWT_EXPIRED))
                    .andExpect(status().isUnauthorized())
                    .andDo(print());
        });

        assertTrue(exception.getMessage().contains("The Token has expired"));
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

    private void assertNotFound(SignInRequest signInRequest, String expectedError) throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(ENDPOINT_SIGNIN)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(signInRequest)))
                .andExpect(status().isNotFound())
                .andDo(print())
                .andReturn();
        String responseBody = result.getResponse().getContentAsString();
        ResponseError<String> responseError = objectMapper.readValue(responseBody, new TypeReference<ResponseError<String>>() {
        });
        assertEquals(expectedError, responseError.getErrors());
    }

    private void writeLocalStorage() throws IOException, JSONException {
        FileWriter fileWriter = new FileWriter(new File("src/main/resources/data/json/auth_jwt.json"));

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("jwt_admin", JWT_ADMIN.toString());
        jsonObject.put("jwt_guru", JWT_GURU.toString());
        jsonObject.put("jwt_siswa", JWT_SISWA.toString());

        fileWriter.write(jsonObject.toString(4));
        fileWriter.flush();
        fileWriter.close();
    }
}