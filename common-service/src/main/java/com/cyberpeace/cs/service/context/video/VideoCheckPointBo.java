package com.cyberpeace.cs.service.context.video;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class VideoCheckPointBo {

	@ApiModelProperty(value = "时间（秒）")
	private Integer time;

	@ApiModelProperty(value = "进度（百分比）")
	private Integer progress;
}
