package com.huiketong.sumaifang.controller;

import com.huiketong.sumaifang.utils.AliyunOSSUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;

@Controller
@RequestMapping("upload")
public class UploadController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "upload_image",method = RequestMethod.POST)
    @ResponseBody
    public String uploadImage(MultipartFile file){
        logger.info("===========>文件上传");
        try {
            if(null != file){
                String filename = file.getOriginalFilename();
                if(!"".equals(filename.trim())){
                    File newfile = new File(filename);
                    FileOutputStream os  = new FileOutputStream(newfile);
                    os.write(file.getBytes());
                    os.close();
                    file.transferTo(newfile);
                    //上传到OSS
                    String imageUrl = AliyunOSSUtil.upload(newfile);
                }
            }
        }catch (Exception ex){

        }
        return "上传成功";
    }

    @GetMapping(value = "upload")
    public String upload(){
        return "upload";
    }
}
