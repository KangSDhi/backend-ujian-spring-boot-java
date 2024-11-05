package dev.kangsdhi.backendujianspringbootjava.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Data
@Entity
@Table(name = "tingkat")
@EqualsAndHashCode(callSuper = true)
public class Tingkat extends BaseEntity {

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

    @Column(name = "nama_tingkat", unique = true)
    private String tingkat;
}
