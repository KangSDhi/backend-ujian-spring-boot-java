package dev.kangsdhi.backendujianspringbootjava.services;

import dev.kangsdhi.backendujianspringbootjava.dto.data.TingkatDto;
import dev.kangsdhi.backendujianspringbootjava.dto.request.TingkatCreateRequest;
import dev.kangsdhi.backendujianspringbootjava.dto.request.TingkatEditRequest;
import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseWithMessage;
import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseWithMessageAndData;

import java.util.List;

public interface TingkatService {
    ResponseWithMessageAndData<List<TingkatDto>> allTingkat();
    ResponseWithMessageAndData<TingkatDto> findTingkatById(String id);
    ResponseWithMessageAndData<TingkatDto> storeTingkat(TingkatCreateRequest tingkatCreateRequest);
    ResponseWithMessageAndData<TingkatDto> updateTingkat(TingkatEditRequest tingkatEditRequest);
    ResponseWithMessage destroyTingkat(String idTingkat);
}
