package dev.kangsdhi.backendujianspringbootjava.controllers.admin;

import dev.kangsdhi.backendujianspringbootjava.dto.data.SiswaDto;
import dev.kangsdhi.backendujianspringbootjava.dto.request.SiswaCreateBatchRequest;
import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseWithMessageAndData;
import dev.kangsdhi.backendujianspringbootjava.services.PenggunaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
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

    @PostMapping("/create/batch")
    public ResponseEntity<ResponseWithMessageAndData<List<SiswaDto>>> createBatchSiswa(@Valid @RequestBody SiswaCreateBatchRequest siswaCreateBatchRequest){
//        System.out.println(Arrays.toString(siswaCreateBatchRequest.getData().toArray()));
        ResponseWithMessageAndData<List<SiswaDto>> response = penggunaService.storeSiswaBatch(siswaCreateBatchRequest);
        HttpStatus httpStatus = HttpStatus.valueOf(response.getHttpCode());
        return new ResponseEntity<>(response, httpStatus);
    }
}
