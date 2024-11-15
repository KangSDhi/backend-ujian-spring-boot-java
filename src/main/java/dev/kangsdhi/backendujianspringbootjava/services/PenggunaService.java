package dev.kangsdhi.backendujianspringbootjava.services;

import dev.kangsdhi.backendujianspringbootjava.dto.data.SiswaDto;
import dev.kangsdhi.backendujianspringbootjava.dto.request.CreatePenggunaAdminRequest;
import dev.kangsdhi.backendujianspringbootjava.dto.request.SiswaCreateBatchRequest;
import dev.kangsdhi.backendujianspringbootjava.dto.request.SiswaCreateRequest;
import dev.kangsdhi.backendujianspringbootjava.dto.request.SiswaEditRequest;
import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseWithMessageAndData;
import org.apache.coyote.BadRequestException;

import java.util.List;

public interface PenggunaService {
    ResponseWithMessageAndData<Object> createPenggunaRoleAdmin(CreatePenggunaAdminRequest createPenggunaAdminRequest) throws BadRequestException;
    ResponseWithMessageAndData<List<SiswaDto>> allSiswa();
    ResponseWithMessageAndData<List<SiswaDto>> storeSiswaBatch(SiswaCreateBatchRequest siswaCreateBatchRequest);
    ResponseWithMessageAndData<SiswaDto> storeSiswa(SiswaCreateRequest siswaCreateRequest);
    ResponseWithMessageAndData<SiswaDto> updateSiswa(SiswaEditRequest siswaEditRequest);
}
