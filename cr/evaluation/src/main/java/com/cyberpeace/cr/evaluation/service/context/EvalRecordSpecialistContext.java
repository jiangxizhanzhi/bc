package com.cyberpeace.cr.evaluation.service.context;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 测评记录专家上下文
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class EvalRecordSpecialistContext implements Serializable {
	/**
	 * 数据类型 3级指标时 value不为空 百分比:1,周期:2,延时:3
	 */
    @ApiModelProperty("数据类型 3级指标时 value不为空 百分比:1,周期:2,延时:3")
    private Integer type;

	/**
	 * 数据属性
	 */
    @ApiModelProperty("数据属性")
    private Integer property;

	/**
	 * 值
	 */
    @ApiModelProperty(value = "值")
    private Integer value;
}
