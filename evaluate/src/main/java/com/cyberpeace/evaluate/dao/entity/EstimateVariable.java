package com.cyberpeace.evaluate.dao.entity;

import com.cyberpeace.common.model.BaseTable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 评估变量表
 */
@Data
public class EstimateVariable extends BaseTable {

	/**变量项id */
	@ApiModelProperty("变量项编号")
	private String estimateVariableId;

	/**变量名 */
	@ApiModelProperty("变量项名称")
	private String name;

	/**数据格式化算法id */
	@ApiModelProperty("格式化算法编号")
	private String formatAlgorithmId;

	/**检测模型id */
	@ApiModelProperty("检测模型算法编号")
	private String detectionAlgorithmId;

	/**评分模型id */
	@ApiModelProperty("评分模型id")
	private String markAlgorithmId;

	/**评估方案id */
	@ApiModelProperty("评估方案id")
	private String estimateSchemeId;

	/**检测模型自定义参数（英文逗号隔开） */
	@ApiModelProperty("检测模型自定义参数")
	private String detectionParam;

	/**评分模型自定义参数（英文逗号隔开） */
	@ApiModelProperty("评分模型自定义参数")
	private String markParam;

	/** 格式化算法自定义参数 */
	@ApiModelProperty("格式化算法自定义参数")
	private String formatParam;
}
