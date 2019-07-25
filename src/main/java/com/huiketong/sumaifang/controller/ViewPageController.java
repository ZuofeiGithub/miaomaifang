package com.huiketong.sumaifang.controller;

import com.huiketong.sumaifang.domain.Biotope;
import com.huiketong.sumaifang.domain.Cities;
import com.huiketong.sumaifang.domain.HouseImg;
import com.huiketong.sumaifang.domain.HouseInfo;
import com.huiketong.sumaifang.service.BiotopeService;
import com.huiketong.sumaifang.service.CitiesService;
import com.huiketong.sumaifang.service.HouseImgService;
import com.huiketong.sumaifang.service.HouseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ViewPageController {
    @Autowired
    HouseInfoService houseInfoService;
    @Autowired
    HouseImgService houseImgService;
    @Autowired
    CitiesService citiesService;
    @Autowired
    BiotopeService biotopeService;
    @GetMapping("/login")
    public String login(){
        return "/login/login";
    }

    @GetMapping("/admin")
    public String admin(){
        return "admin/admin";
    }

    @GetMapping("/main")
    public String main(){
        return "main/main";
    }

    @GetMapping("/houseinfomanager")
    public String houseinfo(){
        return "houseinfomanager/houseinfomanager";
    }

    @GetMapping("/review")
    public String review(Integer id, Model model){
        HouseInfo houseInfo = houseInfoService.findMyHouseById(id);
        if(!ObjectUtils.isEmpty(houseInfo)){
            model.addAttribute("houseInfo",houseInfo);
            model.addAttribute("room",houseInfo.getHouseLayout() == null ? "0":houseInfo.getHouseLayout().substring(0,1));
            model.addAttribute("hall",houseInfo.getHouseLayout() == null ? "0":houseInfo.getHouseLayout().substring(2,3));
            model.addAttribute("toilet",houseInfo.getHouseLayout() == null ? "0":houseInfo.getHouseLayout().substring(4,5));
            model.addAttribute("tier",houseInfo.getHouseTier() == null ? "0":houseInfo.getHouseTier().substring(0,1));
            model.addAttribute("all",houseInfo.getHouseTier() == null ? "0":houseInfo.getHouseTier().substring(2,3));
        }
        List<HouseImg> houseImgList = houseImgService.findHouseImg(id);
        if(houseImgList.size() > 0){
            List<String> imglist = new ArrayList<>();
            for(HouseImg img :houseImgList){
                imglist.add(img.getImgurl());
            }
            model.addAttribute("imagelist",imglist);
        }
        return "review";
    }
    @GetMapping("/imageupload")
    public String imageUpload(){
        return "imageupload";
    }

    @GetMapping("/setting")
    public String setting(){
        return "setting/setting";
    }

    @GetMapping("/add_house")
    public String addHouse(Model model){
        Cities cities = citiesService.findCityByName("南通市");
        if(!ObjectUtils.isEmpty(cities)) {
            List<Biotope> biotopeList = biotopeService.findBiotopList(cities.getCityid());
            if(biotopeList.size() >0 ){
                List<String> district = new ArrayList<>();
                for(Biotope biotope:biotopeList){
                    district.add(biotope.getName());
                }
                model.addAttribute("districts",district);
            }
        }
        return "houseinfomanager/add_house";
    }
}
