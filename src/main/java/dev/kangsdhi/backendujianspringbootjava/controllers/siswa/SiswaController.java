package dev.kangsdhi.backendujianspringbootjava.controllers.siswa;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/siswa")
public class SiswaController {

    @GetMapping("/ping")
    public String ping() {
        return "pong siswa";
    }
}
