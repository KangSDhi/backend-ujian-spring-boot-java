package dev.kangsdhi.backendujianspringbootjava.services.Implementation;

import dev.kangsdhi.backendujianspringbootjava.dto.data.TingkatDto;
import dev.kangsdhi.backendujianspringbootjava.dto.request.TingkatCreateRequest;
import dev.kangsdhi.backendujianspringbootjava.dto.request.TingkatEditRequest;
import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseWithMessage;
import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseWithMessageAndData;
import dev.kangsdhi.backendujianspringbootjava.entities.Tingkat;
import dev.kangsdhi.backendujianspringbootjava.repository.TingkatRepository;
import dev.kangsdhi.backendujianspringbootjava.services.TingkatService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TingkatServiceImplementation implements TingkatService {

    @Autowired
    private TingkatRepository tingkatRepository;

    @Override
    public ResponseWithMessageAndData<List<TingkatDto>> allTingkat() {
        List<TingkatDto> tingkatDtoList = tingkatRepository.findAll().stream()
                .map(this::mapToTingkatDto)
                .collect(Collectors.toList());
        return createResponse(HttpStatus.OK.value(), "Berhasil Mengambil Data Tingkat", tingkatDtoList);
    }

    @Override
    public ResponseWithMessageAndData<TingkatDto> findTingkatById(String id) {
        UUID tingkatId = UUID.fromString(id);
        Tingkat existingTingkat = tingkatRepository.findById(tingkatId)
                .orElseThrow(() -> new EntityNotFoundException("Tingkat Tidak Ditemukan!"));
        return createResponse(HttpStatus.OK.value(), "Berhasil Menemukan Data!", mapToTingkatDto(existingTingkat));
    }

    @Override
    public ResponseWithMessageAndData<TingkatDto> storeTingkat(TingkatCreateRequest tingkatCreateRequest) {
        Tingkat tingkat = prepareTingkatEntity(new Tingkat(), tingkatCreateRequest.getNama_tingkat());
        Tingkat tingkatStore = tingkatRepository.save(tingkat);

        return createResponse(HttpStatus.CREATED.value(), "Berhasil Membuat Tingkat", mapToTingkatDto(tingkatStore));
    }

    @Override
    public ResponseWithMessageAndData<TingkatDto> updateTingkat(TingkatEditRequest tingkatEditRequest) {
        UUID tingkatId = UUID.fromString(tingkatEditRequest.getId());
        Tingkat existingTingkat = tingkatRepository.findById(tingkatId)
                .orElseThrow(() -> new EntityNotFoundException("Tingkat Tidak Ditemukan!"));

        Tingkat updatedTingkat = prepareTingkatEntity(existingTingkat, tingkatEditRequest.getNama_tingkat());
        Tingkat savedTingkat = tingkatRepository.save(updatedTingkat);

        return createResponse(HttpStatus.CREATED.value(), "Berhasil Memperbarui Tingkat", mapToTingkatDto(savedTingkat));
    }

    @Override
    public ResponseWithMessage destroyTingkat(String idTingkat) {
        UUID tingkatId = UUID.fromString(idTingkat);
        Tingkat tingkat = tingkatRepository.findById(tingkatId).orElseThrow(() -> new EntityNotFoundException("Tingkat Tidak Ditemukan!"));

        tingkatRepository.delete(tingkat);
        return createResponse(HttpStatus.OK.value(), "Berhasil Menghapus Tingkat");
    }

    private TingkatDto mapToTingkatDto(Tingkat tingkat){
        TingkatDto tingkatDto = new TingkatDto();
        tingkatDto.setId(tingkat.getId().toString());
        tingkatDto.setNama_tingkat(tingkat.getTingkat());
        tingkatDto.setCreated_at(tingkat.getCreatedAt());
        tingkatDto.setUpdated_at(tingkat.getUpdatedAt());
        return tingkatDto;
    }

    private Tingkat prepareTingkatEntity(Tingkat tingkat, String namaTingkat) {
        tingkat.setTingkat(namaTingkat);
        return tingkat;
    }

    private <T> ResponseWithMessageAndData<T> createResponse(int httpCode, String message, T data) {
        ResponseWithMessageAndData<T> response = new ResponseWithMessageAndData<>();
        response.setHttpCode(httpCode);
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
