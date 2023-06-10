package apap.tugaskelompok.rumahsehat.restcontroller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import apap.tugaskelompok.rumahsehat.model.AppointmentModel;
import apap.tugaskelompok.rumahsehat.model.PasienModel;
import apap.tugaskelompok.rumahsehat.model.UserModel;
import apap.tugaskelompok.rumahsehat.model.DokterModel;
import apap.tugaskelompok.rumahsehat.service.AppointmentService;
import apap.tugaskelompok.rumahsehat.service.PasienService;
import apap.tugaskelompok.rumahsehat.service.UserService;
import apap.tugaskelompok.rumahsehat.service.DokterService;

@RestController
@RequestMapping(path = "api/v1/appointment/")
public class AppointmentRestController {
    
    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasienService pasienService;

    @Autowired
    private DokterService dokterService;

    @PostMapping("/add")
    public AppointmentModel addAppointment (@Valid @RequestBody AppointmentModel appointment, BindingResult bindingResult){
        if (bindingResult.hasFieldErrors()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Request body has invalid type or missing field"
            );
        } else {
            return appointmentService.createAppointment(appointment);
        }

    }

    @GetMapping("/{username}")
    public List<AppointmentModel> listAppointment(@PathVariable String username){
        UserModel user = userService.getUserByUsername(username);  
        PasienModel pasien = pasienService.getPasienById(user.getUuid()); 
        return appointmentService.getListAppointment(pasien);
    }

    @GetMapping("/dokter")
    public List<DokterModel> listDokter(){
        
        return dokterService.getListDokter();
    }

    @GetMapping("/dokter/{uuidDokter}")
    public DokterModel getDokter(@PathVariable String uuidDokter){
        
        return dokterService.getDokterById(uuidDokter);
    }
    @GetMapping("/pasien/{usernamePasien}")
    public PasienModel getPasien(@PathVariable String usernamePasien){
        UserModel user = userService.getUserByUsername(usernamePasien);  
        return pasienService.getPasienById(user.getUuid());
    }
}
 