package apap.tugaskelompok.rumahsehat.service;

import apap.tugaskelompok.rumahsehat.model.ApotekerModel;
import apap.tugaskelompok.rumahsehat.model.UserModel;

import java.util.List;

public interface ApotekerService {

    ApotekerModel getApotekerById(String uuid);

    List<ApotekerModel> getListApoteker();


    ApotekerModel addApoteker(ApotekerModel apoteker);
}
