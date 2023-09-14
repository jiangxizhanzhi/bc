package com.cyberpeace.cr.crbase.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author myy
 */
@Data
public class AttackTopFiveRsp implements Serializable {

	@ApiModelProperty("攻防类型")
	private Integer category;

	@ApiModelProperty("次数")
	private Integer count;

}
