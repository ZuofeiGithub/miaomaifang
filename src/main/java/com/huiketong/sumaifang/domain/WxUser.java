package com.huiketong.sumaifang.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

public class WxUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String openid;
    String telphone;
    boolean bindstatus;
    Date bindTime;
}
