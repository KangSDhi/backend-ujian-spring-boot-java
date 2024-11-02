package dev.kangsdhi.backendujianspringbootjava.controllers.admin;

import dev.kangsdhi.backendujianspringbootjava.dto.data.SoalDto;
import dev.kangsdhi.backendujianspringbootjava.dto.request.SoalCreateRequest;
import dev.kangsdhi.backendujianspringbootjava.dto.request.SoalEditRequest;
import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseWithMessage;
import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseWithMessageAndData;
import dev.kangsdhi.backendujianspringbootjava.services.SoalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/findbyid/{idSoal}")
    public ResponseEntity<ResponseWithMessageAndData<SoalDto>> getSoalById(@PathVariable String idSoal) {
        ResponseWithMessageAndData<SoalDto> response = soalService.soalById(idSoal);
        System.out.println(response.getData());
        HttpStatus httpStatus = HttpStatus.valueOf(response.getHttpCode());
        return new ResponseEntity<>(response, httpStatus);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseWithMessageAndData<SoalDto>> createSoal(@Valid @RequestBody SoalCreateRequest soalCreateRequest) {
        ResponseWithMessageAndData<SoalDto> response = soalService.createSoal(soalCreateRequest);
        HttpStatus httpStatus = HttpStatus.valueOf(response.getHttpCode());
        return new ResponseEntity<>(response, httpStatus);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseWithMessageAndData<SoalDto>> updateSoal(@Valid @RequestBody SoalEditRequest soalEditRequest) {
        ResponseWithMessageAndData<SoalDto> response = soalService.updateSoal(soalEditRequest);
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
