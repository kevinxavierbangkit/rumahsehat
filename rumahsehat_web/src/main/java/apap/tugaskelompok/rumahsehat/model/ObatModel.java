package apap.tugaskelompok.rumahsehat.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "obat")
public class ObatModel implements Serializable {

    @Id
    @Column(name = "id")
    private String id;

    @NotNull
    @Column(name = "nama_obat", nullable = false)
    private String namaObat;

    @NotNull
    @Column(name = "stok", nullable = false)
    @ColumnDefault("100")
    private Integer stok;

    @NotNull
    @Column(name = "harga")
    private Integer harga;

    @OneToMany(mappedBy = "obat", cascade = CascadeType.ALL)
    @JsonIgnore
    List<ResepObatModel> listResep;

}
