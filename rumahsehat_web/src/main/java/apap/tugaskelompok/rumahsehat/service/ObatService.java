package apap.tugaskelompok.rumahsehat.service;

import apap.tugaskelompok.rumahsehat.model.ObatModel;
import java.util.List;

public interface ObatService {
    List<ObatModel> findAll();
    ObatModel findById(String id);
    ObatModel updateObat(ObatModel obat);
}
