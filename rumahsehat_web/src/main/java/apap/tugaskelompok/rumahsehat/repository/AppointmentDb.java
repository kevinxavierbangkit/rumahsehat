package apap.tugaskelompok.rumahsehat.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import apap.tugaskelompok.rumahsehat.model.AppointmentModel;
import apap.tugaskelompok.rumahsehat.model.DokterModel;
import apap.tugaskelompok.rumahsehat.model.PasienModel;

@Repository
public interface AppointmentDb extends JpaRepository<AppointmentModel, String>{
    List<AppointmentModel> findByPasien(PasienModel pasien);
    List<AppointmentModel> findByDokter(DokterModel dokter);

    Optional<AppointmentModel> findByKode(String kode);
    
}
