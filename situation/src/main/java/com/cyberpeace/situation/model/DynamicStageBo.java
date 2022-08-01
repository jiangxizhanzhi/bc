package com.cyberpeace.situation.model;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DynamicStageBo implements Serializable {

	@ApiModelProperty("阶段名称")
	private String name;

	@ApiModelProperty("动效id")
	private List<String> attackMessageIds;
}
