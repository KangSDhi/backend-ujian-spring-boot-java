package dev.kangsdhi.backendujianspringbootjava.controllers.health;

import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseWithMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/health")
public class HealthController {

    @GetMapping("/ping")
    public ResponseEntity<ResponseWithMessage> ping() {
        ResponseWithMessage responsePing = new ResponseWithMessage();
        responsePing.setHttpCode(HttpStatus.OK.value());
        responsePing.setMessage("Pong From Health");
        return ResponseEntity.ok(responsePing);
    }
}
