package apap.tugaskelompok.rumahsehat.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties(value={"tagihan"}, allowSetters = true)
@Table(name = "appointment")
public class AppointmentModel implements Serializable {

    @Id
    @Size(max = 255)
    @GenericGenerator(name = "appointment_id", strategy = "apap.tugaskelompok.rumahsehat.model.AppointmentIdGenerator")
    @GeneratedValue(generator = "appointment_id")
    @Column(name = "kode", nullable = false)
    private String kode;

    @NotNull
    @Column(name = "waktu_awal", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime waktuAwal;

    @NotNull
    @Column(name = "is_done", nullable = false)
    private Boolean isDone;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "uuid_pasien", referencedColumnName = "uuid", nullable = false)
    private PasienModel pasien;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "uuid_dokter", referencedColumnName = "uuid")
    private DokterModel dokter;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "kode_tagihan", referencedColumnName = "kode")
    private TagihanModel tagihan;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_resep", referencedColumnName = "id")
    @JsonIgnore
    private ResepModel resep;
}
