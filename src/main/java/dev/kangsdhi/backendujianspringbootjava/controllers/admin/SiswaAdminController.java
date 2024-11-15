package dev.kangsdhi.backendujianspringbootjava.controllers.admin;

import dev.kangsdhi.backendujianspringbootjava.dto.data.SiswaDto;
import dev.kangsdhi.backendujianspringbootjava.dto.request.SiswaCreateBatchRequest;
import dev.kangsdhi.backendujianspringbootjava.dto.request.SiswaCreateRequest;
import dev.kangsdhi.backendujianspringbootjava.dto.request.SiswaEditRequest;
import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseWithMessage;
import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseWithMessageAndData;
import dev.kangsdhi.backendujianspringbootjava.services.PenggunaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/siswa")
@RequiredArgsConstructor
public class SiswaAdminController {

    private final PenggunaService penggunaService;

    @GetMapping("")
    public ResponseEntity<ResponseWithMessageAndData<List<SiswaDto>>> getAllSiswa(){
        ResponseWithMessageAndData<List<SiswaDto>> response = penggunaService.allSiswa();
        HttpStatus httpStatus = HttpStatus.valueOf(response.getHttpCode());
        return new ResponseEntity<>(response, httpStatus);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseWithMessageAndData<SiswaDto>> createSiswa(@RequestBody @Valid SiswaCreateRequest siswaCreateRequest){
        ResponseWithMessageAndData<SiswaDto> response = penggunaService.storeSiswa(siswaCreateRequest);
        HttpStatus httpStatus = HttpStatus.valueOf(response.getHttpCode());
        return new ResponseEntity<>(response, httpStatus);
    }

    @PostMapping("/create/batch")
    public ResponseEntity<ResponseWithMessageAndData<List<SiswaDto>>> createBatchSiswa(@Valid @RequestBody SiswaCreateBatchRequest siswaCreateBatchRequest){
        ResponseWithMessageAndData<List<SiswaDto>> response = penggunaService.storeSiswaBatch(siswaCreateBatchRequest);
        HttpStatus httpStatus = HttpStatus.valueOf(response.getHttpCode());
        return new ResponseEntity<>(response, httpStatus);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseWithMessageAndData<SiswaDto>> updateSiswa(@RequestBody @Valid SiswaEditRequest siswaEditRequest){
        ResponseWithMessageAndData<SiswaDto> response = penggunaService.updateSiswa(siswaEditRequest);
        HttpStatus httpStatus = HttpStatus.valueOf(response.getHttpCode());
        return new ResponseEntity<>(response, httpStatus);
    }

    @GetMapping("/findbyid/{idSiswa}")
    public ResponseEntity<ResponseWithMessageAndData<SiswaDto>> getSiswaById(@PathVariable String idSiswa) throws BadRequestException {
        ResponseWithMessageAndData<SiswaDto> response = penggunaService.findSiswaById(idSiswa);
        HttpStatus httpStatus = HttpStatus.valueOf(response.getHttpCode());
        return new ResponseEntity<>(response, httpStatus);
    }

    @DeleteMapping("/delete/{idSiswa}")
    public ResponseEntity<ResponseWithMessage> deleteSiswa(@PathVariable String idSiswa) throws BadRequestException {
        ResponseWithMessage response = penggunaService.deleteSiswa(idSiswa);
        HttpStatus httpStatus = HttpStatus.valueOf(response.getHttpCode());
        return new ResponseEntity<>(response, httpStatus);
    }
}
