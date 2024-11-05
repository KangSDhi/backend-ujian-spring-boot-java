package dev.kangsdhi.backendujianspringbootjava.services;

import dev.kangsdhi.backendujianspringbootjava.dto.data.JurusanDto;
import dev.kangsdhi.backendujianspringbootjava.dto.request.JurusanCreateRequest;
import dev.kangsdhi.backendujianspringbootjava.dto.request.JurusanEditRequest;
import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseWithMessage;
import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseWithMessageAndData;

import java.util.List;

public interface JurusanService {
    ResponseWithMessageAndData<List<JurusanDto>> allJurusan();
    ResponseWithMessageAndData<JurusanDto> storeJurusan(JurusanCreateRequest jurusanCreateRequest);
    ResponseWithMessageAndData<JurusanDto> updateJurusan(JurusanEditRequest jurusanEditRequest);
    ResponseWithMessage destroyJurusan(String idJurusan);
}
