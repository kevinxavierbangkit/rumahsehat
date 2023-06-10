package apap.tugaskelompok.rumahsehat.controller;

import apap.tugaskelompok.rumahsehat.model.ObatModel;
import apap.tugaskelompok.rumahsehat.service.ObatService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/obat")
public class ObatController {
    @Autowired
    private ObatService obatService;

    @GetMapping(value = "/viewall")
    public String viewAllObat(Model model) {
        List<ObatModel> listObat = obatService.findAll();
        model.addAttribute("listObat", listObat);
        return "obat/viewall-obat";
    }

    @GetMapping(value = "/update/{id}")
    public String updateObatFormPage(@PathVariable String id, Model model) {
        ObatModel obat = obatService.findById(id);
        model.addAttribute("obat", obat);
        return "obat/form-update-obat";
    }

    @PostMapping(value = "/update")
    public String updateObatSubmitPage(@ModelAttribute ObatModel obat, Model model) {
        ObatModel updatedObat = obatService.updateObat(obat);
        model.addAttribute("namaObat", updatedObat.getNamaObat());
        return "obat/update-obat";
    }
}
