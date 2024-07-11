package dev.kangsdhi.backendujianspringbootjava.services;

import dev.kangsdhi.backendujianspringbootjava.dto.SignInRequest;
import dev.kangsdhi.backendujianspringbootjava.dto.SignInResponse;

public interface AuthenticationService {
    SignInResponse signIn(SignInRequest signInRequest);
}
