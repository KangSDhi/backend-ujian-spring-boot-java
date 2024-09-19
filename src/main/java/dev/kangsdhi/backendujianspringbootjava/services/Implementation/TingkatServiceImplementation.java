package dev.kangsdhi.backendujianspringbootjava.services.Implementation;

import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseWithMessageAndData;
import dev.kangsdhi.backendujianspringbootjava.entities.Tingkat;
import dev.kangsdhi.backendujianspringbootjava.repository.TingkatRepository;
import dev.kangsdhi.backendujianspringbootjava.services.TingkatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TingkatServiceImplementation implements TingkatService {

    @Autowired
    private TingkatRepository tingkatRepository;

    @Override
    public ResponseWithMessageAndData<List<Tingkat>> getAllTingkat() {
        List<Tingkat> tingkatList = tingkatRepository.findAll();
        ResponseWithMessageAndData<List<Tingkat>> response = new ResponseWithMessageAndData<>();
        response.setHttpCode(HttpStatus.OK.value());
        response.setMessage("Berhasil Mengambil Data Tingkat");
        response.setData(tingkatList);
        return response;
    }
}
