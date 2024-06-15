package dev.kangsdhi.backendujianspringbootjava.repository;

import dev.kangsdhi.backendujianspringbootjava.entities.Kelas;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface KelasRepository extends JpaRepository<Kelas, UUID> {
}
