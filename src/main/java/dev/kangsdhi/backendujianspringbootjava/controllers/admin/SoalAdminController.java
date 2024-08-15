package dev.kangsdhi.backendujianspringbootjava.controllers.admin;

import dev.kangsdhi.backendujianspringbootjava.dto.data.SoalDto;
import dev.kangsdhi.backendujianspringbootjava.dto.request.SoalRequest;
import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseWithMessage;
import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseWithMessageAndData;
import dev.kangsdhi.backendujianspringbootjava.services.SoalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/soal")
@RequiredArgsConstructor
public class SoalAdminController {

    private final SoalService soalService;

    @GetMapping("")
    public ResponseEntity<ResponseWithMessageAndData<List<SoalDto>>> getAllSoals() {
        ResponseWithMessageAndData<List<SoalDto>> response = soalService.listAllSoal();
        HttpStatus httpStatus = HttpStatus.valueOf(response.getHttpCode());
        return new ResponseEntity<>(response, httpStatus);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseWithMessageAndData<SoalDto>> createSoal(@Valid @RequestBody SoalRequest soalRequest){
        ResponseWithMessageAndData<SoalDto> response = soalService.createSoal(soalRequest);
        HttpStatus httpStatus = HttpStatus.valueOf(response.getHttpCode());
        return new ResponseEntity<>(response, httpStatus);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseWithMessageAndData<SoalDto>> updateSoal(@RequestParam String idSoal, @Valid @RequestBody SoalRequest soalRequest){
        ResponseWithMessageAndData<SoalDto> response = soalService.updateSoal(idSoal, soalRequest);
        HttpStatus httpStatus = HttpStatus.valueOf(response.getHttpCode());
        return new ResponseEntity<>(response, httpStatus);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseWithMessage> deleteSoal(@RequestParam String idSoal){
        ResponseWithMessage response = soalService.deleteSoal(idSoal);
        HttpStatus httpStatus = HttpStatus.valueOf(response.getHttpCode());
        return new ResponseEntity<>(response, httpStatus);
    }
}
