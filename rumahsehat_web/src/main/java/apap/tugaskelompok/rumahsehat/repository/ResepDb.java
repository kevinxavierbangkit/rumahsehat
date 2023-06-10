package apap.tugaskelompok.rumahsehat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import apap.tugaskelompok.rumahsehat.model.ResepModel;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface ResepDb extends JpaRepository<ResepModel, Long> {
    Optional<ResepModel> findById(Long id);
    List<ResepModel> findAllByOrderById();
}
