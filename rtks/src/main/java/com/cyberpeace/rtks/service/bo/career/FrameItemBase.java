package com.cyberpeace.rtks.service.bo.career;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FrameItemBase implements Serializable {

	@ApiModelProperty("资源id")
	private String resourceId;

	@ApiModelProperty("名称")
	private String name;

}
