package com.cyberpeace.cr.crbase.service.context.guide;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 攻击时间计数群
 *
 * @author SN
 * @date 2024/11/13
 */
@Data
public class AttackTimeCountGroup implements Serializable {

	/**
	 * 创建时间
	 */
	@ApiModelProperty("攻击时间")
	private String createTime;

	/**
	 * 计数
	 */
	@ApiModelProperty("攻击次数")
	private Integer count;
}
