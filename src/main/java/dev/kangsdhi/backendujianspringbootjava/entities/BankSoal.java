package dev.kangsdhi.backendujianspringbootjava.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Data
@Entity
@Table(name = "bank_soal")
public class BankSoal {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;

    @PrePersist
    public void generateUUID(){
        if (id == null) {
            id = UUID.randomUUID();
        }
    }

    @ManyToOne
    @JoinColumn(name = "soal_id", referencedColumnName = "id", nullable = false)
    private Soal soal;

    @Column(name = "pertanyaan_bank_soal", columnDefinition = "LONGTEXT", nullable = false)
    private String pertanyaanBankSoal;

    @Column(name = "gambar_pertanyaan_bank_soal", columnDefinition = "MEDIUMTEXT", nullable = true)
    private String gambarPertanyaanBankSoal;

    @Column(name = "pilihan_a", columnDefinition = "LONGTEXT", nullable = true)
    private String pilihanA;

    @Column(name = "pilihan_b", columnDefinition = "LONGTEXT", nullable = true)
    private String pilihanB;

    @Column(name = "pilihan_c", columnDefinition = "LONGTEXT", nullable = true)
    private String pilihanC;

    @Column(name = "pilihan_d", columnDefinition = "LONGTEXT", nullable = true)
    private String pilihanD;

    @Column(name = "pilihan_e", columnDefinition = "LONGTEXT", nullable = true)
    private String pilihanE;

    @Column(name = "nilai_a", columnDefinition = "FLOAT(10) UNSIGNED", nullable = true)
    private Float nilaiA;

    @Column(name = "nilai_b", columnDefinition = "FLOAT(10) UNSIGNED", nullable = true)
    private Float nilaiB;

    @Column(name = "nilai_c", columnDefinition = "FLOAT(10) UNSIGNED", nullable = true)
    private Float nilaiC;

    @Column(name = "nilai_d", columnDefinition = "FLOAT(10) UNSIGNED", nullable = true)
    private Float nilaiD;

    @Column(name = "nilai_e", columnDefinition = "FLOAT(10) UNSIGNED", nullable = true)
    private Float nilaiE;
}
