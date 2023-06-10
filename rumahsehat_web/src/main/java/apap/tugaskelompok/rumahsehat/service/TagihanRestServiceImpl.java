package apap.tugaskelompok.rumahsehat.service;

import apap.tugaskelompok.rumahsehat.model.AppointmentModel;
import apap.tugaskelompok.rumahsehat.model.PasienModel;
import apap.tugaskelompok.rumahsehat.model.TagihanModel;
import apap.tugaskelompok.rumahsehat.repository.PasienDb;
import apap.tugaskelompok.rumahsehat.repository.TagihanDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TagihanRestServiceImpl implements TagihanRestService {

    @Autowired
    TagihanDb tagihanDb;

    @Autowired
    PasienDb pasienDb;

    @Override
    public List<TagihanModel> getListTagihan() {
        return tagihanDb.findAll();
    }

    @Override
    public List<TagihanModel> getTagihanPasien(PasienModel pasien){
        List<TagihanModel> res = new ArrayList<>();
        List<AppointmentModel> apt = pasien.getListAppointment();
        for(AppointmentModel a : apt){
            res.add(a.getTagihan());
        }
        return res;
    }

    @Override
    public TagihanModel getTagihanByKode(String kode){
        return tagihanDb.findByKode(kode);
    }

    @Override
    public TagihanModel bayarTagihan(PasienModel pasien, TagihanModel tagihan){
        int sisaSaldo = pasien.getSaldo() - tagihan.getJumlahTagihan();
        pasien.setSaldo(sisaSaldo);
        pasienDb.save(pasien);
        tagihan.setIsPaid(true);
        tagihan.setTanggalBayar(LocalDateTime.now());
        tagihanDb.save(tagihan);
        return tagihan;
    }
}
