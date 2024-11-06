package dev.kangsdhi.backendujianspringbootjava.dto.request;

import dev.kangsdhi.backendujianspringbootjava.validators.SiswaDuplicateID;
import dev.kangsdhi.backendujianspringbootjava.validators.SiswaUniqueIDSiswa;
import dev.kangsdhi.backendujianspringbootjava.validators.SiswaUniqueNamaSiswa;
import jakarta.validation.Valid;
import lombok.Data;

import java.util.List;

@Data
public class SiswaCreateBatchRequest {

    @Valid
    @SiswaDuplicateID(message = "Duplicated ID Siswa detected in the batch!")
    @SiswaUniqueIDSiswa(message = "Duplicated Unique ID Siswa detected in the batch!")
    @SiswaUniqueNamaSiswa(message = "Unique")
    List<SiswaRequest> data;

}
