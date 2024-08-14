package dev.kangsdhi.backendujianspringbootjava.controllers.siswa;

import dev.kangsdhi.backendujianspringbootjava.dto.data.MataUjianDto;
import dev.kangsdhi.backendujianspringbootjava.dto.request.JawabanUjianRequest;
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
        HttpStatus httpStatus = mataUjianResponse.getData().isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        String message = mataUjianResponse.getData().isEmpty() ? "Soal Mata Ujian Tidak Tersedia!" : "Soal Mata Ujian Tersedia!";

        mataUjianResponse.setHttpCode(httpStatus.value());
        mataUjianResponse.setMessage(message);

        return new ResponseEntity<>(mataUjianResponse, httpStatus);
    }

    @GetMapping("/ujian")
    public ResponseEntity<ResponseWithMessageAndData<Object>> loadSoal(@RequestParam String idSoal){
        ResponseWithMessageAndData<Object> response = ujianService.loadDataJawabanSoal(idSoal);
        HttpStatus httpStatus = HttpStatus.valueOf(response.getHttpCode());
        return new ResponseEntity<>(response, httpStatus);
    }

    @GetMapping("/ujian/checkin")
    public ResponseEntity<ResponseWithMessage> checkInUjian(@RequestParam String idSoal){
        ResponseWithMessage response = ujianService.checkInUjian(idSoal);
        HttpStatus httpStatus = response.getMessage().equals("Ujian Ada!") ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        response.setHttpCode(httpStatus.value());
        return new ResponseEntity<>(response, httpStatus);
    }

    @GetMapping("/ujian/generate")
    public ResponseEntity<ResponseWithMessage> generateUjian(@RequestParam String idSoal){
        ResponseWithMessage response = ujianService.generateUjian(idSoal);
        HttpStatus httpStatus = HttpStatus.valueOf(response.getHttpCode());
        return new ResponseEntity<>(response, httpStatus);
    }

    @PostMapping("/ujian/jawab")
    public ResponseEntity<ResponseWithMessage> jawabUjian(@Valid @RequestBody JawabanUjianRequest jawabanUjianRequest){
        ResponseWithMessage response = ujianService.jawabUjian(jawabanUjianRequest);
        HttpStatus httpStatus = HttpStatus.valueOf(response.getHttpCode());
        return new ResponseEntity<>(response, httpStatus);
    }
}
