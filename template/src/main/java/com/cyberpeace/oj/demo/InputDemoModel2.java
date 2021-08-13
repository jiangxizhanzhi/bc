package com.cyberpeace.oj.demo;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class InputDemoModel2 implements Serializable {

	@ApiModelProperty(name = "resource_id", value = "demo2资源id", required = true)
	@NotBlank
	private String resourceId;

	@NotBlank
	private String name;

	@ApiModelProperty(name = "liebiao", value = "一个列表数据")
	private List<String> list;
}
