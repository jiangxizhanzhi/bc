package com.cyberpeace.cr.evaluation.controller.rsp;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 评估系统树响应
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class EvalSystemTreeRsp {

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "名称")
	private String name;

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "ID")
	private String resourceId;

	/**
	 * 水平
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "级别")
	private Integer level;

	/**
	 * 类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "3级指标项评分类型")
	private Integer type;

	/**
	 * 第三级类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "3级指标项评分选项")
	private Integer thirdLevelType;

	/**
	 * 子级
	 *
	 * @see List<EvalSystemTreeRsp>
	 */
	@ApiModelProperty(value = "子级列表")
	private List<EvalSystemTreeRsp> children;

}