package dev.kangsdhi.backendujianspringbootjava.dto.data;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SiswaDto {
    private String id;
    private String id_siswa;
    private String nama_siswa;
    private String password;
    private String kelas;
    private String tingkat;
    private String jurusan;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}
