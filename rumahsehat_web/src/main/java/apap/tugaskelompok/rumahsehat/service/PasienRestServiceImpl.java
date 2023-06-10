package apap.tugaskelompok.rumahsehat.service;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import apap.tugaskelompok.rumahsehat.model.PasienModel;
import apap.tugaskelompok.rumahsehat.repository.PasienDb;

import java.util.NoSuchElementException;

@Service
@Transactional
public class PasienRestServiceImpl implements PasienRestService {
    @Autowired
    private PasienDb pasienDb;

    @Override
    public String encrypt(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        return hashedPassword;
    }

    @Override
    public PasienModel createPasien(PasienModel pasien) {
        pasien.setRole("Pasien");
        String pass = encrypt(pasien.getPassword());
        pasien.setPassword(pass);
        return pasienDb.save(pasien);
    }

    @Override
    public PasienModel getPasienByUsername(String username) {
        PasienModel pasien = pasienDb.findByUsername(username);
        if (pasien == null) {
            throw new NoSuchElementException();
        }
        return pasien;
    }

    @Override
    public PasienModel topUpSaldo(String username, PasienModel updatedPasien) {
        PasienModel pasien = getPasienByUsername(username);
        pasien.setSaldo(updatedPasien.getSaldo());
//        Integer saldoAwal = pasien.getSaldo();
//        Integer saldoTambah = updatedPasien.getSaldo();
//        pasien.setSaldo(saldoAwal + saldoTambah);
        return pasienDb.save(pasien);
    }
}
