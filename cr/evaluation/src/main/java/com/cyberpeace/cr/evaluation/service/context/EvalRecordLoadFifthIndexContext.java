package com.cyberpeace.cr.evaluation.service.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class EvalRecordLoadFifthIndexContext implements Serializable {

    @ApiModelProperty(value = "五级指标id",required = false)
    private String fifthIndexResourceId;

    @ApiModelProperty(value = "数据类型 3级指标时 value不为空 百分比:1,周期:2,延时:3" ,required = false)
    private Integer type;

    @ApiModelProperty(value = "载荷执行记录id",required = false)
    private String loadExecutionId;

    @ApiModelProperty(value = "载荷执行记录名",required = false)
    private String loadExecutionName;

    @ApiModelProperty(value = "研判结果(0:未研判 1:成功 2:失败)",required = false)
    private Integer judgmentStatus;

    @ApiModelProperty(value = "值",required = false)
    private Integer value;
}
