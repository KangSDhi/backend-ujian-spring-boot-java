package dev.kangsdhi.backendujianspringbootjava.controllers.admin;

import dev.kangsdhi.backendujianspringbootjava.dto.data.TingkatDto;
import dev.kangsdhi.backendujianspringbootjava.dto.request.TingkatCreateRequest;
import dev.kangsdhi.backendujianspringbootjava.dto.request.TingkatEditRequest;
import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseWithMessage;
import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseWithMessageAndData;
import dev.kangsdhi.backendujianspringbootjava.services.TingkatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/tingkat")
@RequiredArgsConstructor
public class TingkatAdminController {

    private final TingkatService tingkatService;

    @GetMapping("")
    public ResponseEntity<ResponseWithMessageAndData<List<TingkatDto>>> getAllTingkat() {
        ResponseWithMessageAndData<List<TingkatDto>> response = tingkatService.allTingkat();
        HttpStatus httpStatus = HttpStatus.valueOf(response.getHttp_code());
        return new ResponseEntity<>(response, httpStatus);
    }

    @GetMapping("/findbyid/{idTingkat}")
    public ResponseEntity<ResponseWithMessageAndData<TingkatDto>> getTingkatById(@PathVariable String idTingkat) {
        ResponseWithMessageAndData<TingkatDto> response = tingkatService.findTingkatById(idTingkat);
        HttpStatus httpStatus = HttpStatus.valueOf(response.getHttp_code());
        return new ResponseEntity<>(response, httpStatus);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseWithMessageAndData<TingkatDto>> createTingkat(@Valid @RequestBody TingkatCreateRequest tingkatCreateRequest) {
        ResponseWithMessageAndData<TingkatDto> response = tingkatService.storeTingkat(tingkatCreateRequest);
        HttpStatus httpStatus = HttpStatus.valueOf(response.getHttp_code());
        return new ResponseEntity<>(response, httpStatus);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseWithMessageAndData<TingkatDto>> updateTingkat(@Valid @RequestBody TingkatEditRequest tingkatEditRequest) {
        ResponseWithMessageAndData<TingkatDto> response = tingkatService.updateTingkat(tingkatEditRequest);
        HttpStatus httpStatus = HttpStatus.valueOf(response.getHttp_code());
        return new ResponseEntity<>(response, httpStatus);
    }

    @DeleteMapping("/delete/{idTingkat}")
    public ResponseEntity<ResponseWithMessage> deleteTingkat(@PathVariable String idTingkat) {
        ResponseWithMessage response = tingkatService.destroyTingkat(idTingkat);
        HttpStatus httpStatus = HttpStatus.valueOf(response.getHttp_code());
        return new ResponseEntity<>(response, httpStatus);
    }
}
