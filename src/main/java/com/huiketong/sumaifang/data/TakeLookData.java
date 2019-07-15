package com.huiketong.sumaifang.data;

import lombok.Data;

/**
 * 带看经纪人列表
 */
@Data
public class TakeLookData {
    String intermediary; //中介名字
    String agent_name; //经纪人名字
    String agent_icon; //经纪人头像
    String isapprove; //是否通过
    String see_num; //带看次数
}
