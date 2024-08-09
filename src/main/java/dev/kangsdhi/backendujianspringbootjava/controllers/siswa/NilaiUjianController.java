package dev.kangsdhi.backendujianspringbootjava.controllers.siswa;

import dev.kangsdhi.backendujianspringbootjava.dto.request.HasilUjianRequest;
import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseWithMessageAndData;
import dev.kangsdhi.backendujianspringbootjava.services.NilaiUjianService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/siswa/nilai")
@RequiredArgsConstructor
public class NilaiUjianController {

    private final NilaiUjianService nilaiUjianService;

    @GetMapping("/findit")
    public ResponseEntity<ResponseWithMessageAndData<Object>> findItByNilaiId(@RequestParam String idNilaiUjian){
        ResponseWithMessageAndData<Object> response = nilaiUjianService.findByIdNilaiUjian(idNilaiUjian);
        HttpStatus httpStatus = HttpStatus.valueOf(response.getHttpCode());
        return new ResponseEntity<>(response, httpStatus);
    }

    @PostMapping("/generate")
    public ResponseEntity<ResponseWithMessageAndData<Object>> generateNilaiUjian(@Valid @RequestBody HasilUjianRequest hasilUjianRequest) {
        ResponseWithMessageAndData<Object> response = nilaiUjianService.generateHasilUjian(hasilUjianRequest);
        HttpStatus httpStatus = HttpStatus.valueOf(response.getHttpCode());
        return new ResponseEntity<>(response, httpStatus);
    }
}
