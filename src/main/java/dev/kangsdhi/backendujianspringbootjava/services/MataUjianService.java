package dev.kangsdhi.backendujianspringbootjava.services;

import dev.kangsdhi.backendujianspringbootjava.dto.MataUjian;
import dev.kangsdhi.backendujianspringbootjava.dto.MataUjianRequest;
import dev.kangsdhi.backendujianspringbootjava.dto.MataUjianResponse;

import java.util.List;

public interface MataUjianService {
    MataUjianResponse<List<MataUjian>> listMataUjian(MataUjianRequest mataUjianRequest);
}
