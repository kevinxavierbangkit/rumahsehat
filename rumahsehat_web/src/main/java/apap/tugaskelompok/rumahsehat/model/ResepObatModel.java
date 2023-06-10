package apap.tugaskelompok.rumahsehat.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "resep_obat")
public class ResepObatModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "resep", referencedColumnName = "id")
    @JsonIgnore
    private ResepModel resep;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "obat", referencedColumnName = "id")
    private ObatModel obat;

    @Column(name = "kuantitas")
    private Integer kuantitas;
}
