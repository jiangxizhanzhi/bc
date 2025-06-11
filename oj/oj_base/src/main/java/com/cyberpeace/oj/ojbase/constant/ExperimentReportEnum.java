package com.cyberpeace.oj.ojbase.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;
import lombok.Getter;

@Getter
public enum ExperimentReportEnum implements GenericEnum {
    NOT_SUBMIT(1, "待提交"), // 未提交
    WAIT_EVALUATE(2, "待批改"), // 已提交
    EVALUATED(3, "已批改"); // 已批改, 是否通过不在这里展示

    private final Integer code;

    private final String desc;

    ExperimentReportEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }


}
