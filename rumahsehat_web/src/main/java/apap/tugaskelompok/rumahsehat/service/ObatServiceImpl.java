package apap.tugaskelompok.rumahsehat.service;

import apap.tugaskelompok.rumahsehat.model.ObatModel;
import apap.tugaskelompok.rumahsehat.repository.ObatDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ObatServiceImpl implements ObatService {
    @Autowired
    ObatDb obatDb;

    @Override
    public List<ObatModel> findAll() {
        return obatDb.findAllByOrderByNamaObat();
    }

    @Override
    public ObatModel findById(String id) {
        Optional<ObatModel> obat = obatDb.findById(id);
        if (obat.isPresent()) {
            return obat.get();
        } else {
            return null;
        }
    }

    @Override
    public ObatModel updateObat(ObatModel obat) {
        obatDb.save(obat);
        return obat;
    }
}
