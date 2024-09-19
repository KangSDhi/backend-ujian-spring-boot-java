package dev.kangsdhi.backendujianspringbootjava.services;

import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseWithMessageAndData;
import dev.kangsdhi.backendujianspringbootjava.entities.Tingkat;

import java.util.List;

public interface TingkatService {
    ResponseWithMessageAndData<List<Tingkat>> getAllTingkat();
}
