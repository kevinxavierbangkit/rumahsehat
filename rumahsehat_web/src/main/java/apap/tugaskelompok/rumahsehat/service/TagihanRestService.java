package apap.tugaskelompok.rumahsehat.service;

import apap.tugaskelompok.rumahsehat.model.PasienModel;
import apap.tugaskelompok.rumahsehat.model.TagihanModel;

import java.util.List;

public interface TagihanRestService {

    List<TagihanModel> getListTagihan();
    List<TagihanModel> getTagihanPasien(PasienModel pasien);
    TagihanModel getTagihanByKode(String kode);
    TagihanModel bayarTagihan(PasienModel pasien, TagihanModel tagihan);
}
