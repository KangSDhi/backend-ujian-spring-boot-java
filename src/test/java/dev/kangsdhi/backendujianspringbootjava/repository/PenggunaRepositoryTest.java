package dev.kangsdhi.backendujianspringbootjava.repository;

import dev.kangsdhi.backendujianspringbootjava.entities.Pengguna;
import dev.kangsdhi.backendujianspringbootjava.entities.RolePengguna;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PenggunaRepositoryTest {

    @Autowired
    private PenggunaRepository penggunaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @TestConfiguration
    static class PasswordEncoderConfig {
        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
    }

    @Test
    void penggunaRepositorySaveShouldReturnSavedPengguna() {
        // Arrange
        Pengguna pengguna = new Pengguna();
        pengguna.setNamaPengguna("Kang Test");
        pengguna.setEmailPengguna("kangtest@gmail.com");
        pengguna.setRolePengguna(RolePengguna.ADMIN);
        pengguna.setPasswordPengguna(passwordEncoder.encode("password"));

        //Act
        Pengguna penggunaSaved = penggunaRepository.save(pengguna);

        //Assert
        assertNotNull(penggunaSaved);
    }

    @Test
    void penggunaRepositoryGetAllShouldReturnAllPengguna() {
        Pengguna pengguna0 = new Pengguna();
        pengguna0.setNamaPengguna("Kang Test 0");
        pengguna0.setEmailPengguna("kangtest0@gmail.com");
        pengguna0.setRolePengguna(RolePengguna.ADMIN);
        pengguna0.setPasswordPengguna(passwordEncoder.encode("password"));

        Pengguna pengguna1 = new Pengguna();
        pengguna1.setNamaPengguna("Kang Test 1");
        pengguna1.setEmailPengguna("kangtest1@gmail.com");
        pengguna1.setRolePengguna(RolePengguna.ADMIN);
        pengguna1.setPasswordPengguna(passwordEncoder.encode("password"));

        penggunaRepository.save(pengguna0);
        penggunaRepository.save(pengguna1);

        List<Pengguna> penggunaList = penggunaRepository.findAll();
        assertNotNull(penggunaList);
        assertEquals(7, penggunaList.size());
    }

    @Test
    void penggunaRepositoryFindByIdShouldReturnPengguna() {
        Pengguna pengguna = new Pengguna();
        pengguna.setNamaPengguna("Kang Test");
        pengguna.setEmailPengguna("kangtest@gmail.com");
        pengguna.setRolePengguna(RolePengguna.ADMIN);
        pengguna.setPasswordPengguna(passwordEncoder.encode("password"));

        penggunaRepository.save(pengguna);

        Pengguna penggunaFindById = penggunaRepository.findById(pengguna.getId()).get();
        assertNotNull(penggunaFindById);
    }

    @Test
    void penggunaRepositoryFindByRolePenggunaShouldReturnPengguna() {
        Pengguna pengguna = new Pengguna();
        pengguna.setNamaPengguna("Kang Test");
        pengguna.setEmailPengguna("kangtest@gmail.com");
        pengguna.setRolePengguna(RolePengguna.ADMIN);
        pengguna.setPasswordPengguna(passwordEncoder.encode("password"));

        penggunaRepository.save(pengguna);

        List<Pengguna> penggunaFindByRolePengguna = penggunaRepository.findByRolePengguna(RolePengguna.ADMIN);
        assertNotNull(penggunaFindByRolePengguna);
    }

    @Test
    void penggunaRepositoryUpdatePenggunaShouldReturnPengguna() {
        Pengguna pengguna = new Pengguna();
        pengguna.setNamaPengguna("Kang Test 0");
        pengguna.setEmailPengguna("kangtest0@gmail.com");
        pengguna.setRolePengguna(RolePengguna.ADMIN);
        pengguna.setPasswordPengguna(passwordEncoder.encode("password"));

        penggunaRepository.save(pengguna);

        Pengguna penggunaUpdate = penggunaRepository.findById(pengguna.getId()).get();
        penggunaUpdate.setNamaPengguna("Kang Test 1");
        penggunaUpdate.setRolePengguna(RolePengguna.GURU);
        Pengguna updatePengguna = penggunaRepository.save(penggunaUpdate);

        assertNotNull(updatePengguna.getNamaPengguna());
        assertNotNull(updatePengguna.getRolePengguna());
    }

    @Test
    void penggunaRepositoryDeletePenggunaShouldReturnEmpty() {
        Pengguna pengguna = new Pengguna();
        pengguna.setNamaPengguna("Kang Test 0");
        pengguna.setEmailPengguna("kangtest0@gmail.com");
        pengguna.setRolePengguna(RolePengguna.ADMIN);
        pengguna.setPasswordPengguna(passwordEncoder.encode("password"));

        Pengguna savedPengguna = penggunaRepository.save(pengguna);
        penggunaRepository.deleteById(savedPengguna.getId());
        Optional<Pengguna> penggunaFindById = penggunaRepository.findById(savedPengguna.getId());

        assertTrue(penggunaFindById.isEmpty());
    }
}