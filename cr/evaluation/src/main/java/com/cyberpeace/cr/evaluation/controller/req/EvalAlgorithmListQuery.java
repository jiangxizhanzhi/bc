package com.cyberpeace.cr.evaluation.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EvalAlgorithmListQuery extends PageReqBase implements Serializable {

	@ApiModelProperty(value = "名称", required = false)
	private String name;

	@ApiModelProperty(value = "算法类型", required = false)
	@NotNull
	private Integer type;
}
