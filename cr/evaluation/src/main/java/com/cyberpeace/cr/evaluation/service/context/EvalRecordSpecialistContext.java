package com.cyberpeace.cr.evaluation.service.context;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EvalRecordSpecialistContext implements Serializable {
    @ApiModelProperty("数据类型 3级指标时 value不为空 百分比:1,周期:2,延时:3")
    private Integer type;

    @ApiModelProperty("数据属性")
    private Integer property;

    @ApiModelProperty(value = "值")
    private Integer value;
}
