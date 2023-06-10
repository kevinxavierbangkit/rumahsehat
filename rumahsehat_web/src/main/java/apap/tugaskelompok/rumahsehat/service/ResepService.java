package apap.tugaskelompok.rumahsehat.service;

import java.util.List;
import apap.tugaskelompok.rumahsehat.model.ResepModel;

public interface ResepService {
    void createResep(ResepModel resep);
    List<ResepModel> getListResep();
    ResepModel getResepById(Long id);
    ResepModel updateResep(ResepModel resep);
    void deleteResep(ResepModel resep);
}
