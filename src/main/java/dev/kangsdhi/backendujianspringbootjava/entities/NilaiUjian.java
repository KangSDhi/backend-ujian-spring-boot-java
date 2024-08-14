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
@Table(name = "nilai_ujian")
public class NilaiUjian {

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

    @OneToOne
    @JoinColumn(name = "ujian_id", referencedColumnName = "id", nullable = false)
    private Ujian ujian;

    @Column(name = "nilai_ujian", columnDefinition = "FLOAT(10) UNSIGNED")
    private Float nilaiUjian;
}
