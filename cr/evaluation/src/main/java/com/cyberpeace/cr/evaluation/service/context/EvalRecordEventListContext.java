package com.cyberpeace.cr.evaluation.service.context;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.io.Serializable;

@Data
public class EvalRecordEventListContext implements Serializable {
    @ApiModelProperty("数据类型 3级指标时 value不为空 百分比:1,周期:2,延时:3")
    private Integer type;

    @ApiModelProperty("数据属性")
    private Integer property;

    @ApiModelProperty("节点id")
    private String eventNodeId;

    @ApiModelProperty(value = "值")
    private Integer eventValue;

    @ApiModelProperty(value = "告警事件名称")
    private String evnName;
}
