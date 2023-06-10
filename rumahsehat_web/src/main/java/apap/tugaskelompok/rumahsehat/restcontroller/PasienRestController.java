package apap.tugaskelompok.rumahsehat.restcontroller;

import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import apap.tugaskelompok.rumahsehat.model.PasienModel;
import apap.tugaskelompok.rumahsehat.service.PasienRestService;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin("http://localhost:51962/")
public class PasienRestController {
    @Autowired
    private PasienRestService pasienRestService;

    // add
    @PostMapping(value = "/pasien/add")
    private PasienModel createPasien(@Valid @RequestBody PasienModel pasien, BindingResult bindingResult) {
        System.out.println("halohalo");
        if (bindingResult.hasFieldErrors()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "request body has invalid type or missing field"
            );
        } else {
            return pasienRestService.createPasien(pasien);
        }

    }

    //top-up-saldo
    @PutMapping("/pasien/top-up/{username}")
    private PasienModel topUpSaldo(@PathVariable("username") String username, @RequestBody PasienModel pasien){
        try {
            return pasienRestService.topUpSaldo(username, pasien);
        }
        catch (NoSuchElementException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pasien dengan username " + username + " tidak ditemukan");
        }
    }
}
