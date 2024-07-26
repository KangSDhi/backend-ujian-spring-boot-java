package dev.kangsdhi.backendujianspringbootjava.controllers.siswa;

import dev.kangsdhi.backendujianspringbootjava.dto.data.MataUjianDto;
import dev.kangsdhi.backendujianspringbootjava.dto.request.MataUjianRequest;
import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseWithMessage;
import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseWithMessageAndData;
import dev.kangsdhi.backendujianspringbootjava.services.MataUjianService;
import dev.kangsdhi.backendujianspringbootjava.services.UjianService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/siswa")
@RequiredArgsConstructor
public class UjianController {

    private final MataUjianService mataUjianService;
    private final UjianService ujianService;

    @PostMapping("/mata-ujian")
    public ResponseEntity<ResponseWithMessageAndData<List<MataUjianDto>>> mataUjian(@Valid @RequestBody MataUjianRequest mataUjianRequest) {
        ResponseWithMessageAndData<List<MataUjianDto>> mataUjianResponse = mataUjianService.listMataUjian(mataUjianRequest);
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

    @GetMapping("/ujian/checkin")
    public ResponseEntity<ResponseWithMessage> checkInUjian(@RequestParam String idSoal){
        ResponseWithMessage response = ujianService.checkInUjian(idSoal);
        if (response.getMessage().equals("Ujian Ada!")){
            response.setHttpCode(HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setHttpCode(HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/ujian/generate")
    public ResponseEntity<ResponseWithMessage> generateUjian(@RequestParam String idSoal){
        return ResponseEntity.ok(ujianService.generateUjian(idSoal));
    }
}
