package apap.tugaskelompok.rumahsehat.repository;

import apap.tugaskelompok.rumahsehat.model.TagihanDailyModel;
import apap.tugaskelompok.rumahsehat.model.TagihanModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagihanDb extends JpaRepository<TagihanModel, Long> {

    TagihanModel findByKode(String kode);

    @Query(value=
            "SELECT DAY(CURRENT_DATE) as day, SUM(t.jumlahTagihan) as total " +
                    "FROM TagihanModel as t WHERE YEAR(t.tanggalTerbuat) = YEAR(CURRENT_DATE) AND " +
                    "MONTH(t.tanggalTerbuat) = MONTH(CURRENT_DATE) GROUP BY MONTH(CURRENT_DATE)"
    )
    List<TagihanDailyModel> getListTagihanHarian();
}
