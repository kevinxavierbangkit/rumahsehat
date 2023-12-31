package apap.tugaskelompok.rumahsehat.repository;

import apap.tugaskelompok.rumahsehat.model.AdminModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;

@Controller
public interface AdminDb extends JpaRepository<AdminModel, Long> {

    AdminModel findByUuid(String uuid);
}
