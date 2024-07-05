package dev.kangsdhi.backendujianspringbootjava.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "nilai_ujian")
public class NilaiUjian {

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

    @OneToOne
    @JoinColumn(name = "ujian_id", referencedColumnName = "id", nullable = false)
    private Ujian ujian;

    @Column(name = "nilai_ujian", columnDefinition = "INT(10) UNSIGNED")
    private Integer nilaiUjian;
}
