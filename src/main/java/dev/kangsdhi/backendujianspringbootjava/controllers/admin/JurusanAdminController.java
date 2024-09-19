package dev.kangsdhi.backendujianspringbootjava.controllers.admin;

import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseWithMessageAndData;
import dev.kangsdhi.backendujianspringbootjava.entities.Jurusan;
import dev.kangsdhi.backendujianspringbootjava.services.JurusanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/jurusan")
@RequiredArgsConstructor
public class JurusanAdminController {

    private final JurusanService jurusanService;

    @GetMapping("")
    public ResponseEntity<ResponseWithMessageAndData<List<Jurusan>>> getAllJurusan() {
        ResponseWithMessageAndData<List<Jurusan>> response = jurusanService.getAllJurusan();
        HttpStatus httpStatus = HttpStatus.valueOf(response.getHttpCode());
        return new ResponseEntity<>(response, httpStatus);
    }
}
