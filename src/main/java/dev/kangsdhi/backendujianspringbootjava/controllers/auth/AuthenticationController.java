package dev.kangsdhi.backendujianspringbootjava.controllers.auth;

import dev.kangsdhi.backendujianspringbootjava.dto.CheckUserAuthResponse;
import dev.kangsdhi.backendujianspringbootjava.dto.PingResponse;
import dev.kangsdhi.backendujianspringbootjava.dto.SignInRequest;
import dev.kangsdhi.backendujianspringbootjava.dto.SignInResponse;
import dev.kangsdhi.backendujianspringbootjava.services.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signin")
    public ResponseEntity<SignInResponse<Object>> signIn(@Valid @RequestBody SignInRequest signInRequest) {
        return ResponseEntity.ok(authenticationService.signIn(signInRequest));
    }

    @GetMapping("/check/user")
    public ResponseEntity<CheckUserAuthResponse<Object>> checkAuth() {
        CheckUserAuthResponse<Object> checkUserAuthResponse = new CheckUserAuthResponse<>();
        checkUserAuthResponse.setHttpCode(HttpStatus.OK.value());
        HashMap<String, String> mapData = new HashMap<>();
        mapData.put("user", authenticationService.getCurrentUser().getUsername());
        mapData.put("level", authenticationService.getCurrentUser().getAuthorities()
                .stream().map(GrantedAuthority::getAuthority).findFirst().orElse(null));
        checkUserAuthResponse.setData(mapData);
        return ResponseEntity.ok(checkUserAuthResponse);
    }
}
