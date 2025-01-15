package dev.kangsdhi.backendujianspringbootjava.services;

import dev.kangsdhi.backendujianspringbootjava.dto.data.BankSoalDto;
import dev.kangsdhi.backendujianspringbootjava.dto.request.BankSoalCreateRequest;
import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseWithMessageAndData;

import java.util.List;

public interface BankSoalService {
    ResponseWithMessageAndData<List<BankSoalDto>> findBankSoalBySoalId(String soalId);
    ResponseWithMessageAndData<BankSoalDto> createBankSoal(BankSoalCreateRequest bankSoalCreateRequest);
}
