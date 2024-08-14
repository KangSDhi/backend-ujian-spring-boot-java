package dev.kangsdhi.backendujianspringbootjava.services.Implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.kangsdhi.backendujianspringbootjava.dto.data.HasilUjianDto;
import dev.kangsdhi.backendujianspringbootjava.dto.data.UjianMappingDto;
import dev.kangsdhi.backendujianspringbootjava.dto.request.HasilUjianRequest;
import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseWithMessageAndData;
import dev.kangsdhi.backendujianspringbootjava.entities.*;
import dev.kangsdhi.backendujianspringbootjava.enums.StatusUjian;
import dev.kangsdhi.backendujianspringbootjava.repository.*;
import dev.kangsdhi.backendujianspringbootjava.services.NilaiUjianService;
import dev.kangsdhi.backendujianspringbootjava.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class NilaiUjianServiceImplementation implements NilaiUjianService {

    private final UserService userService;
    private final PenggunaRepository penggunaRepository;
    private final SoalRepository soalRepository;
    private final UjianRepository ujianRepository;
    private final BankSoalRepository bankSoalRepository;
    private final NilaiUjianRepository nilaiUjianRepository;

    @Override
    public ResponseWithMessageAndData<Object> findByIdNilaiUjian(String idNilaiUjian) {
        UUID nilaiId = UUID.fromString(idNilaiUjian);
        NilaiUjian nilaiUjian = nilaiUjianRepository.findById(nilaiId).orElse(null);
        if (nilaiUjian == null) {
            return createResponseWithMessageAndData(HttpStatus.NOT_FOUND, "Nilai Tidak Ditemukan!", null);
        }

        HasilUjianDto hasilUjianDto = new HasilUjianDto();
        hasilUjianDto.setIdNilaiUjian(nilaiUjian.getId());
        hasilUjianDto.setNilaiUjian(nilaiUjian.getNilaiUjian());

        return createResponseWithMessageAndData(HttpStatus.OK, "Nilai Ditemukan!", hasilUjianDto);
    }

    @Override
    public ResponseWithMessageAndData<Object> generateHasilUjian(HasilUjianRequest hasilUjianRequest) {
        UUID soalId = UUID.fromString(hasilUjianRequest.getIdSoal());
        Pengguna pengguna = getCurrentPengguna();
        Soal soal = soalRepository.findById(soalId).orElse(null);
        if (soal == null) {
            return createResponseWithMessageAndData(HttpStatus.NOT_FOUND, "Soal Tidak Ditemukan!", null);
        }

        Ujian ujian = ujianRepository.findBySoalAndPengguna(soal, pengguna).orElse(null);
        if (ujian == null) {
            return createResponseWithMessageAndData(HttpStatus.NOT_FOUND, "Ujian Tidak Ditemukan!", null);
        }

        Optional<NilaiUjian> existingNilaiUjian = nilaiUjianRepository.findByUjian(ujian);

        HasilUjianDto hasilUjianDto = new HasilUjianDto();

        if (existingNilaiUjian.isPresent()) {
            hasilUjianDto.setIdNilaiUjian(existingNilaiUjian.get().getId());
            hasilUjianDto.setNilaiUjian(existingNilaiUjian.get().getNilaiUjian());
            return createResponseWithMessageAndData(HttpStatus.OK, "Berhasil Mengambil Nilai", hasilUjianDto);
        } else {
            ObjectMapper objectMapper = new ObjectMapper();
            List<UjianMappingDto> ujianMappingDtoList;
            try {
                ujianMappingDtoList = Arrays.asList(objectMapper.readValue(ujian.getListJawabanUjian(), UjianMappingDto[].class));
            } catch (Exception e) {
                return createResponseWithMessageAndData(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null);
            }

            AtomicReference<Float> totalNilai = new AtomicReference<>(0f);

            ujianMappingDtoList.forEach(ujianMappingDto -> {
                BankSoal bankSoal = bankSoalRepository.findById(ujianMappingDto.getIdBank()).orElse(null);

                if (bankSoal != null) {
                    if (Objects.equals(bankSoal.getPilihanA(), ujianMappingDto.getJawaban())){
                        totalNilai.updateAndGet(v -> v + bankSoal.getNilaiA());
                    }

                    if (Objects.equals(bankSoal.getPilihanB(), ujianMappingDto.getJawaban())){
                        totalNilai.updateAndGet(v -> v + bankSoal.getNilaiB());
                    }

                    if (Objects.equals(bankSoal.getPilihanC(), ujianMappingDto.getJawaban())){
                        totalNilai.updateAndGet(v -> v + bankSoal.getNilaiC());
                    }

                    if (Objects.equals(bankSoal.getPilihanD(), ujianMappingDto.getJawaban())){
                        totalNilai.updateAndGet(v -> v + bankSoal.getNilaiD());
                    }

                    if (Objects.equals(bankSoal.getPilihanE(), ujianMappingDto.getJawaban())){
                        totalNilai.updateAndGet(v -> v + bankSoal.getNilaiE());
                    }
                }
            });

            NilaiUjian nilaiUjian = new NilaiUjian();
            nilaiUjian.setUjian(ujian);
            nilaiUjian.setNilaiUjian(totalNilai.get());

            NilaiUjian nilaiUjianBaru = nilaiUjianRepository.save(nilaiUjian);

            ujian.setStatusUjian(StatusUjian.SELESAI);
            ujianRepository.save(ujian);

            hasilUjianDto.setIdNilaiUjian(nilaiUjianBaru.getId());
            hasilUjianDto.setNilaiUjian(nilaiUjian.getNilaiUjian());

            return createResponseWithMessageAndData(HttpStatus.CREATED, "Berhasil Membuat Nilai", hasilUjianDto);
        }
    }

    private Pengguna getCurrentPengguna(){
        String namaPengguna = userService.getCurrentUser().getUsername();
        return penggunaRepository.findByNamaPengguna(namaPengguna).orElse(null);
    }

    private ResponseWithMessageAndData<Object> createResponseWithMessageAndData(HttpStatus httpStatus, String message, Object data){
        ResponseWithMessageAndData<Object> responseWithMessageAndData = new ResponseWithMessageAndData<>();
        responseWithMessageAndData.setHttpCode(httpStatus.value());
        responseWithMessageAndData.setMessage(message);
        responseWithMessageAndData.setData(data);
        return responseWithMessageAndData;
    }
}
