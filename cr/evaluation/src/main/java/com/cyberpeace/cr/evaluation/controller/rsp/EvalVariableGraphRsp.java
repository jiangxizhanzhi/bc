package com.cyberpeace.cr.evaluation.controller.rsp;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 评估变量图响应
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class EvalVariableGraphRsp implements Serializable {
	/**
	 * 时间节点
	 */
	@ApiModelProperty("时间节点")
    private List<Date> time;

	/**
	 * 格式化算法生成值
	 */
	@ApiModelProperty("格式化算法生成值")
    private List<Integer> value;

	/**
	 * 是否通过模型检测算法
	 */
	@ApiModelProperty("是否通过模型检测算法")
    private List<Boolean> isMatched;
}
