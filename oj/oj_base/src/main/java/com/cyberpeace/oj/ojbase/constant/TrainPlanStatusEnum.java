package com.cyberpeace.oj.ojbase.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;
import lombok.Getter;

@Getter
public enum TrainPlanStatusEnum implements GenericEnum {
    //  1- 未开始， 2- 进行中， 3- 已结束
    NOT_START(1, "未开始"),
    IN_PROGRESS(2, "进行中"),
    FINISHED(3, "已结束");


    final private Integer code;

    final private String desc;

    TrainPlanStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
