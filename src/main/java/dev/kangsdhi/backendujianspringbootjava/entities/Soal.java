package dev.kangsdhi.backendujianspringbootjava.entities;

import dev.kangsdhi.backendujianspringbootjava.enums.AcakSoal;
import dev.kangsdhi.backendujianspringbootjava.enums.TipeSoal;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "soal")
public class Soal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @PrePersist
    public void generateUUID(){
        if (id == null) {
            id = UUID.randomUUID();
        }
    }

    @Column(name = "nama_soal", nullable = false, unique = true)
    private String namaSoal;

    @Column(name = "butir_soal", nullable = false)
    private Integer butirSoal;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "acak_soal", nullable = false)
    private AcakSoal acakSoal;

    @Column(name = "waktu_mulai_soal", nullable = false)
    private Date waktuMulaiSoal;

    @Temporal(TemporalType.TIME)
    @Column(name = "durasi_soal", nullable = false)
    private Date durasiSoal;

    @Column(name = "waktu_selesai_soal", nullable = false)
    private Date waktuSelesaiSoal;

    @ManyToOne
    @JoinColumn(name = "tingkat_id", referencedColumnName = "id", nullable = false)
    private Tingkat tingkat;

    @ManyToOne
    @JoinColumn(name = "jurusan_id", referencedColumnName = "id", nullable = true)
    private Jurusan jurusan;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "tipe_soal", nullable = false)
    private TipeSoal tipeSoal;
}
