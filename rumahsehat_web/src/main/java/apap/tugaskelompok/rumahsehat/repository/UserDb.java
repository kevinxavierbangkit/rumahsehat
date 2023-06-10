package apap.tugaskelompok.rumahsehat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import apap.tugaskelompok.rumahsehat.model.UserModel;

@Repository
public interface UserDb extends JpaRepository<UserModel, String> {
  UserModel findByUsername(String username);

  UserModel findByUuid(String uuid);
}
