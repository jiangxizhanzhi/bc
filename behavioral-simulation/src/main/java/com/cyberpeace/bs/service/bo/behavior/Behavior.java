package com.cyberpeace.bs.service.bo.behavior;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 访问行为
 */
@Data
public class Behavior extends BehaviorSimple implements Serializable {

	@ApiModelProperty(value = "行为名称")
	private String name;

	@ApiModelProperty(value = "行为步骤")
	private Integer stepCount;

	@ApiModelProperty(value = "行为耗时（min）")
	private Integer timeCost;

}
