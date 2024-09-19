package dev.kangsdhi.backendujianspringbootjava.services;

import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseWithMessageAndData;
import dev.kangsdhi.backendujianspringbootjava.entities.Jurusan;

import java.util.List;

public interface JurusanService {
    ResponseWithMessageAndData<List<Jurusan>> getAllJurusan();
}
