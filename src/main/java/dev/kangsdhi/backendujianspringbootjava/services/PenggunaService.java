package dev.kangsdhi.backendujianspringbootjava.services;

import dev.kangsdhi.backendujianspringbootjava.dto.request.CreatePenggunaAdminRequest;
import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseWithMessageAndData;
import org.apache.coyote.BadRequestException;

public interface PenggunaService {
    ResponseWithMessageAndData<Object> createPenggunaRoleAdmin(CreatePenggunaAdminRequest createPenggunaAdminRequest) throws BadRequestException;
}
