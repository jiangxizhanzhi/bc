package com.cyberpeace.cr.evaluation.controller.rsp;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 评估记录结果variable响应
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class EvalRecordResultVariableRsp implements Serializable {
	/**
	 * ID
	 */
	@ApiModelProperty("ID")
    private String resourceId;

	/**
	 * 变量项名称
	 */
	@ApiModelProperty("变量项名称")
    private String name;

	/**
	 * 格式化算法信息
	 */
	@ApiModelProperty("格式化算法信息")
    private EvalRecordResultAlgorithmRsp formatAlgorithm;

	/**
	 * 匹配算法信息
	 */
	@ApiModelProperty("匹配算法信息")
    private EvalRecordResultAlgorithmRsp matchAlgorithm;

	/**
	 * 评分算法信息
	 */
	@ApiModelProperty("评分算法信息")
    private EvalRecordResultAlgorithmRsp markAlgorithm;
}
