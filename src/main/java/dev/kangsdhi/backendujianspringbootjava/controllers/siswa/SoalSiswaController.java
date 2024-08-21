package dev.kangsdhi.backendujianspringbootjava.controllers.siswa;

import dev.kangsdhi.backendujianspringbootjava.dto.data.SoalDto;
import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseWithMessageAndData;
import dev.kangsdhi.backendujianspringbootjava.services.SoalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/siswa")
@RequiredArgsConstructor
public class SoalSiswaController {

    private final SoalService soalService;

    @GetMapping("/soal/findit")
    public ResponseEntity<ResponseWithMessageAndData<SoalDto>> finditSoalById(@RequestParam String idSoal) {
        ResponseWithMessageAndData<SoalDto> soalResponse = soalService.soalById(idSoal);
        HttpStatus httpStatus = HttpStatus.valueOf(soalResponse.getHttpCode());
        return new ResponseEntity<>(soalResponse, httpStatus);
    }
}
