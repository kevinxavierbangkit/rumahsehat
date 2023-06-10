package apap.tugaskelompok.rumahsehat.restcontroller;

import apap.tugaskelompok.rumahsehat.model.PasienModel;
import apap.tugaskelompok.rumahsehat.model.TagihanModel;
import apap.tugaskelompok.rumahsehat.service.PasienRestService;
import apap.tugaskelompok.rumahsehat.service.TagihanRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1")
public class TagihanRestController {

    @Autowired
    private PasienRestService pasienRestService;

    @Autowired
    private TagihanRestService tagihanRestService;

    @GetMapping(value = "/tagihan/{username}")
    private List<TagihanModel> getTagihanPasien(@PathVariable("username") String username) {
        try {
            PasienModel pasien = pasienRestService.getPasienByUsername(username);
            return tagihanRestService.getTagihanPasien(pasien);

        } catch (NoSuchElementException e){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Tagihan tidak ditemukan"
            );
        }
    }

    @GetMapping("/tagihan/detail/{kode}")
    private TagihanModel getTagihan(@PathVariable("kode") String kode){
        try {
            return tagihanRestService.getTagihanByKode(kode);
        }
        catch (NoSuchElementException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tagihan dengan kode " + kode + " tidak ditemukan");
        }
    }

    @GetMapping("/tagihan/{username}/bayar/{kode}")
    private TagihanModel bayarTagihan(@PathVariable("username") String username, @PathVariable("kode") String kode){
        try {
            PasienModel pasien = pasienRestService.getPasienByUsername(username);
            TagihanModel tagihan = tagihanRestService.getTagihanByKode(kode);
            return tagihanRestService.bayarTagihan(pasien, tagihan);
        }
        catch (NoSuchElementException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pasien dengan username " + username + " tidak ditemukan");
        }
    }
}
