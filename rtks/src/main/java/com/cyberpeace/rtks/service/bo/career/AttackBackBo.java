package com.cyberpeace.rtks.service.bo.career;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 攻击返回Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see FrameItemBase
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class AttackBackBo extends FrameItemBase implements Serializable {

	/**
	 * 关联的训练模板数
	 *
	 * @see Integer
	 */
	@ApiModelProperty("关联的训练模板数")
	private Integer count = 0;
}
