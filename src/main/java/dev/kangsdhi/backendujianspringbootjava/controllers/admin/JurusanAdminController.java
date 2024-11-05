package dev.kangsdhi.backendujianspringbootjava.controllers.admin;

import dev.kangsdhi.backendujianspringbootjava.dto.data.JurusanDto;
import dev.kangsdhi.backendujianspringbootjava.dto.request.JurusanCreateRequest;
import dev.kangsdhi.backendujianspringbootjava.dto.request.JurusanEditRequest;
import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseWithMessage;
import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseWithMessageAndData;
import dev.kangsdhi.backendujianspringbootjava.services.JurusanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/jurusan")
@RequiredArgsConstructor
public class JurusanAdminController {

    private final JurusanService jurusanService;

    @GetMapping("")
    public ResponseEntity<ResponseWithMessageAndData<List<JurusanDto>>> getAllJurusan() {
        ResponseWithMessageAndData<List<JurusanDto>> response = jurusanService.allJurusan();
        HttpStatus httpStatus = HttpStatus.valueOf(response.getHttpCode());
        return new ResponseEntity<>(response, httpStatus);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseWithMessageAndData<JurusanDto>> createJurusan(@Valid @RequestBody JurusanCreateRequest jurusanCreateRequest) {
        ResponseWithMessageAndData<JurusanDto> response = jurusanService.storeJurusan(jurusanCreateRequest);
        HttpStatus httpStatus = HttpStatus.valueOf(response.getHttpCode());
        return new ResponseEntity<>(response, httpStatus);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseWithMessageAndData<JurusanDto>> updateJurusan(@Valid @RequestBody JurusanEditRequest jurusanEditRequest) {
        ResponseWithMessageAndData<JurusanDto> response = jurusanService.updateJurusan(jurusanEditRequest);
        HttpStatus httpStatus = HttpStatus.valueOf(response.getHttpCode());
        return new ResponseEntity<>(response, httpStatus);
    }

    @DeleteMapping("/delete/{idJurusan}")
    public ResponseEntity<ResponseWithMessage> deleteJurusan(@PathVariable String idJurusan) {
        ResponseWithMessage response = jurusanService.destroyJurusan(idJurusan);
        HttpStatus httpStatus = HttpStatus.valueOf(response.getHttpCode());
        return new ResponseEntity<>(response, httpStatus);
    }
}
