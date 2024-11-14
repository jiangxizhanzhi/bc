package com.cyberpeace.rtks.service.bo.career;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AttackBackBo extends FrameItemBase implements Serializable {

	/**
	 * 关联的训练模板数
	 */
	@ApiModelProperty("关联的训练模板数")
	private Integer count = 0;
}
