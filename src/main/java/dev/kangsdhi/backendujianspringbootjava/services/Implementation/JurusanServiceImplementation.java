package dev.kangsdhi.backendujianspringbootjava.services.Implementation;

import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseWithMessageAndData;
import dev.kangsdhi.backendujianspringbootjava.entities.Jurusan;
import dev.kangsdhi.backendujianspringbootjava.repository.JurusanRepository;
import dev.kangsdhi.backendujianspringbootjava.services.JurusanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JurusanServiceImplementation implements JurusanService {

    @Autowired
    private JurusanRepository jurusanRepository;

    @Override
    public ResponseWithMessageAndData<List<Jurusan>> getAllJurusan() {
        List<Jurusan> jurusanList = jurusanRepository.findAll();
        ResponseWithMessageAndData<List<Jurusan>> response = new ResponseWithMessageAndData<>();
        response.setHttpCode(HttpStatus.OK.value());
        response.setMessage("Berhasil Mengambil Data Jurusan");
        response.setData(jurusanList);
        return response;
    }
}
