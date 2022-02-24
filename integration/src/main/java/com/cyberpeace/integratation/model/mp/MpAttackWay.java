package com.cyberpeace.integratation.model.mp;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel("攻击百分比")
@Data
public class MpAttackWay implements Serializable {

	@ApiModelProperty("攻击百分比")
	private Double attackTrafficPercent;

	@ApiModelProperty("攻击类型")
	private String type;
}
