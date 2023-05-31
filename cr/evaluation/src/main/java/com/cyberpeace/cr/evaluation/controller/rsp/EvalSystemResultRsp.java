package com.cyberpeace.cr.evaluation.controller.rsp;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class EvalSystemResultRsp {

	@ApiModelProperty(value = "名称")
	private String name;

	@ApiModelProperty(value = "ID")
	private String resourceId;

	@ApiModelProperty(value = "执行轮次")
	private Integer exeCycle;

	@ApiModelProperty(value = "一级得分")
	private Integer firstScore;

	@ApiModelProperty(value = "二级得分")
	private Integer secondScore;

	@ApiModelProperty(value = "三级得分")
	private Integer thirdScore;

	@ApiModelProperty(value = "四级得分")
	private Integer fourthScore;

	@ApiModelProperty(value = "五级得分")
	private Integer fifthScore;

	@ApiModelProperty(value = "子级列表")
	private List<EvalSystemResultRsp> children;

}