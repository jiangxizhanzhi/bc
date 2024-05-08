package com.cyberpeace.cs.service.context.video;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class VideoCheckPointItem {

	@ApiModelProperty(value = "时间（秒）")
	private Integer time;

	private Boolean popUp = false;

}
