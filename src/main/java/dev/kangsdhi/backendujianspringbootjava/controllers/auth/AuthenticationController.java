package dev.kangsdhi.backendujianspringbootjava.controllers.auth;

import dev.kangsdhi.backendujianspringbootjava.dto.request.SignInRequest;
import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseWithMessage;
import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseWithMessageAndData;
import dev.kangsdhi.backendujianspringbootjava.services.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signin")
    public ResponseEntity<ResponseWithMessageAndData<Map<String, String>>> signIn(@Valid @RequestBody SignInRequest signInRequest) {
        return ResponseEntity.ok(authenticationService.signIn(signInRequest));
    }

    @GetMapping("/signout")
    public ResponseEntity<ResponseWithMessage> signOut() {
        return ResponseEntity.ok(authenticationService.signOut());
    }

    @GetMapping("/check/user")
    public ResponseEntity<ResponseWithMessageAndData<Map<String, String>>> checkAuth() {
        ResponseWithMessageAndData<Map<String, String>> checkUserAuthResponse = new ResponseWithMessageAndData<>();
        checkUserAuthResponse.setHttpCode(HttpStatus.OK.value());
        checkUserAuthResponse.setMessage("Autentikasi Berhasil");
        HashMap<String, String> mapData = new HashMap<>();
        mapData.put("user", authenticationService.getCurrentUser().getUsername());
        mapData.put("level", authenticationService.getCurrentUser().getAuthorities()
                .stream().map(GrantedAuthority::getAuthority).findFirst().orElse(null));
        checkUserAuthResponse.setData(mapData);
        return ResponseEntity.ok(checkUserAuthResponse);
    }
}
