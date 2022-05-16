package com.cyberpeace.cr.evaluation.service.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class EvalRecordSpecialistContext implements Serializable {
    @ApiModelProperty("数据类型")
    private Integer type;

    @ApiModelProperty("数据属性")
    private Integer property;

    @ApiModelProperty("值")
    private Integer value;
}
