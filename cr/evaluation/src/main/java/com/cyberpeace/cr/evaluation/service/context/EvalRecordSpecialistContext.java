package com.cyberpeace.cr.evaluation.service.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class EvalRecordSpecialistContext implements Serializable {
    @ApiModelProperty("类型")
    private Integer type;

    @ApiModelProperty("值")
    private Integer value;
}
