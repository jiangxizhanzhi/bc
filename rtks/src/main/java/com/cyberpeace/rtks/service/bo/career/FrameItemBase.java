package com.cyberpeace.rtks.service.bo.career;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FrameItemBase implements Serializable {

	/**
	 * 资源id
	 */
	@ApiModelProperty("资源id")
	private String resourceId;

	/**
	 * 名称
	 */
	@ApiModelProperty("名称")
	private String name;

}
