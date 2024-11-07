package com.cyberpeace.cr.evaluation.controller.rsp;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 评估系统树响应
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class EvalSystemTreeRsp {

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称")
	private String name;

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "ID")
	private String resourceId;

	/**
	 * 水平
	 */
	@ApiModelProperty(value = "级别")
	private Integer level;

	/**
	 * 类型
	 */
	@ApiModelProperty(value = "3级指标项评分类型")
	private Integer type;

	/**
	 * 第三级类型
	 */
	@ApiModelProperty(value = "3级指标项评分选项")
	private Integer thirdLevelType;

	/**
	 * 子级
	 */
	@ApiModelProperty(value = "子级列表")
	private List<EvalSystemTreeRsp> children;

}