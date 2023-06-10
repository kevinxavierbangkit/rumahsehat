package apap.tugaskelompok.rumahsehat.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties(value={"listAppointment"}, allowSetters = true)
@Table(name = "dokter")
public class DokterModel extends UserModel implements Serializable {

    @NotNull
    @Column(name = "tarif", nullable = false)
    private Integer tarif;

    @OneToMany(mappedBy = "dokter", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<AppointmentModel> listAppointment;
}
