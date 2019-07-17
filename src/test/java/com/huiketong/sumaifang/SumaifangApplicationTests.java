package com.huiketong.sumaifang;

import com.huiketong.sumaifang.service.TerraceRecordService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SumaifangApplicationTests {

    @Autowired
    TerraceRecordService terraceRecordService;
    @Test
    public void contextLoads() {

    }

}
