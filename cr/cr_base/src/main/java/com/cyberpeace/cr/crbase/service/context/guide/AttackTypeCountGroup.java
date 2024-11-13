package com.cyberpeace.cr.crbase.service.context.guide;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 攻击类型计数群
 *
 * @author SN
 * @date 2024/11/13
 */
@Data
public class AttackTypeCountGroup implements Serializable {

	/**
	 * 类别
	 */
	@ApiModelProperty("攻击类型")
	private Integer category;

	/**
	 * 计数
	 */
	@ApiModelProperty("攻击次数")
	private Integer count;
}
