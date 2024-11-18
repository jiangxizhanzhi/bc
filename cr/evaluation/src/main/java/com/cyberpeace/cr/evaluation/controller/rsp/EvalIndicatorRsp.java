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
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class EvalIndicatorRsp {

	/**
	 * 评估指示器id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "指标项Id", required = true)
	@NotNull
	private String evalIndicatorId;

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "名称", required = true)
	@NotNull
	private String name;

	/**
	 * 描述
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "描述", required = false)
	private String description;

	/**
	 * 属性
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "指标属性", required = true)
	@NotNull
	private Integer attribute;

	/**
	 * 属性值
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "指标属性值", required = false)
	private Integer attributeValue;

	/**
	 * 属性 val
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "指标属性值", required = false)
	private String attributeVal;

	/**
	 * 数据类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "数据类型", required = true)
	@NotNull
	private Integer datatype;

	/**
	 * 连接列表
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "连接线", required = true)
	@NotNull
	private String connlist;

	/**
	 * EVAL 集合
	 *
	 * @see List<EvalCollectionContext>
	 */
	@ApiModelProperty(value = "采集项列表", required = true)
	@NotNull
	private List<EvalCollectionContext> evalCollections;

	/**
	 * eval 聚合
	 *
	 * @see List<EvalAggregationContext>
	 */
	@ApiModelProperty(value = "聚合项列表", required = true)
	@NotNull
	private List<EvalAggregationContext> evalAggregations;

	/**
	 * 评估指示器
	 *
	 * @see List<EvalIndicatorContext>
	 */
	@ApiModelProperty(value = "指标项列表", required = true)
	@NotNull
	private List<EvalIndicatorContext> evalIndicators;

	/**
	 * 评估集合体数量
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "聚合项数量", required = true)
	@NotNull
	private Integer evalAggregationNum;

	/**
	 * 评估收集数量
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "采集项数量", required = true)
	@NotNull
	private Integer evalCollectionNum;
}
