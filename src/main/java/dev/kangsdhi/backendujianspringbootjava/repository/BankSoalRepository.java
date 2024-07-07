package dev.kangsdhi.backendujianspringbootjava.repository;

import dev.kangsdhi.backendujianspringbootjava.entities.BankSoal;
import dev.kangsdhi.backendujianspringbootjava.entities.Soal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface BankSoalRepository extends JpaRepository<BankSoal, UUID> {
    List<BankSoal> findBySoal(Soal soal);

//    @Query("""
//            select bs
//            from BankSoal bs inner join BankSoal.soal s where s.namaSoal = :namaSoal""")
//    List<BankSoal> findBySoalInnerJoinSoal(@Param("namaSoal") String namaSoal);
}
