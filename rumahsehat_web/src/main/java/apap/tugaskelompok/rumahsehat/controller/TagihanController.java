package apap.tugaskelompok.rumahsehat.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import apap.tugaskelompok.rumahsehat.service.TagihanService;
import apap.tugaskelompok.rumahsehat.model.TagihanDailyModel;

@Controller
@RequestMapping("/tagihan-chart")
public class TagihanController {
  @Autowired
  TagihanService tagihanService;

  @GetMapping("/default")
  private String getListofObatIncome(Model model) {
    List<TagihanDailyModel> listTagihan = tagihanService.getListTagihanHarian();

    List<Integer> day = new ArrayList<>();
    for (int i = 1; i< 31; i++){
      day.add(i);
    }


    List<Integer> total = new ArrayList<>();
    for (int i = 1; i< 31; i++){
      total.add(0);
    }

    for (TagihanDailyModel dayTotal : listTagihan) {
      for (int i=1; i< 31; i++) {
        if (dayTotal.getDay() == i) {
          int temp = total.get(i-1);
          total.add(i-1, dayTotal.getTotal()+temp);
        }
      }
    }


    model.addAttribute("totalTagihan", total);
    model.addAttribute("day", day);
    return "tagihan/chart-tagihan-default";
  }

}
