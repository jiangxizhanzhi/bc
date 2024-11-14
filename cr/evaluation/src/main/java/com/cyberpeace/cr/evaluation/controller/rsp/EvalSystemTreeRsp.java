package com.cyberpeace.cr.evaluation.controller.rsp;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 评估系统树响应
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class EvalSystemTreeRsp {

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称")
	private String name;

	/**
	 * ID
	 */
	@ApiModelProperty(value = "ID")
	private String resourceId;

	/**
	 * 级别
	 */
	@ApiModelProperty(value = "级别")
	private Integer level;

	/**
	 * 3级指标项评分类型
	 */
	@ApiModelProperty(value = "3级指标项评分类型")
	private Integer type;

	/**
	 * 3级指标项评分选项
	 */
	@ApiModelProperty(value = "3级指标项评分选项")
	private Integer thirdLevelType;

	/**
	 * 子级列表
	 */
	@ApiModelProperty(value = "子级列表")
	private List<EvalSystemTreeRsp> children;

}