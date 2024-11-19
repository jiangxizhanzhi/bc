package com.cyberpeace.rtks.service.bo.career;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 攻击前面Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see FrameItemBase
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class AttackFrontBo extends FrameItemBase implements Serializable {

	/**
	 * 是否和当前训练关联
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否和当前训练关联")
	private Boolean related = false;
}
