package apap.tugaskelompok.rumahsehat.service;

import apap.tugaskelompok.rumahsehat.model.DokterModel;
import apap.tugaskelompok.rumahsehat.repository.DokterDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class DokterServiceImpl implements DokterService{

    @Autowired
    private DokterDb dokterDb;

    @Override
    public DokterModel getDokterById(String uuid){
        DokterModel dokter = dokterDb.findByUuid(uuid);
        return dokter;
    }

    @Override
    public List<DokterModel> getListDokter(){
        return dokterDb.findAll();
    }

    @Override
    public DokterModel addDokter(DokterModel dokter){
        return dokterDb.save(dokter);
    }
}
