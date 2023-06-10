package apap.tugaskelompok.rumahsehat.service;

import apap.tugaskelompok.rumahsehat.model.TagihanDailyModel;
import apap.tugaskelompok.rumahsehat.repository.TagihanDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class TagihanSeriveImpl implements TagihanService{
  @Autowired
  TagihanDb tagihanDb;
  @Override
  public List<TagihanDailyModel> getListTagihanHarian() {
    return tagihanDb.getListTagihanHarian();
  }
}
