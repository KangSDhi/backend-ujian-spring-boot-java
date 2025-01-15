package dev.kangsdhi.backendujianspringbootjava.controllers.admin;

import dev.kangsdhi.backendujianspringbootjava.dto.data.BankSoalDto;
import dev.kangsdhi.backendujianspringbootjava.dto.request.BankSoalCreateRequest;
import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseWithMessageAndData;
import dev.kangsdhi.backendujianspringbootjava.services.BankSoalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/bank-soal")
@RequiredArgsConstructor
public class BankSoalAdminController {

    private final BankSoalService bankSoalService;

    @GetMapping("/findbyidsoal/{idSoal}")
    public ResponseEntity<ResponseWithMessageAndData<List<BankSoalDto>>> getBankSoalByidSoal(@PathVariable String idSoal) {
        ResponseWithMessageAndData<List<BankSoalDto>> response = bankSoalService.findBankSoalBySoalId(idSoal);
        HttpStatus httpStatus = HttpStatus.valueOf(response.getHttp_code());
        return new ResponseEntity<>(response, httpStatus);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseWithMessageAndData<BankSoalDto>> createBankSoal(@RequestBody @Valid BankSoalCreateRequest bankSoalCreateRequest) {
        ResponseWithMessageAndData<BankSoalDto> response = bankSoalService.createBankSoal(bankSoalCreateRequest);
        HttpStatus httpStatus = HttpStatus.valueOf(response.getHttp_code());
        return new ResponseEntity<>(response, httpStatus);
    }
}
