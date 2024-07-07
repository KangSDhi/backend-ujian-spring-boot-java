package dev.kangsdhi.backendujianspringbootjava.repository;

import dev.kangsdhi.backendujianspringbootjava.entities.Soal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SoalRepository extends JpaRepository<Soal, UUID> {
    Soal findByNamaSoal(String namaSoal);
}
