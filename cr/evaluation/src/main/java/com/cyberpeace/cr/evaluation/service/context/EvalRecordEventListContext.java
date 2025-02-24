package com.cyberpeace.cr.evaluation.service.context;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.io.Serializable;

@Data
public class EvalRecordEventListContext implements Serializable {
    @ApiModelProperty(value = "数据类型 3级指标时 value不为空 百分比:1,周期:2,延时:3" ,required = false)
    private Integer type;

    @ApiModelProperty(value = "数据属性",required = false)
    private Integer property;

    @ApiModelProperty(value = "节点id",required = false)
    private String eventNodeId;

    @ApiModelProperty(value = "值",required = false)
    private Integer eventValue;

    @ApiModelProperty(value = "告警事件名称",required = false)
    private String eventName;

    @ApiModelProperty(value = "告警匹配名称",required = false)
    private String alarmMatchName;

    @ApiModelProperty(value = "是否自定义分数",required = false)
    private Boolean isCustomScore;

    @ApiModelProperty(value = "自定义分数",required = false)
    private Integer customScore;
}
