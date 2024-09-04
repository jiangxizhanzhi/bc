package com.cyberpeace.cr.evaluation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("cr_eval_alarm_condition")
public class EvalAlarmCondition extends BaseTable {
    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    @ApiModelProperty(value = "报警条件的类型")
    private String type;

    @ApiModelProperty(value = "聚合类型")
    private String aggregationType;

    @ApiModelProperty(value = "聚合类型名称")
    private String aggregationTypeName;

    @ApiModelProperty(value = "运算符")
    private String operator;

    @ApiModelProperty(value = "运算符名称")
    private String operatorName;

    @ApiModelProperty(value = "阈值")
    private double threshold;

    @ApiModelProperty(value = "搜索条件")
    private String alarmCondition;

    @ApiModelProperty(value = "频率")
    private String frequency;

    /**
     * 规则id
     */
    private String ruleId;

    /**
     * 云模板id
     */
    private String cloudTemplateId;
}