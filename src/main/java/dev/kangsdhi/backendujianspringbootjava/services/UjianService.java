package dev.kangsdhi.backendujianspringbootjava.services;

import dev.kangsdhi.backendujianspringbootjava.dto.data.MataUjianDto;
import dev.kangsdhi.backendujianspringbootjava.dto.request.JawabanUjianRequest;
import dev.kangsdhi.backendujianspringbootjava.dto.request.MataUjianRequest;
import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseWithMessage;
import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseWithMessageAndData;
import dev.kangsdhi.backendujianspringbootjava.entities.BankSoal;

import java.util.List;

public interface UjianService {
    ResponseWithMessageAndData<List<MataUjianDto>> listMataUjian(MataUjianRequest mataUjianRequest);
    ResponseWithMessage checkInUjian(String idSoal);
    ResponseWithMessage generateUjian(String idSoal);
    ResponseWithMessageAndData<Object> loadDataJawabanSoal(String idSoal);
    ResponseWithMessage jawabUjian(JawabanUjianRequest jawabanUjianRequest);
}
