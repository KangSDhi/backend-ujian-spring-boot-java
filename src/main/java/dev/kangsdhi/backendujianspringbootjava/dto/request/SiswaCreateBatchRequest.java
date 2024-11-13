package dev.kangsdhi.backendujianspringbootjava.dto.request;

import dev.kangsdhi.backendujianspringbootjava.validators.*;
import jakarta.validation.Valid;
import lombok.Data;

import java.util.List;

@Data
public class SiswaCreateBatchRequest {

    @Valid
    @SiswaBatchRequestUniqueIDSiswa()
    @SiswaBatchRequestUniqueNamaSiswa()
    @SiswaBatchRequestMin(message = "Jumlah Item Tidak Boleh Kosong!")
    @SiswaBatchRequestMax()
    @SiswaBatchRequestCheckKelas()
    List<SiswaItemBatchRequest> data;

}
