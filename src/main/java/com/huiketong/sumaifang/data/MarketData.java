package com.huiketong.sumaifang.data;

import lombok.Data;

/**
 * 市场行情
 */
@Data
public class MarketData {
    //平均售出天数
    String averageWorkOffDays;
    //合作中介个数
    String intermediaryNum;
    //储备买家
    String clientNum;
    //全城经纪人
    String agentNum;
    //全市均价
    String averagePrice;
    //环比上月增长
    String increase;
}
