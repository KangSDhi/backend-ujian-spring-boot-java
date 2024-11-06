package dev.kangsdhi.backendujianspringbootjava.database.seeder;

import dev.kangsdhi.backendujianspringbootjava.entities.Jurusan;
import dev.kangsdhi.backendujianspringbootjava.entities.Kelas;
import dev.kangsdhi.backendujianspringbootjava.entities.Tingkat;
import dev.kangsdhi.backendujianspringbootjava.repository.JurusanRepository;
import dev.kangsdhi.backendujianspringbootjava.repository.KelasRepository;
import dev.kangsdhi.backendujianspringbootjava.repository.TingkatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class KelasSeeder {

    @Autowired
    TingkatRepository tingkatRepository;

    @Autowired
    JurusanRepository jurusanRepository;

    @Autowired
    KelasRepository kelasRepository;

    public void seedKelas() {
        int countDataKelas = kelasRepository.findAll().toArray().length;
        if (countDataKelas == 0){

            List<Tingkat> tingkats = tingkatRepository.findAll();

            String jurusan = "";

            for (Tingkat itemTingkat : tingkats){
                List<Jurusan> jurusans = jurusanRepository.findAll();

                for (Jurusan itemJurusan : jurusans){

                    if (itemJurusan.getJurusan().equals("Teknik Konstruksi dan Properti")){
                        jurusan = "TKP";
                    }

                    if (itemJurusan.getJurusan().equals("Desain Pemodelan dan Informasi Bangunan")){
                        jurusan = "DPIB";
                    }

                    if (itemJurusan.getJurusan().equals("Kimia Industri")){
                        jurusan = "KI";
                    }

                    if (itemJurusan.getJurusan().equals("Teknik Geomatika")){
                        jurusan = "GMT";
                    }

                    if (itemJurusan.getJurusan().equals("Teknik Installasi Tenaga Listrik")){
                        jurusan = "TITL";
                    }

                    if (itemJurusan.getJurusan().equals("Teknik Komputer dan Jaringan")){
                        jurusan = "TKJ";
                    }

                    if (itemJurusan.getJurusan().equals("Teknik Mekatronika")) {
                        jurusan = "MEKA";
                    }

                    if (itemJurusan.getJurusan().equals("Teknik Kendaraan Ringan Otomotif")) {
                        jurusan = "TKRO";
                    }

                    if (itemJurusan.getJurusan().equals("Teknik Pengelasan")){
                        jurusan = "TP";
                    }

                    if (itemJurusan.getJurusan().equals("Teknik Elektronika Industri")){
                        jurusan = "TEI";
                    }

                    if (jurusan.equals("TKRO")){
                        for (int i = 0; i < 3; i++) {
                            Kelas kelasBaru = new Kelas();
                            kelasBaru.setKelas(itemTingkat.getTingkat()+"-"+jurusan+"-"+String.valueOf(i+1));
                            kelasBaru.setTingkat(itemTingkat);
                            kelasBaru.setJurusan(itemJurusan);
                            kelasRepository.save(kelasBaru);
                            System.out.println("Membuat Data Kelas Baru : "+itemTingkat.getTingkat()+"-"+jurusan+"-"+String.valueOf(i+1)+" ✅");
                        }
                    }else if (jurusan.equals("KI") || jurusan.equals("MEKA") || jurusan.equals("TP")){
                        Kelas kelasBaru = new Kelas();
                        kelasBaru.setKelas(itemTingkat.getTingkat()+"-"+jurusan);
                        kelasBaru.setTingkat(itemTingkat);
                        kelasBaru.setJurusan(itemJurusan);
                        kelasRepository.save(kelasBaru);
                        System.out.println("Membuat Data Kelas Baru : "+itemTingkat.getTingkat()+"-"+jurusan+" ✅");
                    } else {
                        for (int i = 0; i < 2; i++) {
                            Kelas kelasBaru = new Kelas();
                            kelasBaru.setKelas(itemTingkat.getTingkat()+"-"+jurusan+"-"+String.valueOf(i+1));
                            kelasBaru.setTingkat(itemTingkat);
                            kelasBaru.setJurusan(itemJurusan);
                            kelasRepository.save(kelasBaru);
                            System.out.println("Membuat Data Kelas Baru : "+itemTingkat.getTingkat()+"-"+jurusan+"-"+String.valueOf(i+1)+" ✅");
                        }
                    }
                }
            }
        }
    }
}
