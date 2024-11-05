package dev.kangsdhi.backendujianspringbootjava.services.Implementation;

import dev.kangsdhi.backendujianspringbootjava.dto.data.JurusanDto;
import dev.kangsdhi.backendujianspringbootjava.dto.request.JurusanCreateRequest;
import dev.kangsdhi.backendujianspringbootjava.dto.request.JurusanEditRequest;
import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseWithMessage;
import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseWithMessageAndData;
import dev.kangsdhi.backendujianspringbootjava.entities.Jurusan;
import dev.kangsdhi.backendujianspringbootjava.repository.JurusanRepository;
import dev.kangsdhi.backendujianspringbootjava.services.JurusanService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class JurusanServiceImplementation implements JurusanService {

    @Autowired
    private JurusanRepository jurusanRepository;

    @Override
    public ResponseWithMessageAndData<List<JurusanDto>> allJurusan() {
        List<JurusanDto> jurusanDtoList = jurusanRepository.findAll().stream()
                .map(this::mapToJurusanDto)
                .collect(Collectors.toList());
        return createResponse(HttpStatus.OK.value(), "Berhasil Mengambil Data Jurusan", jurusanDtoList);
    }

    @Override
    public ResponseWithMessageAndData<JurusanDto> findJurusanById(String idJurusan) {
        UUID jurusanId = UUID.fromString(idJurusan);
        Jurusan existingJurusan = jurusanRepository.findById(jurusanId)
                .orElseThrow(() -> new EntityNotFoundException("Jurusan Tidak Ditemukan!"));
        return createResponse(HttpStatus.OK.value(), "Berhasil Menemukan Jurusan!", mapToJurusanDto(existingJurusan));
    }

    @Override
    public ResponseWithMessageAndData<JurusanDto> storeJurusan(JurusanCreateRequest jurusanCreateRequest) {
        Jurusan jurusan = prepareJurusanEntity(new Jurusan(), jurusanCreateRequest.getNamaJurusan());
        Jurusan savedJurusan = jurusanRepository.save(jurusan);

        return createResponse(HttpStatus.CREATED.value(), "Berhasil Membuat Jurusan", mapToJurusanDto(savedJurusan));
    }

    @Override
    public ResponseWithMessageAndData<JurusanDto> updateJurusan(JurusanEditRequest jurusanEditRequest) {
        UUID jurusanId = UUID.fromString(jurusanEditRequest.getIdJurusan());
        Jurusan existingJurusan = jurusanRepository.findById(jurusanId)
                .orElseThrow(() -> new EntityNotFoundException("Jurusan Tidak Ditemukan!"));

        Jurusan updatedJurusan = prepareJurusanEntity(existingJurusan, jurusanEditRequest.getNamaJurusan());
        Jurusan savedJurusan = jurusanRepository.save(updatedJurusan);

        return createResponse(HttpStatus.CREATED.value(), "Berhasil Memperbarui Jurusan", mapToJurusanDto(savedJurusan));
    }

    @Override
    public ResponseWithMessage destroyJurusan(String idJurusan) {
        UUID jurusanId = UUID.fromString(idJurusan);
        Jurusan jurusan = jurusanRepository.findById(jurusanId)
                .orElseThrow(() -> new EntityNotFoundException("Jurusan Tidak Ditemukan!"));

        jurusanRepository.delete(jurusan);
        return createResponse(HttpStatus.OK.value(), "Berhasil Menghapus Jurusan");
    }

    private JurusanDto mapToJurusanDto(Jurusan jurusan) {
        JurusanDto jurusanDto = new JurusanDto();
        jurusanDto.setId(jurusan.getId().toString());
        jurusanDto.setNamaJurusan(jurusan.getJurusan());
        jurusanDto.setCreatedAt(jurusan.getCreatedAt());
        jurusanDto.setUpdatedAt(jurusan.getUpdatedAt());
        return jurusanDto;
    }

    private Jurusan prepareJurusanEntity(Jurusan jurusan, String namaJurusan) {
        jurusan.setJurusan(namaJurusan);
        return jurusan;
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
        response.setHttpCode(httpCode);
        response.setMessage(message);
        return response;
    }
}
