package apap.tugaskelompok.rumahsehat.service;

import apap.tugaskelompok.rumahsehat.model.DokterModel;

import java.util.List;

public interface DokterService {

    DokterModel getDokterById(String uuid);

    List<DokterModel> getListDokter();

    DokterModel addDokter(DokterModel dokter);
}
