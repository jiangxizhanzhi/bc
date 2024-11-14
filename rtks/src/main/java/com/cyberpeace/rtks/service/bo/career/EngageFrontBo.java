package com.cyberpeace.rtks.service.bo.career;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EngageFrontBo extends FrameItemBase implements Serializable {

	/**
	 * 是否和当前训练关联
	 */
	@ApiModelProperty("是否和当前训练关联")
	private Boolean related = false;
}
