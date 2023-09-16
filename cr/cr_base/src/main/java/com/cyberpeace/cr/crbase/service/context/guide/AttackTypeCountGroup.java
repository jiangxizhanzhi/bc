package com.cyberpeace.cr.crbase.service.context.guide;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class AttackTypeCountGroup implements Serializable {

	@ApiModelProperty("攻击类型")
	private Integer category;

	@ApiModelProperty("攻击次数")
	private Integer count;
}
