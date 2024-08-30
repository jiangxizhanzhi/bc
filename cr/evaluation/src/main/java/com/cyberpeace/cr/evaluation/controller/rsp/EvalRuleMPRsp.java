package com.cyberpeace.cr.evaluation.controller.rsp;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class EvalRuleMPRsp implements Serializable {

    @ApiModelProperty("场景id")
    private String sceneId;

    @ApiModelProperty("云模板id")
    private String cloudTemplateId;

    @ApiModelProperty("规则名称")
    private String name;

    @ApiModelProperty("规则描述")
    private String description;

    @ApiModelProperty("创建时间")
    private String createTime;

    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("创建人")
    private String createUserId;

    @ApiModelProperty("标签")
    private String labels;

    @ApiModelProperty("主机")
    private String hosts;

    @ApiModelProperty("告警条件")
    private String alarmConditions;

    @ApiModelProperty("告警名称")
    private String alarmName;

    @ApiModelProperty("告警描述")
    private String alarmDescription;

    @ApiModelProperty("告警事件级别")
    private Integer alarmLevel;

    @ApiModelProperty("自定义属性")
    private String alarmAttributes;
}
