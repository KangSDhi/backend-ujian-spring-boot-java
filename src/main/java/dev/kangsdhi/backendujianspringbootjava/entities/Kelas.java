package dev.kangsdhi.backendujianspringbootjava.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "kelas")
public class Kelas {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @PrePersist
    public void generateUUID(){
        if (id == null){
            id = UUID.randomUUID();
        }
    }

    @ManyToOne
    @JoinColumn(name = "tingkat_id", referencedColumnName = "id", nullable = false)
    private Tingkat tingkat;

    @ManyToOne
    @JoinColumn(name = "jurusan_id", referencedColumnName = "id", nullable = false)
    private Jurusan jurusan;
}
