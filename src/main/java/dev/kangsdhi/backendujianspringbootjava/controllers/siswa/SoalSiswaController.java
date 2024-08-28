package dev.kangsdhi.backendujianspringbootjava.controllers.siswa;

import dev.kangsdhi.backendujianspringbootjava.dto.data.SoalDto;
import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseWithMessage;
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
@RequestMapping("/api/siswa/soal")
@RequiredArgsConstructor
public class SoalSiswaController {

    private final SoalService soalService;

    @GetMapping("/findit")
    public ResponseEntity<ResponseWithMessageAndData<SoalDto>> finditSoalById(@RequestParam String idSoal) {
        ResponseWithMessageAndData<SoalDto> soalResponse = soalService.soalById(idSoal);
        HttpStatus httpStatus = HttpStatus.valueOf(soalResponse.getHttpCode());
        return new ResponseEntity<>(soalResponse, httpStatus);
    }

    @GetMapping("/check/token")
    public ResponseEntity<ResponseWithMessage> checkTokenSoal(@RequestParam String token, @RequestParam String idSoal) {
        ResponseWithMessage responseWithMessage = soalService.checkTokenSoal(idSoal, token);
        HttpStatus httpStatus = HttpStatus.valueOf(responseWithMessage.getHttpCode());
        return new ResponseEntity<>(responseWithMessage, httpStatus);
    }
}
