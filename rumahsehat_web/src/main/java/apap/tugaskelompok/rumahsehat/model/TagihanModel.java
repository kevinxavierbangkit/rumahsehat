package apap.tugaskelompok.rumahsehat.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties(value={"appointment"}, allowSetters = true)
@Table(name = "tagihan")
public class TagihanModel implements Serializable {

    @Id
    @Size(max = 255)
    @GenericGenerator(name = "tagihan_id", strategy = "apap.tugaskelompok.rumahsehat.model.TagihanIdGenerator")
    @GeneratedValue(generator = "tagihan_id")
    @Column(name = "kode", nullable = false)
    private String kode;

    @NotNull
    @Column(name = "tanggal_terbuat", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime tanggalTerbuat;

    @Column(name = "tanggal_bayar")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime tanggalBayar;

    @NotNull
    @Column(name = "is_paid", nullable = false)
    private Boolean isPaid;

    @NotNull
    @Column(name = "jumlah_tagihan", nullable = false)
    private Integer jumlahTagihan;

    @OneToOne(mappedBy = "tagihan")
    private AppointmentModel appointment;
}
