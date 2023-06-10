package apap.tugaskelompok.rumahsehat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties(value={"listAppointment"}, allowSetters = true)
@Table(name = "pasien")
public class PasienModel extends UserModel implements Serializable {

    @Column(name = "saldo")
    private int saldo;

    @NotNull
    @Column(name = "umur", nullable = false)
    private Integer umur;

    @JsonIgnore
    @OneToMany(mappedBy = "pasien", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<AppointmentModel> listAppointment;
}
