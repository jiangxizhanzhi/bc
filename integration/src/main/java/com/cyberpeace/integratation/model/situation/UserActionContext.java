package com.cyberpeace.integratation.model.situation;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserActionContext implements Serializable {

	@ApiModelProperty(value = "用户名称")
	private String userName;

	@ApiModelProperty(value = "行为描述")
	private String desc;

	@ApiModelProperty(value = "行为结果")
	private String result;

	@ApiModelProperty(value = "时间")
	private String time;
}
