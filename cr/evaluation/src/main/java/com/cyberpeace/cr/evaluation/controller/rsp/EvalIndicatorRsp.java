package com.cyberpeace.cr.evaluation.controller.rsp;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.cyberpeace.cr.evaluation.service.context.EvalAggregationContext;
import com.cyberpeace.cr.evaluation.service.context.EvalCollectionContext;
import com.cyberpeace.cr.evaluation.service.context.EvalIndicatorContext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 评估指示器响应
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class EvalIndicatorRsp {

	/**
	 * 指标项Id
	 */
	@ApiModelProperty(value = "指标项Id", required = true)
	@NotNull
	private String evalIndicatorId;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称", required = true)
	@NotNull
	private String name;

	/**
	 * 描述
	 */
	@ApiModelProperty(value = "描述", required = false)
	private String description;

	/**
	 * 指标属性
	 */
	@ApiModelProperty(value = "指标属性", required = true)
	@NotNull
	private Integer attribute;

	/**
	 * 指标属性值
	 */
	@ApiModelProperty(value = "指标属性值", required = false)
	private Integer attributeValue;

	/**
	 * 指标属性值
	 */
	@ApiModelProperty(value = "指标属性值", required = false)
	private String attributeVal;

	/**
	 * 数据类型
	 */
	@ApiModelProperty(value = "数据类型", required = true)
	@NotNull
	private Integer datatype;

	/**
	 * 连接线
	 */
	@ApiModelProperty(value = "连接线", required = true)
	@NotNull
	private String connlist;

	/**
	 * 采集项列表
	 */
	@ApiModelProperty(value = "采集项列表", required = true)
	@NotNull
	private List<EvalCollectionContext> evalCollections;

	/**
	 * 聚合项列表
	 */
	@ApiModelProperty(value = "聚合项列表", required = true)
	@NotNull
	private List<EvalAggregationContext> evalAggregations;

	/**
	 * 指标项列表
	 */
	@ApiModelProperty(value = "指标项列表", required = true)
	@NotNull
	private List<EvalIndicatorContext> evalIndicators;

	/**
	 * 聚合项数量
	 */
	@ApiModelProperty(value = "聚合项数量", required = true)
	@NotNull
	private Integer evalAggregationNum;

	/**
	 * 采集项数量
	 */
	@ApiModelProperty(value = "采集项数量", required = true)
	@NotNull
	private Integer evalCollectionNum;
}
