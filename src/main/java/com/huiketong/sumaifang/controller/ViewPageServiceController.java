package com.huiketong.sumaifang.controller;

import com.huiketong.sumaifang.domain.HouseImg;
import com.huiketong.sumaifang.service.HouseImgService;
import com.huiketong.sumaifang.vo.HouseInfoTableResp;
import com.huiketong.sumaifang.domain.HouseInfo;
import com.huiketong.sumaifang.service.HouseInfoService;
import com.huiketong.sumaifang.service.SysUserService;
import com.huiketong.sumaifang.utils.RandomValidateCodeUtil;
import com.huiketong.sumaifang.vo.BaseResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "back")
public class ViewPageServiceController {

    @Autowired
    SysUserService sysUserService;
    @Autowired
    HouseInfoService houseInfoService;
    @Autowired
    HouseImgService houseImgService;
    @GetMapping(value = "/get_verify_code")
    public void getVerifyCode(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("image/jpeg");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0);
        RandomValidateCodeUtil randomValidateCode = new RandomValidateCodeUtil();
        randomValidateCode.getRandcode(request, response);//输出验证码图片方法
    }

    @PostMapping(value = "login")
    @ResponseBody
    public BaseResp login(HttpSession session,String userName,String password,String code){
        BaseResp resp = new BaseResp();
       String verify_code = (String) session.getAttribute(RandomValidateCodeUtil.RANDOMCODEKEY);
       if(verify_code.toLowerCase().equals(code.toLowerCase())){
           if(sysUserService.login(userName,password)) {
               resp.setCode("0").setMsg("登陆成功").setData(userName);
           }else{
               resp.setCode("1").setMsg("密码错误");
           }
       }else{
            resp.setCode("1").setMsg("验证码不正确");
       }


      return resp;
    }

    @PostMapping(value = "register")
    @ResponseBody
    public BaseResp register(String userName,String password){
        BaseResp resp = new BaseResp();
        boolean success  = sysUserService.registerSysUser(userName,password);
        if(success){
            resp.setCode("0").setMsg("注册成功");
        }else {
            resp.setCode("1").setMsg("注册失败");
        }
        return resp;
    }


    @GetMapping(value = "get_house_info_list")
    @ResponseBody
    public HouseInfoTableResp getHouseInfoList(){
        HouseInfoTableResp resp = new HouseInfoTableResp();
        List<HouseInfoTableResp.DataBean> dataBeanList = new ArrayList<>();

        List<HouseInfo> houseInfoList = houseInfoService.getHouseInfoList();
        if(houseInfoList.size() > 0){
            for(HouseInfo houseInfo:houseInfoList){
                HouseInfoTableResp.DataBean dataBean = new HouseInfoTableResp.DataBean();
                dataBean.setId(houseInfo.getId());
                dataBean.setHouseAddress(houseInfo.getHouseAddress());
                dataBean.setHouseArea(houseInfo.getHouseArea());
                dataBean.setHousePrice(houseInfo.getHouseTotalPrice());
                dataBean.setAssessor(houseInfo.getAssessor());
                dataBeanList.add(dataBean);
            }
        }

        resp.setData(dataBeanList);
        return resp;
    }


    /**
     * 上传房源图片
     * @param houseid
     * @param imgurl
     * @return
     */
    @PostMapping(value = "saveImg")
    @ResponseBody
    public BaseResp saveImg(Integer houseid,String imgurl){
        BaseResp resp = new BaseResp();
        if(houseImgService.save(houseid,imgurl)){
            resp.setMsg("图片存储完成").setCode("0");
        }else{
            resp.setMsg("图片存储失败").setCode("1");
        }
        return resp;
    }

    /**
     *
     * @param air 气源费是否已缴纳
     * @param property_rights_type 产权类型
     * @param maintain 维修基金是否已经缴纳
     * @param residence_booklet 有无户口
     * @param room 室
     * @param hall 厅
     * @param toilet 卫
     * @param tier 第几层
     * @param all 总楼层
     * @param orientation 房屋朝向
     * @param use 房屋用途
     * @param sell_house_reason 卖房原因
     * @param two_taxes_assume 两税是否承担
     * @return
     */
    @PostMapping(value = "approve")
    @ResponseBody
    public BaseResp approve(Integer houseid,Integer air,String property_rights_type,Integer maintain,String residence_booklet,Integer room,Integer hall,Integer toilet,Integer tier,Integer all,String orientation,
    String use,String sell_house_reason,Integer two_taxes_assume){
        BaseResp resp = new BaseResp();
        System.out.println(houseid);
        return resp;
    }
}
