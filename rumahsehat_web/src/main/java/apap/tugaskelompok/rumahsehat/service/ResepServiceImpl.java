package apap.tugaskelompok.rumahsehat.service;

import apap.tugaskelompok.rumahsehat.model.ResepModel;
import apap.tugaskelompok.rumahsehat.repository.ResepDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ResepServiceImpl implements ResepService{
    @Autowired
    ResepDb resepDb;

    @Override
    public void createResep(ResepModel resep) {
        resepDb.save(resep);
    }

    @Override
    public List<ResepModel> getListResep() {
        return resepDb.findAllByOrderById();
    }

    @Override
    public ResepModel getResepById(Long id) {
        Optional<ResepModel> resep = resepDb.findById(id);
        if(resep.isPresent()) {
            return resep.get();
        } else return null;
    }

    @Override
    public ResepModel updateResep(ResepModel resep) {
        resepDb.save(resep);
        return resep;
    }

    @Override
    public void deleteResep(ResepModel resep) {
        resepDb.delete(resep);
    }
}
