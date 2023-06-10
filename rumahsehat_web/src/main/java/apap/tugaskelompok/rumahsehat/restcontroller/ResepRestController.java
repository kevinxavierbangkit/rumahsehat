package apap.tugaskelompok.rumahsehat.restcontroller;

import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import apap.tugaskelompok.rumahsehat.model.ResepModel;
import apap.tugaskelompok.rumahsehat.service.ResepService;

@RestController
@RequestMapping("api/v1")
public class ResepRestController {

    @Autowired
    ResepService resepService;

    @GetMapping(value = "/resep/view/{id}")
    private ResepModel viewDetailResep(@PathVariable Long id) {
        try {
            return resepService.getResepById(id);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Receipt  not found");
        }
    }
}
