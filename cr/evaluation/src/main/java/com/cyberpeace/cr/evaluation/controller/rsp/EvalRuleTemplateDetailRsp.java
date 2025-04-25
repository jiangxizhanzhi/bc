package com.cyberpeace.cr.evaluation.controller.rsp;

import com.cyberpeace.cr.evaluation.controller.req.EvalAlarmConditionReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalAlarmReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalSituationReq;
import com.cyberpeace.cr.evaluation.service.context.EvalDimensionContext;
import com.cyberpeace.cr.evaluation.service.context.EvalHostContext;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class EvalRuleTemplateDetailRsp implements Serializable {

    @ApiModelProperty("云模板id")
    private String cloudTemplateId;

    @ApiModelProperty("规则id")
    private String ruleId;

    @ApiModelProperty("规则名称")
    private String ruleName;

    @ApiModelProperty("规则描述")
    private String description;

    @ApiModelProperty("创建时间")
    private String createTime;

    @ApiModelProperty("严重程度")
    private Integer grade;

    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("状态值")
    private String statusName;

    @ApiModelProperty("创建人")
    private String createUser;

    @ApiModelProperty("布尔关系")
    private Integer booleanRelation;

    @ApiModelProperty("标签")
    private List<String> labels;

    @ApiModelProperty("标注")
    private List<EvalDimensionContext> dimensions;

    @ApiModelProperty("主机")
    private List<EvalHostContext> hosts;

    @ApiModelProperty("任务列表")
    private List<EvalStepRsp> steps;

    @ApiModelProperty("触发告警")
    private List<EvalAlarmReq> alarms;

    @ApiModelProperty("态势展示")
    private List<EvalSituationReq> situations;

    @ApiModelProperty("告警条件")
    private List<EvalAlarmConditionReq> alarmConditions;

    @ApiModelProperty("告警名称")
    private String alarmName;

    @ApiModelProperty("告警描述")
    private String alarmDescription;

    @ApiModelProperty("告警事件级别")
    private Integer alarmLevel;

    @ApiModelProperty("自定义属性")
    private String alarmAttributes;
}
