package com.cyberpeace.cr.evaluation.service.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class EvalRecordEventListContext implements Serializable {

    @ApiModelProperty("数据类型 3级指标时 value不为空 百分比:1,周期:2,延时:3")
    private Integer type;

    @ApiModelProperty("数据属性")
    private Integer property;

    @ApiModelProperty(value = "值")
    private Integer value;

    @ApiModelProperty(value = "关联的五级指标告警设置")
    private List<EvalRecordEventFifthIndexContext> eventIndexes = new ArrayList<>(); // 初始化空集合
}
