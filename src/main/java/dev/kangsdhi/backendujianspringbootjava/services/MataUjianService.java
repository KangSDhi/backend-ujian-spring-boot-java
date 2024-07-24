package dev.kangsdhi.backendujianspringbootjava.services;

import dev.kangsdhi.backendujianspringbootjava.dto.data.MataUjian;
import dev.kangsdhi.backendujianspringbootjava.dto.request.MataUjianRequest;
import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseWithMessageAndData;

import java.util.List;

public interface MataUjianService {
    ResponseWithMessageAndData<List<MataUjian>> listMataUjian(MataUjianRequest mataUjianRequest);
}
