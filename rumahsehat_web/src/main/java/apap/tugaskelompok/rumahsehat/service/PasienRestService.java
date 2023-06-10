package apap.tugaskelompok.rumahsehat.service;

import apap.tugaskelompok.rumahsehat.model.PasienModel;
import org.springframework.http.ResponseEntity;

import java.util.NoSuchElementException;
import java.util.Optional;

public interface PasienRestService {
    String encrypt(String password);
    PasienModel createPasien(PasienModel pasien);

    PasienModel getPasienByUsername(String username);

    PasienModel topUpSaldo(String username, PasienModel pasienUpdate);

}
