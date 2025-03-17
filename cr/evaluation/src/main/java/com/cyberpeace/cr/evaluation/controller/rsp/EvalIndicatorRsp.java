package com.cyberpeace.cr.evaluation.controller.rsp;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.cyberpeace.cr.evaluation.service.context.EvalAggregationContext;
import com.cyberpeace.cr.evaluation.service.context.EvalCollectionContext;
import com.cyberpeace.cr.evaluation.service.context.EvalIndicatorContext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EvalIndicatorRsp {

	@ApiModelProperty
	private String resourceId;

	@ApiModelProperty(value = "指标项Id", required = true)
	@NotNull
	private String evalIndicatorId;

	@ApiModelProperty(value = "名称", required = true)
	@NotNull
	private String name;

	@ApiModelProperty(value = "描述", required = false)
	private String description;

	@ApiModelProperty(value = "指标属性", required = true)
	@NotNull
	private Integer attribute;

	@ApiModelProperty(value = "指标属性值", required = false)
	private Integer attributeValue;

	@ApiModelProperty(value = "指标属性值", required = false)
	private String attributeVal;

	@ApiModelProperty(value = "数据类型", required = true)
	@NotNull
	private Integer datatype;

	@ApiModelProperty(value = "连接线", required = true)
	@NotNull
	private String connlist;

	@ApiModelProperty(value = "采集项列表", required = true)
	@NotNull
	private List<EvalCollectionContext> evalCollections;

	@ApiModelProperty(value = "聚合项列表", required = true)
	@NotNull
	private List<EvalAggregationContext> evalAggregations;

	@ApiModelProperty(value = "指标项列表", required = true)
	@NotNull
	private List<EvalIndicatorContext> evalIndicators;

	@ApiModelProperty(value = "聚合项数量", required = true)
	@NotNull
	private Integer evalAggregationNum;

	@ApiModelProperty(value = "采集项数量", required = true)
	@NotNull
	private Integer evalCollectionNum;
}
