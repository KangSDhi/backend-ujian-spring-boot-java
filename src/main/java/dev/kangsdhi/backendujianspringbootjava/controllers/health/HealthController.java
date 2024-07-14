package dev.kangsdhi.backendujianspringbootjava.controllers.health;

import dev.kangsdhi.backendujianspringbootjava.dto.HealthPingResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/health")
public class HealthController {

    @GetMapping("/ping")
    public ResponseEntity<HealthPingResponse> ping() {
        HealthPingResponse response = new HealthPingResponse();
        response.setHttpCode(HttpStatus.OK.value());
        response.setMessage("Pong From Health");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
