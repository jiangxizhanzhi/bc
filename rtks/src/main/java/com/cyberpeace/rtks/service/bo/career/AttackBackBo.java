package com.cyberpeace.rtks.service.bo.career;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 反击博
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class AttackBackBo extends FrameItemBase implements Serializable {

	/**
	 * 计数
	 */
	@ApiModelProperty("关联的训练模板数")
	private Integer count = 0;
}
