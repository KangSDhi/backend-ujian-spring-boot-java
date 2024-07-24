package dev.kangsdhi.backendujianspringbootjava.services.Implementation;

import dev.kangsdhi.backendujianspringbootjava.dto.data.SoalDto;
import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseWithMessageAndData;
import dev.kangsdhi.backendujianspringbootjava.entities.Soal;
import dev.kangsdhi.backendujianspringbootjava.repository.SoalRepository;
import dev.kangsdhi.backendujianspringbootjava.services.SoalService;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SoalServiceImplementation implements SoalService {

    private final SoalRepository soalRepository;

    @Override
    public ResponseWithMessageAndData<SoalDto> soalById(String idSoal) {

        UUID idFromRequest = UUID.fromString(idSoal);

        Soal soal = soalRepository.findById(idFromRequest).orElse(null);

        ResponseWithMessageAndData<SoalDto> responseSoal = new ResponseWithMessageAndData<>();

        if (soal == null){
            responseSoal.setData(null);
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");


        SoalDto soalDto = new SoalDto();
        assert soal != null;
        soalDto.setId(soal.getId().toString());
        soalDto.setNamaSoal(soal.getNamaSoal());
        soalDto.setTingkat(soal.getTingkat().getTingkat());
        if (soal.getJurusan() != null){
            soalDto.setJurusan(soal.getJurusan().getJurusan());
        } else {
            soalDto.setJurusan(null);
        }
        soalDto.setDurasiSoal(soal.getDurasiSoal().toString());
        soalDto.setButirSoal(soal.getButirSoal());
        soalDto.setAcakSoal(soal.getAcakSoal());
        soalDto.setTipeSoal(soal.getTipeSoal());
        soalDto.setWaktuMulaiSoal(dateFormat.format(soal.getWaktuMulaiSoal()));
        soalDto.setWaktuSelesaiSoal(dateFormat.format(soal.getWaktuSelesaiSoal()));

        responseSoal.setData(soalDto);

        return responseSoal;
    }
}
