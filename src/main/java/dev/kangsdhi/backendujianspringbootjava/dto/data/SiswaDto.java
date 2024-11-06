package dev.kangsdhi.backendujianspringbootjava.dto.data;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SiswaDto {
    private String id;
    private String idSiswa;
    private String namaSiswa;
    private String passwordSiswa;
    private String kelasSiswa;
    private String tingkatSiswa;
    private String jurusanSiswa;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
