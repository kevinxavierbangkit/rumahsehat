package apap.tugaskelompok.rumahsehat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import apap.tugaskelompok.rumahsehat.model.AppointmentModel;
import apap.tugaskelompok.rumahsehat.model.DokterModel;
import apap.tugaskelompok.rumahsehat.model.ResepModel;
import apap.tugaskelompok.rumahsehat.model.TagihanModel;
import apap.tugaskelompok.rumahsehat.model.UserModel;
import apap.tugaskelompok.rumahsehat.service.AppointmentService;
import apap.tugaskelompok.rumahsehat.service.DokterService;
import apap.tugaskelompok.rumahsehat.service.UserService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(path = "/appointment")
public class AppointmentController {
    @Autowired
    AppointmentService appointmentService;

    @Autowired
    UserService userService;

    @Autowired
    DokterService dokterService;

    @GetMapping("viewall")
    public String listAppointment(Model model){
        UserModel user = userService.getUserByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName());
        
        if (user.getRole().equals("Admin")) {
            List<AppointmentModel> listAppointment = appointmentService.getListAppointment();
            model.addAttribute("listAppointment", listAppointment);

        }else {            
            DokterModel dokter = dokterService.getDokterById(user.getUuid());

            List<AppointmentModel> listAppointment = appointmentService.getListAppointment(dokter);
            model.addAttribute("listAppointment", listAppointment);

        }
        return "appointment/viewall-appointment";
    }


    @GetMapping("/view/{appointment_kode}")
    public String viewDetailAppointment(@PathVariable String appointment_kode, Model model){
        AppointmentModel appointment = appointmentService.getAppointmentByKode(appointment_kode);

        ResepModel resep = appointment.getResep();

        UserModel user = userService.getUserByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName());

        Boolean userAppointment = appointmentService.checkUserAppointment(user, appointment);
        
        if (!userAppointment) {
            return "redirect:/home";
        }

        model.addAttribute("appointment", appointment);
        model.addAttribute("resep", resep);

        return "appointment/view-appointment";

    }

    @GetMapping("/isDone/{appointment_kode}")
    public String doneAppointment(@PathVariable String appointment_kode, Model model, RedirectAttributes redirectAttrs){
        AppointmentModel appointment = appointmentService.getAppointmentByKode(appointment_kode);

        // check apakah resep sudah dikonfirmasi atau belum
        if (appointment.getResep() != null) {
            if (appointment.getResep().getIsDone() == false) {
                redirectAttrs.addFlashAttribute("error", "Tidak dapat menyelesaikan appointment karena resep belum dikonfirmasi oleh apoteker");
                return "redirect:/appointment/view/" + appointment_kode;
            } 
        }


        appointmentService.doneAppointment(appointment);

        TagihanModel tagihan = appointment.getTagihan();
//        tagihanService.updateTagihan(tagihan);

        return "redirect:/appointment/view/" + appointment_kode;
    }

}
