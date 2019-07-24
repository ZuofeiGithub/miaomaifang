package com.huiketong.sumaifang.controller;

import com.huiketong.sumaifang.domain.HouseImg;
import com.huiketong.sumaifang.domain.HouseInfo;
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
}
