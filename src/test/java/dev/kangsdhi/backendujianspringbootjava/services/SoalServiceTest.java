package dev.kangsdhi.backendujianspringbootjava.services;

import dev.kangsdhi.backendujianspringbootjava.dto.data.SoalDto;
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

    @Test
    void soalServiceGetAllSoalReturnResponseWithMessageAndData(){
        // Create mock data
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        Tingkat tingkat = new Tingkat();
        tingkat.setId(UUID.randomUUID());
        tingkat.setTingkat("X");

        ConvertUtils convertUtils = new ConvertUtils();
        List<Soal> soalList = new ArrayList<>();
        Soal mockSoal = new Soal();
        mockSoal.setId(UUID.randomUUID());
        mockSoal.setNamaSoal("Soal Test");
        mockSoal.setTingkat(tingkat);
        mockSoal.setAcakSoal(AcakSoal.ACAK);
        mockSoal.setButirSoal(100);
        mockSoal.setWaktuMulaiSoal(convertUtils.convertStringToDatetimeOrTime(currentDate.format(formatter) + " 05:00:00"));
        mockSoal.setDurasiSoal(convertUtils.convertStringToDatetimeOrTime("03:00:00"));
        mockSoal.setWaktuSelesaiSoal(convertUtils.convertStringToDatetimeOrTime(currentDate.format(formatter) + " 04:00:00"));
        soalList.add(mockSoal);

        // Sort Specification
        Sort sort = Sort.by(Sort.Direction.ASC, "waktuMulaiSoal");

        // Mock the repository behavior
        when(soalRepository.findAll(sort)).thenReturn(soalList);

        // Execute the service method
        ResponseWithMessageAndData<List<SoalDto>> response = soalService.listAllSoal();

        // Assert the results
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getHttpCode());
        assertEquals("Berhasil Mengambil Data Soal!", response.getMessage());
        assertNotNull(response.getData());

        // Verify that the repository method was called once
        Mockito.verify(soalRepository, Mockito.times(1)).findAll(sort);
    }

    @Test
    void soalServiceGetSoalByIdReturnResponseWithMessageAndData(){
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        Tingkat tingkat = new Tingkat();
        tingkat.setId(UUID.randomUUID());
        tingkat.setTingkat("X");

        String stringId = "f823ba29-b657-4516-bc2e-e9ef45333a5e";

        UUID uuid = UUID.fromString(stringId);

        ConvertUtils convertUtils = new ConvertUtils();
        Soal mockSoal = new Soal();
        mockSoal.setId(uuid);
        mockSoal.setNamaSoal("Soal Test");
        mockSoal.setTingkat(tingkat);
        mockSoal.setAcakSoal(AcakSoal.ACAK);
        mockSoal.setButirSoal(100);
        mockSoal.setWaktuMulaiSoal(convertUtils.convertStringToDatetimeOrTime(currentDate.format(formatter) + " 05:00:00"));
        mockSoal.setDurasiSoal(convertUtils.convertStringToDatetimeOrTime("03:00:00"));
        mockSoal.setWaktuSelesaiSoal(convertUtils.convertStringToDatetimeOrTime(currentDate.format(formatter) + " 04:00:00"));

        when(soalRepository.findById(uuid)).thenReturn(Optional.of(mockSoal));

        ResponseWithMessageAndData<SoalDto> response = soalService.soalById(stringId);

        assertNotNull(response);
    }

    @Test
    void soalServiceUpdateSoalReturnResponseWithMessageAndData(){
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        Tingkat tingkat = new Tingkat();
        tingkat.setId(UUID.randomUUID());
        tingkat.setTingkat("X");

        String stringId = "f823ba29-b657-4516-bc2e-e9ef45333a5e";

        UUID uuid = UUID.fromString(stringId);

        ConvertUtils convertUtils = new ConvertUtils();
        Soal mockSoal = new Soal();
        mockSoal.setId(uuid);
        mockSoal.setNamaSoal("Soal Test");
        mockSoal.setTingkat(tingkat);
        mockSoal.setAcakSoal(AcakSoal.ACAK);
        mockSoal.setButirSoal(100);
        mockSoal.setWaktuMulaiSoal(convertUtils.convertStringToDatetimeOrTime(currentDate.format(formatter) + " 05:00:00"));
        mockSoal.setDurasiSoal(convertUtils.convertStringToDatetimeOrTime("03:00:00"));
        mockSoal.setWaktuSelesaiSoal(convertUtils.convertStringToDatetimeOrTime(currentDate.format(formatter) + " 04:00:00"));

        SoalRequest soalRequest = new SoalRequest();
        soalRequest.setNamaSoal("Soal Test Update");
        soalRequest.setTingkatSoal("X");
        soalRequest.setAcakSoal(AcakSoal.ACAK);
        soalRequest.setButirSoal(100);
        soalRequest.setDurasiSoal("01:00:00");
        soalRequest.setTipeSoal(TipeSoal.PILIHAN_GANDA);
        soalRequest.setWaktuMulaiSoal(currentDate.format(formatter) + " 01:00:00");
        soalRequest.setWaktuSelesaiSoal(currentDate.format(formatter) + " 04:00:00");

        when(soalRepository.findById(uuid)).thenReturn(Optional.of(mockSoal));
        lenient().when(tingkatRepository.findTingkatByTingkat(soalRequest.getTingkatSoal())).thenReturn(tingkat);
        lenient().when(jurusanRepository.findJurusanByJurusan(null)).thenReturn(null);
        when(soalRepository.save(Mockito.any(Soal.class))).thenReturn(mockSoal);

        ResponseWithMessageAndData<SoalDto> response = soalService.updateSoal(stringId, soalRequest);

        assertNotNull(response);
    }

    @Test
    void soalServiceDeleteSoalReturnResponseWithMessage(){
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        Tingkat tingkat = new Tingkat();
        tingkat.setId(UUID.randomUUID());
        tingkat.setTingkat("X");

        String stringId = "f823ba29-b657-4516-bc2e-e9ef45333a5e";

        UUID uuid = UUID.fromString(stringId);

        ConvertUtils convertUtils = new ConvertUtils();
        Soal mockSoal = new Soal();
        mockSoal.setId(uuid);
        mockSoal.setNamaSoal("Soal Test");
        mockSoal.setTingkat(tingkat);
        mockSoal.setAcakSoal(AcakSoal.ACAK);
        mockSoal.setButirSoal(100);
        mockSoal.setWaktuMulaiSoal(convertUtils.convertStringToDatetimeOrTime(currentDate.format(formatter) + " 05:00:00"));
        mockSoal.setDurasiSoal(convertUtils.convertStringToDatetimeOrTime("03:00:00"));
        mockSoal.setWaktuSelesaiSoal(convertUtils.convertStringToDatetimeOrTime(currentDate.format(formatter) + " 04:00:00"));

        when(soalRepository.findById(uuid)).thenReturn(Optional.of(mockSoal));

        assertAll(() -> soalService.deleteSoal(stringId));
    }
}