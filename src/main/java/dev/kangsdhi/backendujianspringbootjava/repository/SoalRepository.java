package dev.kangsdhi.backendujianspringbootjava.repository;

import dev.kangsdhi.backendujianspringbootjava.entities.Jurusan;
import dev.kangsdhi.backendujianspringbootjava.entities.Soal;
import dev.kangsdhi.backendujianspringbootjava.entities.Tingkat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface SoalRepository extends JpaRepository<Soal, UUID> {
    Soal findByNamaSoal(String namaSoal);

    @Query(value = "SELECT s FROM Soal as s WHERE FUNCTION('DATE_FORMAT', s.waktuMulaiSoal, '%Y-%m-%d') = :today " +
            "AND s.tingkat = :tingkat " +
            "AND (s.jurusan IS NULL OR s.jurusan = :jurusan) " +
            "ORDER BY s.waktuMulaiSoal ASC", nativeQuery = false)
    List<Soal> findSoalForSiswa(@Param("today") String today,
                                @Param("tingkat") Tingkat tingkat,
                                @Param("jurusan") Jurusan jurusan);
}
