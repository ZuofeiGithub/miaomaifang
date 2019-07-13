package com.huiketong.sumaifang.data;

import lombok.Data;

import java.util.List;

@Data
public class PriceTimeMachineData {

    List<MessageBean> messages;
    List<String> pricelist;
    List<String> datetimelist;

    @Data
    public static class MessageBean{
        String msg;
        String msgtime;
    }
}
