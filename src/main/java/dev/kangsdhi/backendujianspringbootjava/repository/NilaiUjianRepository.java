package dev.kangsdhi.backendujianspringbootjava.repository;

import dev.kangsdhi.backendujianspringbootjava.entities.NilaiUjian;
import dev.kangsdhi.backendujianspringbootjava.entities.Ujian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface NilaiUjianRepository extends JpaRepository<NilaiUjian, UUID> {
    Optional<NilaiUjian> findByUjian(Ujian ujian);
}
