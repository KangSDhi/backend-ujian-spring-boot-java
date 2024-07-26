package dev.kangsdhi.backendujianspringbootjava.services;

import dev.kangsdhi.backendujianspringbootjava.dto.request.SignInRequest;
import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseWithMessage;
import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseWithMessageAndData;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface AuthenticationService {
    ResponseWithMessageAndData<Map<String, String>> signIn(SignInRequest signInRequest);
    ResponseWithMessage signOut();
    UserDetails getCurrentUser();
}
