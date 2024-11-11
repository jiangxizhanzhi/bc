package com.cyberpeace.cr.evaluation.service.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * EVAL 记录专家背景
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class EvalRecordSpecialistContext implements Serializable {
    /**
     * 类型
     */
    @ApiModelProperty("数据类型 3级指标时 value不为空 百分比:1,周期:2,延时:3")
    private Integer type;

    /**
     * 配置
     */
    @ApiModelProperty("数据属性")
    private Integer property;

    /**
     * 值
     */
    @ApiModelProperty(value = "值")
    private Integer value;
}
