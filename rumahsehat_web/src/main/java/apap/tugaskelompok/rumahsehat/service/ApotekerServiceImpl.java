package apap.tugaskelompok.rumahsehat.service;

import apap.tugaskelompok.rumahsehat.model.ApotekerModel;
import apap.tugaskelompok.rumahsehat.model.UserModel;
import apap.tugaskelompok.rumahsehat.repository.ApotekerDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ApotekerServiceImpl implements ApotekerService {

    @Autowired
    ApotekerDb apotekerDb;

    @Autowired
    UserService userService;

    @Override
    public ApotekerModel getApotekerById(String uuid){
        ApotekerModel apoteker = apotekerDb.findByUuid(uuid);
        return apoteker;
    }

    @Override
    public List<ApotekerModel> getListApoteker() {
        return apotekerDb.findAll();
    }

    @Override
    public ApotekerModel addApoteker(ApotekerModel apoteker) {
        return apotekerDb.save(apoteker);
    }

//    public UserModel addApoteker(UserModel apoteker){
//        return userService.add(apoteker);
//    }
}
