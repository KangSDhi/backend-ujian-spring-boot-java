package dev.kangsdhi.backendujianspringbootjava.repository;

import dev.kangsdhi.backendujianspringbootjava.entities.Tingkat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TingkatRepository extends JpaRepository<Tingkat, UUID> {
    Tingkat findTingkatByTingkat(String tingkat);
}
