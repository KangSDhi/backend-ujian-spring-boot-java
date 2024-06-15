package dev.kangsdhi.backendujianspringbootjava.repository;

import dev.kangsdhi.backendujianspringbootjava.entities.Jurusan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JurusanRepository extends JpaRepository<Jurusan, UUID> {
    Jurusan findJurusanByJurusan(String namaJurusan);
}
