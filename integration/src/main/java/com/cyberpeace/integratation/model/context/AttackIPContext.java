package com.cyberpeace.integratation.model.context;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel
public class AttackIPContext extends AttackContext {

	@ApiModelProperty("ip列表参数中ip的类型 0:源ip,1:目标ip,默认为0")
	private Integer ipType = 0;
}
