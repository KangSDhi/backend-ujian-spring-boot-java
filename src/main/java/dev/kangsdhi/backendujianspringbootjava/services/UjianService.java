package dev.kangsdhi.backendujianspringbootjava.services;

import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseWithMessage;

public interface UjianService {
    ResponseWithMessage checkInUjian(String idSoal);
    ResponseWithMessage generateUjian(String idSoal);
}
