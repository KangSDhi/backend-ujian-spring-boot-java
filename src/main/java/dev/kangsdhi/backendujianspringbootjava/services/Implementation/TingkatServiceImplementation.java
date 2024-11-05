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
    public ResponseWithMessageAndData<List<TingkatDto>> getAllTingkat() {
        List<Tingkat> tingkatList = tingkatRepository.findAll();
        List<TingkatDto> tingkatDtoList = tingkatList.stream().map(tingkat -> {
            TingkatDto tingkatDto = new TingkatDto();
            tingkatDto.setId(tingkat.getId().toString());
            tingkatDto.setNamaTingkat(tingkat.getTingkat());
            tingkatDto.setCreatedAt(tingkat.getCreatedAt());
            tingkatDto.setUpdatedAt(tingkat.getUpdatedAt());
            return tingkatDto;
        }).toList();
        ResponseWithMessageAndData<List<TingkatDto>> response = new ResponseWithMessageAndData<>();
        response.setHttpCode(HttpStatus.OK.value());
        response.setMessage("Berhasil Mengambil Data Tingkat");
        response.setData(tingkatDtoList);
        return response;
    }

    @Override
    public ResponseWithMessageAndData<TingkatDto> createTingkat(TingkatCreateRequest tingkatCreateRequest) {

        Tingkat tingkat = prepareTingkatEntity(new Tingkat(), tingkatCreateRequest.getNamaTingkat());
        Tingkat tingkatStore = tingkatRepository.save(tingkat);

        TingkatDto tingkatDto = new TingkatDto();
        tingkatDto.setId(tingkatStore.getId().toString());
        tingkatDto.setNamaTingkat(tingkatStore.getTingkat());
        tingkatDto.setCreatedAt(tingkatStore.getCreatedAt());
        tingkatDto.setUpdatedAt(tingkatStore.getUpdatedAt());

        ResponseWithMessageAndData<TingkatDto> response = new ResponseWithMessageAndData<>();
        response.setHttpCode(HttpStatus.CREATED.value());
        response.setMessage("Berhasil Membuat Tingkat");
        response.setData(tingkatDto);

        return response;
    }

    @Override
    public ResponseWithMessageAndData<TingkatDto> updateTingkat(TingkatEditRequest tingkatEditRequest) {

        UUID tingkatId = UUID.fromString(tingkatEditRequest.getIdTingkat());
        Tingkat findTingkat = tingkatRepository.findById(tingkatId).orElseThrow(() -> new EntityNotFoundException("Tingkat id: " + tingkatId + " Tidak Ditemukan!"));
        Tingkat editTingkat = prepareTingkatEntity(findTingkat, tingkatEditRequest.getNamaTingkat());
        Tingkat tingkatUpdate = tingkatRepository.save(editTingkat);

        TingkatDto tingkatDto = new TingkatDto();
        tingkatDto.setId(tingkatUpdate.getId().toString());
        tingkatDto.setNamaTingkat(tingkatUpdate.getTingkat());
        tingkatDto.setCreatedAt(tingkatUpdate.getCreatedAt());
        tingkatDto.setUpdatedAt(tingkatUpdate.getUpdatedAt());

        ResponseWithMessageAndData<TingkatDto> response = new ResponseWithMessageAndData<>();
        response.setHttpCode(HttpStatus.CREATED.value());
        response.setMessage("Berhasil Memperbarui Tingkat");
        response.setData(tingkatDto);

        return response;
    }

    @Override
    public ResponseWithMessage deleteTingkat(String idTingkat) {
        UUID tingkatId = UUID.fromString(idTingkat);
        Tingkat tingkat = tingkatRepository.findById(tingkatId).orElseThrow(() -> new EntityNotFoundException("Tingkat Tidak Ditemukan!"));
        tingkatRepository.delete(tingkat);

        ResponseWithMessage response = new ResponseWithMessage();
        response.setHttpCode(HttpStatus.OK.value());
        response.setMessage("Berhasil Menghapus Tingkat");
        return response;
    }

    private Tingkat prepareTingkatEntity(Tingkat tingkat, String namaTingkat) {
        tingkat.setTingkat(namaTingkat);
        return tingkat;
    }
}
