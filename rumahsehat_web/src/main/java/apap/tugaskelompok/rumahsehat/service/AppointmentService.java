package apap.tugaskelompok.rumahsehat.service;

import java.util.List;

import apap.tugaskelompok.rumahsehat.model.AppointmentModel;
import apap.tugaskelompok.rumahsehat.model.DokterModel;
import apap.tugaskelompok.rumahsehat.model.PasienModel;
import apap.tugaskelompok.rumahsehat.model.UserModel;

public interface AppointmentService {
    AppointmentModel createAppointment(AppointmentModel appointment);
    List<AppointmentModel> getListAppointment(PasienModel pasien);
    List<AppointmentModel> getListAppointment();
    List<AppointmentModel> getListAppointment(DokterModel dokter);
    AppointmentModel getAppointmentByKode(String kode);
    Boolean checkUserAppointment(UserModel user, AppointmentModel appointment);
    void doneAppointment(AppointmentModel appointment);
}
