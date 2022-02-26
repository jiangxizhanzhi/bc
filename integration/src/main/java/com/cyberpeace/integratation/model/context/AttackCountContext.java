package com.cyberpeace.integratation.model.context;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@ApiModel("攻击统计参数")
@Data
public class AttackCountContext extends AttackContext {


	@ApiModelProperty("默认前五")
	private Integer topSize = 5;
}
