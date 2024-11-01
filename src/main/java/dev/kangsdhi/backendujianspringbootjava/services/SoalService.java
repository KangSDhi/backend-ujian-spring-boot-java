package dev.kangsdhi.backendujianspringbootjava.services;

import dev.kangsdhi.backendujianspringbootjava.dto.data.SoalDto;
import dev.kangsdhi.backendujianspringbootjava.dto.request.SoalCreateRequest;
import dev.kangsdhi.backendujianspringbootjava.dto.request.SoalEditRequest;
import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseWithMessage;
import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseWithMessageAndData;
import org.apache.coyote.BadRequestException;

import java.util.List;

public interface SoalService {
    ResponseWithMessage checkTokenSoal(String idSoal, String token);
    ResponseWithMessageAndData<List<SoalDto>> listAllSoal();
    ResponseWithMessageAndData<SoalDto> soalById(String idSoal);
    ResponseWithMessageAndData<SoalDto> createSoal(SoalCreateRequest soalCreateRequest);
    ResponseWithMessageAndData<SoalDto> updateSoal(SoalEditRequest soalEditRequest);
    ResponseWithMessage deleteSoal(String idSoal);
}
