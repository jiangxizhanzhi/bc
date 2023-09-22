package com.cyberpeace.situation.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class LeakInfo implements Serializable {

	@ApiModelProperty("漏洞名称")
	private String name;

	@ApiModelProperty("版本信息")
	private String version;

	@ApiModelProperty("严重程度")
	private String severity;
}
