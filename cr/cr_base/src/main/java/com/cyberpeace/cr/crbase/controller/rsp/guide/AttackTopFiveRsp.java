package com.cyberpeace.cr.crbase.controller.rsp.guide;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author myy
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttackTopFiveRsp implements Serializable {

	@ApiModelProperty("名称")
	private String name;

	@ApiModelProperty("次数")
	private Integer count;

}
