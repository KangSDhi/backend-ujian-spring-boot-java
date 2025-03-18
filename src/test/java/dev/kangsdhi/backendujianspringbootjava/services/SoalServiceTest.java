package dev.kangsdhi.backendujianspringbootjava.services;

import dev.kangsdhi.backendujianspringbootjava.dto.data.SoalDto;
import dev.kangsdhi.backendujianspringbootjava.dto.request.SoalCreateRequest;
import dev.kangsdhi.backendujianspringbootjava.dto.request.SoalEditRequest;
import dev.kangsdhi.backendujianspringbootjava.dto.request.SoalRequest;
import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseWithMessageAndData;
import dev.kangsdhi.backendujianspringbootjava.entities.Soal;
import dev.kangsdhi.backendujianspringbootjava.entities.Tingkat;
import dev.kangsdhi.backendujianspringbootjava.enums.AcakSoal;
import dev.kangsdhi.backendujianspringbootjava.enums.TipeSoal;
import dev.kangsdhi.backendujianspringbootjava.repository.JurusanRepository;
import dev.kangsdhi.backendujianspringbootjava.repository.SoalRepository;
import dev.kangsdhi.backendujianspringbootjava.repository.TingkatRepository;
import dev.kangsdhi.backendujianspringbootjava.services.Implementation.SoalServiceImplementation;
import dev.kangsdhi.backendujianspringbootjava.utils.ConvertUtils;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SoalServiceTest {

    @Mock
    private SoalRepository soalRepository;

    @Mock
    private TingkatRepository tingkatRepository;

    @Mock
    private JurusanRepository jurusanRepository;

    @InjectMocks
    private SoalServiceImplementation soalService;

    private Soal mockSoal;

    private SoalCreateRequest mockSoalCreateRequest;

    private SoalEditRequest mockSoalEditRequest;

    private Tingkat mockTingkat;

    @BeforeEach
    void Init(){
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        ConvertUtils convertUtils = new ConvertUtils();

        mockTingkat = new Tingkat();
        mockTingkat.setId(UUID.randomUUID());
        mockTingkat.setTingkat("X");

        mockSoal = new Soal();
        mockSoal.setId(UUID.randomUUID());
        mockSoal.setNamaSoal("Soal Test");
        mockSoal.setTingkat(mockTingkat);
        mockSoal.setAcakSoal(AcakSoal.ACAK);
        mockSoal.setButirSoal(100);
        mockSoal.setWaktuMulaiSoal(convertUtils.convertStringToDatetimeOrTime(currentDate.format(formatter) + " 05:00:00"));
        mockSoal.setDurasiSoal(convertUtils.convertStringToDatetimeOrTime("03:00:00"));
        mockSoal.setWaktuSelesaiSoal(convertUtils.convertStringToDatetimeOrTime(currentDate.format(formatter) + " 04:00:00"));

        mockSoalCreateRequest = new SoalCreateRequest();
        mockSoalCreateRequest.setNama_soal("Soal Test Create");
        mockSoalCreateRequest.setTingkat("X");
        mockSoalCreateRequest.setAcak_soal(AcakSoal.ACAK);
        mockSoalCreateRequest.setButir_soal(100);
        mockSoalCreateRequest.setDurasi_soal("01:00:00");
        mockSoalCreateRequest.setTipe_soal(TipeSoal.PILIHAN_GANDA);
        mockSoalCreateRequest.setWaktu_mulai_soal(currentDate.format(formatter) + " 01:00:00");
        mockSoalCreateRequest.setWaktu_selesai_soal(currentDate.format(formatter) + " 04:00:00");

        mockSoalEditRequest = new SoalEditRequest();
        mockSoalEditRequest.setId("f823ba29-b657-4516-bc2e-e9ef45333a5e");
        mockSoalEditRequest.setNama_soal("Soal Test Edit");
        mockSoalEditRequest.setTingkat("XI");
        mockSoalEditRequest.setAcak_soal(AcakSoal.ACAK);
        mockSoalEditRequest.setButir_soal(100);
        mockSoalEditRequest.setDurasi_soal("02:00:00");
        mockSoalEditRequest.setTipe_soal(TipeSoal.PILIHAN_GANDA);
        mockSoalEditRequest.setWaktu_mulai_soal(currentDate.format(formatter) + " 02:00:00");
        mockSoalEditRequest.setWaktu_selesai_soal(currentDate.format(formatter) + " 04:00:00");
    }

    @Test
    void soalServiceGetAllSoalReturnResponseWithMessageAndData(){
        List<Soal> soalList = new ArrayList<>();
        soalList.add(mockSoal);

        // Sort Specification
        Sort sort = Sort.by(Sort.Direction.DESC, "waktuMulaiSoal")
                .and(Sort.by(Sort.Direction.ASC, "tingkat.tingkat"))
                .and(Sort.by(Sort.Direction.ASC, "jurusan.jurusan"));

        // Mock the repository behavior
        when(soalRepository.findAll(sort)).thenReturn(soalList);

        // Execute the service method
        ResponseWithMessageAndData<List<SoalDto>> response = soalService.listAllSoal();

        // Assert the results
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getHttp_code());
        assertEquals("Berhasil Mengambil Data Soal!", response.getMessage());
        assertNotNull(response.getData());

        // Verify that the repository method was called once
        Mockito.verify(soalRepository, Mockito.times(1)).findAll(sort);
    }

    @Test
    void soalServiceCreateSoalReturnResponseWithMessageAndData(){
        when(tingkatRepository.findTingkatByTingkat("X")).thenReturn(mockTingkat);
        when(soalRepository.save(Mockito.any(Soal.class))).thenReturn(mockSoal);

        ResponseWithMessageAndData<SoalDto> response = soalService.createSoal(mockSoalCreateRequest);
        System.out.println(response);
        assertNotNull(response);
    }

    @Test
    void soalServiceGetSoalByIdReturnResponseWithMessageAndData(){
        String stringId = "f823ba29-b657-4516-bc2e-e9ef45333a5e";

        UUID uuid = UUID.fromString(stringId);

        mockSoal.setId(uuid);

        when(soalRepository.findById(uuid)).thenReturn(Optional.of(mockSoal));

        ResponseWithMessageAndData<SoalDto> response = soalService.soalById(stringId);

        assertNotNull(response);
    }

    @Test
    void soalServiceUpdateSoalReturnResponseWithMessageAndData(){
        String stringId = "f823ba29-b657-4516-bc2e-e9ef45333a5e";

        UUID uuid = UUID.fromString(stringId);

        mockSoal.setId(uuid);

        when(soalRepository.findById(uuid)).thenReturn(Optional.of(mockSoal));
        lenient().when(tingkatRepository.findTingkatByTingkat(mockSoalEditRequest.getTingkat())).thenReturn(mockTingkat);
        lenient().when(jurusanRepository.findJurusanByJurusan(null)).thenReturn(null);
        when(soalRepository.save(Mockito.any(Soal.class))).thenReturn(mockSoal);

        ResponseWithMessageAndData<SoalDto> response = soalService.updateSoal(mockSoalEditRequest);

        assertNotNull(response);
    }

    @Test
    void soalServiceDeleteSoalReturnResponseWithMessage(){
        String stringId = "f823ba29-b657-4516-bc2e-e9ef45333a5e";

        UUID uuid = UUID.fromString(stringId);

        mockSoal.setId(uuid);

        when(soalRepository.findById(uuid)).thenReturn(Optional.of(mockSoal));

        assertAll(() -> soalService.deleteSoal(stringId));
    }
}