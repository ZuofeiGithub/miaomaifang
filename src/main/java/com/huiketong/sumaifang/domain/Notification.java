package com.huiketong.sumaifang.domain;


import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(columnDefinition = "int(11) default 0 comment '1.提交成功,2.审核未通过,3.审核通过'")
    Integer notifyType;

    @Column(columnDefinition = "varchar(1024) default ''  comment '通知消息' ")
    String notifyMsg;
    @Column(columnDefinition = "varchar(255) default '' comment '简略消息'")
    String summarizeMsg;
    @Column(columnDefinition = "varchar(255) default '' comment '用户标识'")
    String userToken;
    @Column(columnDefinition = "int(11) default 0 comment '房屋标识'")
    Integer houseId;
    @Column(columnDefinition = "datetime comment '通知时间'")
    Date notifyTime;
}
