package dev.kangsdhi.backendujianspringbootjava.controllers.siswa;

import dev.kangsdhi.backendujianspringbootjava.dto.MataUjian;
import dev.kangsdhi.backendujianspringbootjava.dto.MataUjianRequest;
import dev.kangsdhi.backendujianspringbootjava.dto.MataUjianResponse;
import dev.kangsdhi.backendujianspringbootjava.services.MataUjianService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/siswa")
@RequiredArgsConstructor
public class UjianController {

    private final MataUjianService mataUjianService;

    @PostMapping("/mata-ujian")
    public ResponseEntity<MataUjianResponse<List<MataUjian>>> mataUjian(@Valid @RequestBody MataUjianRequest mataUjianRequest) {
        MataUjianResponse<List<MataUjian>> mataUjianResponse = mataUjianService.listMataUjian(mataUjianRequest);
        int sizeDataMataUjian = mataUjianResponse.getData().toArray().length;
        if (sizeDataMataUjian == 0) {
            mataUjianResponse.setHttpCode(HttpStatus.NOT_FOUND.value());
            mataUjianResponse.setMessage("Soal Mata Ujian Tidak Tersedia!");
            return new ResponseEntity<>(mataUjianResponse, HttpStatus.NOT_FOUND);
        } else {
            mataUjianResponse.setHttpCode(HttpStatus.OK.value());
            mataUjianResponse.setMessage("Soal Mata Ujian Tersedia!");
            return new ResponseEntity<>(mataUjianResponse, HttpStatus.OK);
        }
    }
}
