package dev.kangsdhi.backendujianspringbootjava.dto.request;

import dev.kangsdhi.backendujianspringbootjava.validators.SiswaUniqueIDSiswa;
import dev.kangsdhi.backendujianspringbootjava.validators.SiswaUniqueNamaSiswa;
import jakarta.validation.Valid;
import lombok.Data;

import java.util.List;

@Data
public class SiswaCreateBatchRequest {

    @Valid
    @SiswaUniqueIDSiswa(message = "ID Siswa Sudah Terdaftar!")
    @SiswaUniqueNamaSiswa(message = "Nama Siswa Sudah Terdaftar!")
    List<SiswaRequest> data;

}
