package apap.tugaskelompok.rumahsehat.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import apap.tugaskelompok.rumahsehat.model.AppointmentModel;
import apap.tugaskelompok.rumahsehat.model.DokterModel;
import apap.tugaskelompok.rumahsehat.model.PasienModel;
import apap.tugaskelompok.rumahsehat.model.UserModel;
import apap.tugaskelompok.rumahsehat.repository.AppointmentDb;

@Service
@Transactional
public class AppointmentServiceImpl implements AppointmentService{
    
    @Autowired
    AppointmentDb appointmentDb;

    @Override
    public AppointmentModel createAppointment(AppointmentModel appointment){
        return appointmentDb.save(appointment);
    }

    @Override
    public List<AppointmentModel> getListAppointment(PasienModel pasien){
        return appointmentDb.findByPasien(pasien);
    }
    
    @Override
    public List<AppointmentModel> getListAppointment(DokterModel dokter){
        return appointmentDb.findByDokter(dokter);
    }

    @Override
    public List<AppointmentModel> getListAppointment(){
        return appointmentDb.findAll();
    }

    @Override
    public AppointmentModel getAppointmentByKode(String kode){
        Optional<AppointmentModel> appointment = appointmentDb.findByKode(kode);
        if (appointment.isPresent()) {
            return appointment.get();            
        } else return null;
    }

    @Override
    public Boolean checkUserAppointment(UserModel user, AppointmentModel appointment){
        if ((appointment.getDokter().getUuid().equals(user.getUuid())) || (appointment.getPasien().getUuid().equals(user.getUuid())) || user.getRole().equals("Admin")) {
            return true;
        } else return null;
    }

    @Override
    public void doneAppointment(AppointmentModel appointment){
        appointment.setIsDone(true);
    }
}
