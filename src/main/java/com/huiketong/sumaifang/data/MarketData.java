package com.huiketong.sumaifang.data;

import lombok.Data;

/**
 * 市场行情
 */
@Data
public class MarketData {
    //平均售出天数
    Integer averageWorkOffDays;
    //合作中介个数
    Integer intermediaryNum;
    //储备买家
    Integer clientNum;
    //全城经纪人
    Integer agentNum;
    //全市均价
    Integer averagePrice;
    //环比上月增长
    Double increase;
}
