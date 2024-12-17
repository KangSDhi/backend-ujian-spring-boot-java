package dev.kangsdhi.backendujianspringbootjava.services.Implementation;

import dev.kangsdhi.backendujianspringbootjava.dto.data.BankSoalDto;
import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseWithMessageAndData;
import dev.kangsdhi.backendujianspringbootjava.entities.BankSoal;
import dev.kangsdhi.backendujianspringbootjava.repository.BankSoalRepository;
import dev.kangsdhi.backendujianspringbootjava.services.BankSoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
public class BankSoalServiceImplementation implements BankSoalService {

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
}
