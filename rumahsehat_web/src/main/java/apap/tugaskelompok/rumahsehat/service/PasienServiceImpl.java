package apap.tugaskelompok.rumahsehat.service;

import apap.tugaskelompok.rumahsehat.model.PasienModel;
import apap.tugaskelompok.rumahsehat.repository.PasienDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PasienServiceImpl implements PasienService{

    @Autowired
    PasienDb pasienDb;

    @Override
    public PasienModel getPasienById(String uuid){
        PasienModel pasien = pasienDb.findByUuid(uuid);
        return pasien;
    }

    @Override
    public List<PasienModel> getListPasien(){
        return pasienDb.findAll();
    }

    @Override
    public PasienModel addPasien(PasienModel pasien){
        return pasienDb.save(pasien);
    }
}
