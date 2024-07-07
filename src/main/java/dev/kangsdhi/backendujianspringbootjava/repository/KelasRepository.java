package dev.kangsdhi.backendujianspringbootjava.repository;

import dev.kangsdhi.backendujianspringbootjava.entities.Jurusan;
import dev.kangsdhi.backendujianspringbootjava.entities.Kelas;
import dev.kangsdhi.backendujianspringbootjava.entities.Tingkat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface KelasRepository extends JpaRepository<Kelas, UUID> {
    Kelas findByTingkatAndJurusan(Tingkat tingkat, Jurusan jurusan);
}
