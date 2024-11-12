package dev.kangsdhi.backendujianspringbootjava.dto.request;

import dev.kangsdhi.backendujianspringbootjava.validators.*;
import jakarta.validation.Valid;
import lombok.Data;

import java.util.List;

@Data
public class SiswaCreateBatchRequest {

    @Valid
    @SiswaUniqueIDSiswa()
    @SiswaUniqueNamaSiswa()
    @SiswaRequestMin(message = "Jumlah Item Tidak Boleh Kosong!")
    @SiswaRequestMax()
    @SiswaRequestCheckKelas()
    List<SiswaRequest> data;

}
