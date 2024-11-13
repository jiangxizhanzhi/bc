package com.cyberpeace.cr.crbase.controller.rsp.guide;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 攻击Top5响应
 *
 * @author myy@date 2024/11/12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttackTopFiveRsp implements Serializable {

	/**
	 * 名称
	 */
	@ApiModelProperty("名称")
	private String name;

	/**
	 * 计数
	 */
	@ApiModelProperty("次数")
	private Integer count;

}
