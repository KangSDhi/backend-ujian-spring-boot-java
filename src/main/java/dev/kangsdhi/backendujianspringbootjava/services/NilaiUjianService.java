package dev.kangsdhi.backendujianspringbootjava.services;

import dev.kangsdhi.backendujianspringbootjava.dto.request.HasilUjianRequest;
import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseWithMessageAndData;

public interface NilaiUjianService {
    ResponseWithMessageAndData<Object> findByIdNilaiUjian(String idNilaiUjian);
    ResponseWithMessageAndData<Object> generateHasilUjian(HasilUjianRequest hasilUjianRequest);
}
