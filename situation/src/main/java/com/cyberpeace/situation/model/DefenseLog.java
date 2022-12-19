package com.cyberpeace.situation.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class DefenseLog implements Serializable {

	@ApiModelProperty("时间")
	private String time;

	@ApiModelProperty("主机名称")
	private String hostName;

	@ApiModelProperty("防御类型")
	private String defenseType;

	@ApiModelProperty("主机id")
	private String hostId;
}
