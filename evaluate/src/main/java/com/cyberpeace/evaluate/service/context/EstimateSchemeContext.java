package com.cyberpeace.evaluate.service.context;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.evaluate.dao.entity.EstimateVariable;

import com.cyberpeace.evaluate.service.context.EstimateIndexContext;
import com.cyberpeace.evaluate.service.context.EstimateItemsContext;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EstimateSchemeContext implements Serializable {

	/**
	 * 变量项
	 */
	@ApiModelProperty("变量项")
	private List<EstimateVariable> estimateVariables;

	/**
	 * 指标项
	 */
	@ApiModelProperty("指标项")
	private List<EstimateIndexContext> estimateIndexes;

	/**
	 * 评估项
	 */
	@ApiModelProperty("评估项")
	private List<EstimateItemsContext> estimateItems;

	/**
	 * 前端连线使用
	 */
	private String connlist;

	/**
	 * logo
	 */
	@ApiModelProperty("logo文件路径")
	private String logo;

	/**
	 * 描述
	 */
	@ApiModelProperty("方案描述")
	private String desc;

	/**
	 * 名称
	 */
	@ApiModelProperty("方案名称")
	private String name;

	/**
	 * 评估方案编号
	 */
	@ApiModelProperty("方案编号")
	private String estimateSchemeId;

}
