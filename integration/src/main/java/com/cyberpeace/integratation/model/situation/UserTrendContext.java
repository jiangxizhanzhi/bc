package com.cyberpeace.integratation.model.situation;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserTrendContext implements Serializable {

	@ApiModelProperty(value = "时间")
	private String time;

	@ApiModelProperty(value = "次数")
	private Integer count;

}
