package dev.kangsdhi.backendujianspringbootjava.controllers.guru;

import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseWithMessageAndData;
import dev.kangsdhi.backendujianspringbootjava.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/guru")
public class GuruController {

    @Autowired
    AuthenticationService authenticationService;

    @GetMapping("/ping")
    public ResponseEntity<ResponseWithMessageAndData<Map<String, String >>> ping() {
        ResponseWithMessageAndData<Map<String, String >> pingResponse = new ResponseWithMessageAndData<>();
        pingResponse.setHttpCode(HttpStatus.OK.value());
        pingResponse.setMessage("Pong Guru!");
        HashMap<String, String> mapData = new HashMap<>();
        mapData.put("user", authenticationService.getCurrentUser().getUsername());
        pingResponse.setData(mapData);
        return ResponseEntity.ok(pingResponse);
    }
}
