package dev.kangsdhi.backendujianspringbootjava.dto.request;

import dev.kangsdhi.backendujianspringbootjava.validators.SiswaRequestMax;
import dev.kangsdhi.backendujianspringbootjava.validators.SiswaUniqueIDSiswa;
import dev.kangsdhi.backendujianspringbootjava.validators.SiswaUniqueNamaSiswa;
import jakarta.validation.Valid;
import lombok.Data;

import java.util.List;

@Data
public class SiswaCreateBatchRequest {

    @Valid
    @SiswaUniqueIDSiswa()
    @SiswaUniqueNamaSiswa()
    @SiswaRequestMax()
    List<SiswaRequest> data;

}
