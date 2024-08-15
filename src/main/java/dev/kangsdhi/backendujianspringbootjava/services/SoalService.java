package dev.kangsdhi.backendujianspringbootjava.services;

import dev.kangsdhi.backendujianspringbootjava.dto.data.SoalDto;
import dev.kangsdhi.backendujianspringbootjava.dto.request.SoalRequest;
import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseWithMessage;
import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseWithMessageAndData;

import java.util.List;

public interface SoalService {
    ResponseWithMessageAndData<List<SoalDto>> listAllSoal();
    ResponseWithMessageAndData<SoalDto> soalById(String idSoal);
    ResponseWithMessageAndData<SoalDto> createSoal(SoalRequest soalRequest);
    ResponseWithMessageAndData<SoalDto> updateSoal(String idSoal, SoalRequest soalRequest);
    ResponseWithMessage deleteSoal(String idSoal);
}
