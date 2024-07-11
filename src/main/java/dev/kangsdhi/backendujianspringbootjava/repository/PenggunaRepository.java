package dev.kangsdhi.backendujianspringbootjava.repository;

import dev.kangsdhi.backendujianspringbootjava.entities.Pengguna;
import dev.kangsdhi.backendujianspringbootjava.entities.RolePengguna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PenggunaRepository extends JpaRepository<Pengguna, UUID> {
    List<Pengguna> findByRolePengguna(RolePengguna rolePengguna);
    Pengguna findByNamaPenggunaAndRolePengguna(String namaPengguna, RolePengguna rolePengguna);
    Optional<Pengguna> findByNamaPengguna(String namaPengguna);
    Optional<Pengguna> findByIdSiswa(String idSiswa);
    Optional<Pengguna> findByEmailPengguna(String emailPengguna);
}
