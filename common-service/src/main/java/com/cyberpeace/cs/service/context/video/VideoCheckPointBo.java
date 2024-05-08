package com.cyberpeace.cs.service.context.video;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class VideoCheckPointBo extends VideoCheckPointItem {

	@ApiModelProperty(value = "是否观看")
	private Boolean watched = false;
}
