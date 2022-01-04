package com.cyberpeace.situation.constant;

import lombok.Getter;

public enum ProcessStatus {
    /**
     *过程状态
     */

    START(1, "开始"),
    IN_PROGRESS(2, "进行中"),
    PAUSE(3, "暂停"),
    OVER(4, "已结束"),
    ABNORMAL(5, "异常");

    @Getter
    private Integer code;

    @Getter
    private String desc;

    ProcessStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
