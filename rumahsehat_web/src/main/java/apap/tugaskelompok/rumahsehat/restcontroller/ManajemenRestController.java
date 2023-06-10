package apap.tugaskelompok.rumahsehat.restcontroller;

import apap.tugaskelompok.rumahsehat.model.ApotekerModel;
import apap.tugaskelompok.rumahsehat.model.DokterModel;
import apap.tugaskelompok.rumahsehat.model.PasienModel;
import apap.tugaskelompok.rumahsehat.model.UserModel;
import apap.tugaskelompok.rumahsehat.service.ApotekerService;
import apap.tugaskelompok.rumahsehat.service.DokterService;
import apap.tugaskelompok.rumahsehat.service.PasienService;
import apap.tugaskelompok.rumahsehat.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@Slf4j
@RestController
@RequestMapping("/api/v1/manajemen")
@CrossOrigin
public class ManajemenRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private DokterService dokterService;

    @Autowired
    private ApotekerService apotekerService;

    @Autowired
    private PasienService pasienService;

    @RequestMapping(path = "/apoteker", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<ApotekerModel> manajemenApoteker (){
        return apotekerService.getListApoteker();
    }

    @RequestMapping(path = "/dokter", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<DokterModel> manajemenDokter (){
        return dokterService.getListDokter();
    }

    @RequestMapping(path = "/pasien", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<PasienModel> manajemenPasien (){
        return pasienService.getListPasien();
    }

}
