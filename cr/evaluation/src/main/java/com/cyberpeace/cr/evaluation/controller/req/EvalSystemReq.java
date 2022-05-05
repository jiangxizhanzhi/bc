package com.cyberpeace.cr.evaluation.controller.req;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.cyberpeace.cr.evaluation.service.context.IndexContext;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EvalSystemReq {

	@ApiModelProperty(value = "评估体系Id", required = true)
	@NotNull
	private String evalSystemId;

	@ApiModelProperty(value = "名称", required = true)
	@NotNull
	private String name;

	@ApiModelProperty(value = "描述", required = true)
	@NotNull
	private String description;

	@ApiModelProperty(value = "连接线", required = true)
	@NotNull
	private String connlist;

	@ApiModelProperty(value = "一级指标列表", required = true)
	@NotNull
	private List<IndexContext> firstIndexes;

	@ApiModelProperty(value = "二级指标列表", required = true)
	@NotNull
	private List<IndexContext> secondIndexes;

	@ApiModelProperty(value = "三级指标列表", required = true)
	@NotNull
	private List<IndexContext> thirdIndexes;

}