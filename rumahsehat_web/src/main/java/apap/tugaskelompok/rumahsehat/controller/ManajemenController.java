package apap.tugaskelompok.rumahsehat.controller;

import apap.tugaskelompok.rumahsehat.model.ApotekerModel;
import apap.tugaskelompok.rumahsehat.model.DokterModel;
import apap.tugaskelompok.rumahsehat.model.PasienModel;
import apap.tugaskelompok.rumahsehat.service.ApotekerService;
import apap.tugaskelompok.rumahsehat.service.DokterService;
import apap.tugaskelompok.rumahsehat.service.PasienService;
import apap.tugaskelompok.rumahsehat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/manajemen")
public class ManajemenController {

    @Autowired
    private UserService userService;

    @Autowired
    private DokterService dokterService;

    @Autowired
    private ApotekerService apotekerService;

    @Autowired
    private PasienService pasienService;


    @GetMapping("")
    public String manajemen(Model model){
        return "users/manajemen";
    }

    @GetMapping("/dokter")
    public String manajemenDokter (Model model){
        List<DokterModel> listDokter = dokterService.getListDokter();
        model.addAttribute("listDokter", listDokter);
        return "users/dt-manajemen-dokter";
    }

    @GetMapping("/apoteker")
    public String manajemenApoteker (Model model){
        List<ApotekerModel> listApoteker = apotekerService.getListApoteker();
        model.addAttribute("listApoteker", listApoteker);
        return "users/dt-manajemen-apoteker";
    }

    @GetMapping("/pasien")
    public String manajemenPasien (Model model){
        List<PasienModel> listPasien = pasienService.getListPasien();
        model.addAttribute("listPasien", listPasien);
        return "users/dt-manajemen-pasien";
    }

    @GetMapping("/dokter/add")
    public String addDokter(Model model){
        model.addAttribute("dokter", new DokterModel());
        return "users/form-add-dokter";
    }

    @PostMapping("/dokter/add")
    public String addDokterSubmit(@ModelAttribute DokterModel dokter, Model model){
        DokterModel dokterNew = new DokterModel();
        dokterNew.setNama(dokter.getNama());
        dokterNew.setRole("Dokter");
        dokterNew.setUsername(dokter.getUsername());
        dokterNew.setPassword(dokter.getPassword());
        dokterNew.setEmail(dokter.getEmail());
        dokterNew.setIsSSO(false);
        dokterNew.setTarif(dokter.getTarif());
        userService.add(dokterNew);

        model.addAttribute("listDokter", dokterService.getListDokter());
        return "users/dt-manajemen-dokter";
    }

    @GetMapping("/apoteker/add")
    public String addApoteker(Model model){
        model.addAttribute("apoteker", new ApotekerModel());
        return "users/form-add-apoteker";
    }

    @PostMapping("/apoteker/add")
    public String addApotekerSumbit(@ModelAttribute ApotekerModel apoteker, Model model){
        ApotekerModel apotekerNew = new ApotekerModel();
        apotekerNew.setNama(apoteker.getNama());
        apotekerNew.setRole("Apoteker");
        apotekerNew.setUsername(apoteker.getUsername());
        apotekerNew.setPassword(apoteker.getPassword());
        apotekerNew.setEmail(apoteker.getEmail());
        apotekerNew.setIsSSO(false);
        userService.add(apotekerNew);

        model.addAttribute("listApoteker", apotekerService.getListApoteker());
        return "users/dt-manajemen-apoteker";
    }

}
