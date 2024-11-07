package com.cyberpeace.rtks.service.bo.career;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 攻击前线 bo
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class AttackFrontBo extends FrameItemBase implements Serializable {

	/**
	 * 相关
	 */
	@ApiModelProperty("是否和当前训练关联")
	private Boolean related = false;
}
