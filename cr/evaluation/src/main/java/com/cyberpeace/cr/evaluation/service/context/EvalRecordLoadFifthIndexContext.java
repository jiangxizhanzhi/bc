package com.cyberpeace.cr.evaluation.service.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class EvalRecordLoadFifthIndexContext implements Serializable {

    @ApiModelProperty(value = "五级指标id",required = false)
    private String fifthIndexResourceId;

    @ApiModelProperty(value = "载荷执行记录id",required = false)
    private String loadExecutionId;

    @ApiModelProperty(value = "值",required = false)
    private Integer loadValue;
}
