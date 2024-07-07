package dev.kangsdhi.backendujianspringbootjava.entities;

import dev.kangsdhi.backendujianspringbootjava.enums.StatusUjian;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "ujian")
public class Ujian {

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

    @Column(name = "list_jawaban_ujian", columnDefinition = "LONGTEXT", nullable = true)
    private String listJawabanUjian;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "waktu_selesai_ujian", nullable = false)
    private Date waktuSelesaiUjian;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status_ujian", nullable = false)
    private StatusUjian statusUjian;

    @OneToOne
    @JoinColumn(name = "pengguna_id", referencedColumnName = "id", nullable = false)
    private Pengguna pengguna;

    @ManyToOne
    @JoinColumn(name = "soal_id", referencedColumnName = "id", nullable = false)
    private Soal soal;
}
