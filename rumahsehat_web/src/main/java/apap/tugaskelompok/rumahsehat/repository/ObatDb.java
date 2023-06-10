package apap.tugaskelompok.rumahsehat.repository;

import apap.tugaskelompok.rumahsehat.model.ObatModel;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ObatDb extends JpaRepository<ObatModel, String> {   
    List<ObatModel> findAllByOrderByNamaObat(); 
}