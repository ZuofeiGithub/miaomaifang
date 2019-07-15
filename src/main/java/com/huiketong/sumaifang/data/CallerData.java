package com.huiketong.sumaifang.data;

import lombok.Data;

/**
 * 通话经纪人列表
 */
@Data
public class CallerData {
    String agent_icon; //经纪人头像
    String agent_name; //经纪人名字
    String intermediary; //中介
    String isapprove; //是否认证
    String call_num; //通话次数
}
