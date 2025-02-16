package dev.kangsdhi.backendujianspringbootjava.services.Implementation;

import dev.kangsdhi.backendujianspringbootjava.dto.data.BankSoalDto;
import dev.kangsdhi.backendujianspringbootjava.dto.request.BankSoalCreateRequest;
import dev.kangsdhi.backendujianspringbootjava.dto.request.BankSoalEditRequest;
import dev.kangsdhi.backendujianspringbootjava.dto.request.BankSoalRequest;
import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseWithMessage;
import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseWithMessageAndData;
import dev.kangsdhi.backendujianspringbootjava.entities.BankSoal;
import dev.kangsdhi.backendujianspringbootjava.entities.Soal;
import dev.kangsdhi.backendujianspringbootjava.repository.BankSoalRepository;
import dev.kangsdhi.backendujianspringbootjava.repository.SoalRepository;
import dev.kangsdhi.backendujianspringbootjava.services.BankSoalService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
public class BankSoalServiceImplementation implements BankSoalService {

    @Autowired
    private SoalRepository soalRepository;

    @Autowired
    private BankSoalRepository bankSoalRepository;

    @Override
    public ResponseWithMessageAndData<List<BankSoalDto>> findBankSoalBySoalId(String soalId) {
        UUID idSoal = UUID.fromString(soalId);
        List<BankSoalDto> bankSoalDtoList = bankSoalRepository.findAllBySoalId(idSoal).stream()
                .sorted(Comparator.comparing(BankSoal::getCreatedAt))
                .map(this::mapToBankSoalDto)
                .toList();
        return createResponse(HttpStatus.OK.value(), "Berhasil Mengambil Data Bank Soal!", bankSoalDtoList);
    }

    @Override
    public ResponseWithMessageAndData<BankSoalDto> createBankSoal(BankSoalCreateRequest bankSoalCreateRequest) {
        UUID idSoal = UUID.fromString(bankSoalCreateRequest.getId_soal());
        Soal soal = soalRepository.findById(idSoal)
                .orElseThrow(() -> new EntityNotFoundException("Soal Tidak Ditemukan!"));

        BankSoal newBankSoal = prepareBankSoalEntity(new BankSoal(), bankSoalCreateRequest, soal);
        BankSoal bankSoalStored = bankSoalRepository.save(newBankSoal);

        BankSoalDto bankSoalDto = mapToBankSoalDto(bankSoalStored);
        return createResponse(HttpStatus.CREATED.value(), "Berhasil Membuat Bank Soal!", bankSoalDto);
    }

    @Override
    public ResponseWithMessageAndData<BankSoalDto> updateBankSoal(String idBankSoal, BankSoalEditRequest bankSoalEditRequest) {
        UUID uuidBankSoal = UUID.fromString(idBankSoal);
        UUID idSoal = UUID.fromString(bankSoalEditRequest.getId_soal());
        Soal soal = soalRepository.findById(idSoal)
                .orElseThrow(() -> new EntityNotFoundException("Soal Tidak Ditemukan!"));
        BankSoal bankSoal = bankSoalRepository.findById(uuidBankSoal)
                .orElseThrow(() -> new EntityNotFoundException("Bank Soal Tidak Ditemukan!"));

        BankSoal editBankSoal = prepareBankSoalEntity(bankSoal, bankSoalEditRequest, soal);
        BankSoal bankSoalStored = bankSoalRepository.save(editBankSoal);

        BankSoalDto bankSoalDto = mapToBankSoalDto(bankSoalStored);
        return createResponse(HttpStatus.CREATED.value(), "Berhasil Memperbarui Bank Soal!", bankSoalDto);
    }

    @Override
    public ResponseWithMessage deleteBankSoal(String idBankSoal) {
        UUID bankSoalId = UUID.fromString(idBankSoal);
        BankSoal bankSoal = bankSoalRepository.findById(bankSoalId)
                .orElseThrow(() -> new EntityNotFoundException("Bank Soal Tidak Ditemukan!"));
        bankSoalRepository.delete(bankSoal);
        return createResponse(HttpStatus.OK.value(), "Berhasil Menghapus Bank Soal!");
    }

    private BankSoal prepareBankSoalEntity(BankSoal bankSoal, BankSoalRequest bankSoalRequest, Soal soal) {
        bankSoal.setPertanyaanBankSoal(bankSoalRequest.getPertanyaan());
        bankSoal.setGambarPertanyaanBankSoal(bankSoalRequest.getGambar_pertanyaan());
        bankSoal.setSoal(soal);
        bankSoal.setPilihanA(bankSoalRequest.getPilihan_a());
        bankSoal.setPilihanB(bankSoalRequest.getPilihan_b());
        bankSoal.setPilihanC(bankSoalRequest.getPilihan_c());
        bankSoal.setPilihanD(bankSoalRequest.getPilihan_d());
        bankSoal.setPilihanE(bankSoalRequest.getPilihan_e());
        bankSoal.setNilaiA(bankSoalRequest.getNilai_a());
        bankSoal.setNilaiB(bankSoalRequest.getNilai_b());
        bankSoal.setNilaiC(bankSoalRequest.getNilai_c());
        bankSoal.setNilaiD(bankSoalRequest.getNilai_d());
        bankSoal.setNilaiE(bankSoalRequest.getNilai_e());
        return bankSoal;
    }

    private BankSoalDto mapToBankSoalDto(BankSoal bankSoal) {
        BankSoalDto bankSoalDto = new BankSoalDto();
        bankSoalDto.setId(bankSoal.getId());
        bankSoalDto.setSoal_id(bankSoal.getSoal().getId());
        bankSoalDto.setPertanyaan(bankSoal.getPertanyaanBankSoal());
        bankSoalDto.setGambar_pertanyaan(bankSoal.getGambarPertanyaanBankSoal());
        bankSoalDto.setPilihan_a(bankSoal.getPilihanA());
        bankSoalDto.setPilihan_b(bankSoal.getPilihanB());
        bankSoalDto.setPilihan_c(bankSoal.getPilihanC());
        bankSoalDto.setPilihan_d(bankSoal.getPilihanD());
        bankSoalDto.setPilihan_e(bankSoal.getPilihanE());
        bankSoalDto.setNilai_a(bankSoal.getNilaiA());
        bankSoalDto.setNilai_b(bankSoal.getNilaiB());
        bankSoalDto.setNilai_c(bankSoal.getNilaiC());
        bankSoalDto.setNilai_d(bankSoal.getNilaiD());
        bankSoalDto.setNilai_e(bankSoal.getNilaiE());
        bankSoalDto.setCreated_at(bankSoal.getCreatedAt());
        bankSoalDto.setUpdated_at(bankSoal.getUpdatedAt());
        return bankSoalDto;
    }

    private <T> ResponseWithMessageAndData<T> createResponse(int httpCode, String message, T data) {
        ResponseWithMessageAndData<T> response = new ResponseWithMessageAndData<>();
        response.setHttp_code(httpCode);
        response.setMessage(message);
        response.setData(data);
        return response;
    }

    private ResponseWithMessage createResponse(int httpCode, String message) {
        ResponseWithMessage response = new ResponseWithMessage();
        response.setHttp_code(httpCode);
        response.setMessage(message);
        return response;
    }
}
