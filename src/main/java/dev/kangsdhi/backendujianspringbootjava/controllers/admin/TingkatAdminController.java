package dev.kangsdhi.backendujianspringbootjava.controllers.admin;

import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseWithMessageAndData;
import dev.kangsdhi.backendujianspringbootjava.entities.Tingkat;
import dev.kangsdhi.backendujianspringbootjava.services.TingkatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/tingkat")
@RequiredArgsConstructor
public class TingkatAdminController {

    private final TingkatService tingkatService;

    @GetMapping("")
    public ResponseEntity<ResponseWithMessageAndData<List<Tingkat>>> getAllTingkat() {
        ResponseWithMessageAndData<List<Tingkat>> response = tingkatService.getAllTingkat();
        HttpStatus httpStatus = HttpStatus.valueOf(response.getHttpCode());
        return new ResponseEntity<>(response, httpStatus);
    }
}
