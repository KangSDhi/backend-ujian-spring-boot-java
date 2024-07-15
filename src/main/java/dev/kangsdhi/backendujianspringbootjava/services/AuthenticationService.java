package dev.kangsdhi.backendujianspringbootjava.services;

import dev.kangsdhi.backendujianspringbootjava.dto.SignInRequest;
import dev.kangsdhi.backendujianspringbootjava.dto.SignInResponse;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthenticationService {
    SignInResponse signIn(SignInRequest signInRequest);
    UserDetails getCurrentUser();
}
