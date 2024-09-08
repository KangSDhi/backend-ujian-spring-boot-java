package dev.kangsdhi.backendujianspringbootjava.services;

import dev.kangsdhi.backendujianspringbootjava.dto.request.CreatePenggunaAdminRequest;
import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseWithMessageAndData;
import dev.kangsdhi.backendujianspringbootjava.entities.Pengguna;
import dev.kangsdhi.backendujianspringbootjava.repository.PenggunaRepository;
import dev.kangsdhi.backendujianspringbootjava.services.Implementation.PenggunaServiceImplementation;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PenggunaServiceTest {

    @Mock
    private PenggunaRepository penggunaRepository;

    @InjectMocks
    private PenggunaServiceImplementation penggunaService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void penggunaServiceCreatePenggunaAdminReturnPenggunaDto() throws BadRequestException {
        Pengguna pengguna = new Pengguna();
        pengguna.setNamaPengguna("Kang Mimin");
        pengguna.setEmailPengguna("kangmimin@gmail.com");
        pengguna.setPasswordPengguna(passwordEncoder.encode("password"));

        CreatePenggunaAdminRequest createPenggunaAdminRequest = new CreatePenggunaAdminRequest();
        createPenggunaAdminRequest.setNamaAdmin("Kang Mimin");
        createPenggunaAdminRequest.setEmailAdmin("kangmimin@gmail.com");
        createPenggunaAdminRequest.setPasswordAdmin("password");
        createPenggunaAdminRequest.setKonfimasiPasswordAdmin("password");

        when(penggunaRepository.save(Mockito.any(Pengguna.class))).thenAnswer(invocationOnMock -> {
            Pengguna savedPengguna = invocationOnMock.getArgument(0);
            savedPengguna.setId(UUID.randomUUID());
            return savedPengguna;
        });

        ResponseWithMessageAndData<Object> response = penggunaService.createPenggunaRoleAdmin(createPenggunaAdminRequest);
        System.out.println(response.getData().toString());
        assertNotNull(response);
    }

}