package apap.tugaskelompok.rumahsehat.controller;

import apap.tugaskelompok.rumahsehat.model.ResepModel;
import apap.tugaskelompok.rumahsehat.model.ResepObatModel;
import apap.tugaskelompok.rumahsehat.model.TagihanModel;
import apap.tugaskelompok.rumahsehat.model.UserModel;
import apap.tugaskelompok.rumahsehat.model.ApotekerModel;
import apap.tugaskelompok.rumahsehat.model.AppointmentModel;
import apap.tugaskelompok.rumahsehat.model.DokterModel;
import apap.tugaskelompok.rumahsehat.model.ObatModel;
import apap.tugaskelompok.rumahsehat.service.ApotekerService;
import apap.tugaskelompok.rumahsehat.service.AppointmentService;
import apap.tugaskelompok.rumahsehat.service.ObatService;
import apap.tugaskelompok.rumahsehat.service.ResepService;
import apap.tugaskelompok.rumahsehat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ResepController {
    @Qualifier("resepServiceImpl")
    @Autowired
    private ResepService resepService;

    @Autowired
    private ObatService obatService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private UserService userService;

    @Autowired
    private ApotekerService apotekerService;

    private AppointmentModel appointment;

    @GetMapping("/resep")
    public String listResep(Model model) {
        List<ResepModel> listResep = resepService.getListResep();
        model.addAttribute("listResep", listResep);

        return "resep/viewall-resep";
    }

    @GetMapping("/resep/create/{code}")
    public String createResepFormPage(@PathVariable String code, Model model) {
        appointment = appointmentService.getAppointmentByKode(code);

        ResepModel resep = new ResepModel();
        List<ResepObatModel> listResepObat = new ArrayList<>();
        resep.setListObat(listResepObat);
        resep.getListObat().add(new ResepObatModel());
        
        model.addAttribute("resep", resep);
        model.addAttribute("listObatDb", obatService.findAll());

        return "resep/form-create-resep";
    }

    @PostMapping(value = "/resep/create", params = {"save"})
    public String createResepSubmitPage(@ModelAttribute ResepModel resep, Model model) {
        if (resep.getListObat() == null || resep.getListObat().size()==0){
            resep.setListObat(new ArrayList<>());}
        
        for (ResepObatModel x : resep.getListObat()) {
            x.setResep(resep);}
        
        LocalDateTime waktuDibuat = LocalDateTime.now();
        resep.setCreatedAt(waktuDibuat);
        resep.setIsDone(false);

        resep.setAppointment(appointment);
        appointment.setResep(resep);
        resepService.createResep(resep);
        appointmentService.createAppointment(appointment);

        model.addAttribute("resep", resep);

        return "resep/create-resep";
    }

    @PostMapping(value="/resep/create", params = {"addRowObat"})
    public String addRowObatMultiple(@ModelAttribute ResepModel resep, Model model) {
        if (resep.getListObat() == null || resep.getListObat().size()==0){
            resep.setListObat(new ArrayList<>());}

        resep.getListObat().add(new ResepObatModel());
        model.addAttribute("resep", resep);
        model.addAttribute("listObatDb", obatService.findAll());

        return "resep/form-create-resep";
    }

    @PostMapping(value="/resep/create", params = {"deleteRowObat"})
    public String deleteRowObatMultiple(@ModelAttribute ResepModel resep, @RequestParam("deleteRowObat") Integer row, Model model) {
        final Integer rowId = row;
        resep.getListObat().remove(rowId.intValue());

        model.addAttribute("resep", resep);
        model.addAttribute("listObatDb", obatService.findAll());

        return "resep/form-create-resep";
    }
    
    @GetMapping("/resep/view/{id}")
    public String viewDetailResep(@PathVariable Long id, Model model) {
        ResepModel resep = resepService.getResepById(id);

        String namaApoteker= "";
        if (resep.getApoteker() == null) {
            namaApoteker = "Belum ada";
        } else  {namaApoteker = resep.getApoteker().getNama();}

        model.addAttribute("resep", resep);
        model.addAttribute("namaApoteker", namaApoteker);

        return "resep/view-detail-resep";
    }

    @GetMapping("/resep/konfirmasi/{id}")
    public String konfirmasiResep(@PathVariable Long id, Model model) {
        
        ResepModel resep = resepService.getResepById(id);
        if (!cekStokObat(resep.getListObat())) {
            return "resep/eror-konfirmasi-resep";
        }

        TagihanModel tagihan = new TagihanModel();
        int banyakTagihan = besarTagihan(resep.getListObat(), resep.getAppointment().getDokter());
        tagihan.setTanggalTerbuat(LocalDateTime.now());
        tagihan.setIsPaid(false);
        tagihan.setJumlahTagihan(banyakTagihan);
        tagihan.setAppointment(resep.getAppointment());

        UserModel user = userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        ApotekerModel apoteker = apotekerService.getApotekerById(user.getUuid());
        resep.setApoteker(apoteker);
        resep.setIsDone(true);
        resep.getAppointment().setIsDone(true);
        resep.getAppointment().setTagihan(tagihan);
        resepService.createResep(resep);

        model.addAttribute("resep", resep);
        model.addAttribute("apoteker", apoteker);

        return "resep/konfirmasi-resep";
    }

    private Boolean cekStokObat(List<ResepObatModel> listResepObat) {
        for (ResepObatModel resep: listResepObat) {
            ObatModel obat = obatService.findById(resep.getObat().getId());
            if (resep.getKuantitas() > obat.getStok()) {
                return false;
            }
        }

        for (ResepObatModel resep: listResepObat) {
            ObatModel obat = obatService.findById(resep.getObat().getId());
            int jumlahObat = obat.getStok() - resep.getKuantitas();
            obat.setStok(jumlahObat);
            obatService.updateObat(obat);
        }

        return true;
    }

    private Integer besarTagihan(List<ResepObatModel> listResepObat, DokterModel dokter) {
        int tarifDokter = dokter.getTarif();
        int tarifObat = 0;

        for (ResepObatModel resep: listResepObat) {
            tarifObat += (resep.getKuantitas() * resep.getObat().getHarga());}

        return tarifDokter + tarifObat;
    }
}
