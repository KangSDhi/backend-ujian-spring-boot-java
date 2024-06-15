package dev.kangsdhi.backendujianspringbootjava.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "jurusan")
public class Jurusan {

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

    @Column(name = "nama_jurusan", unique = true)
    private String jurusan;
}
