package apap.tugaskelompok.rumahsehat.service;

import apap.tugaskelompok.rumahsehat.model.DokterModel;
import apap.tugaskelompok.rumahsehat.model.PasienModel;

import java.util.List;

public interface PasienService {

    PasienModel getPasienById(String uuid);

    List<PasienModel> getListPasien();

    PasienModel addPasien(PasienModel pasien);
}
