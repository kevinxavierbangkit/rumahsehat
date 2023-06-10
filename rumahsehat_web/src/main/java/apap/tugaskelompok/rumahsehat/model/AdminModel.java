package apap.tugaskelompok.rumahsehat.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Setter
@Getter
@Entity
@AllArgsConstructor
@Table(name = "admin")
public class AdminModel extends UserModel implements Serializable {
}
