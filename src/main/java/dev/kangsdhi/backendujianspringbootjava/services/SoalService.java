package dev.kangsdhi.backendujianspringbootjava.services;

import dev.kangsdhi.backendujianspringbootjava.dto.data.SoalDto;
import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseWithMessageAndData;

public interface SoalService {
    ResponseWithMessageAndData<SoalDto> soalById(String idSoal);
}
