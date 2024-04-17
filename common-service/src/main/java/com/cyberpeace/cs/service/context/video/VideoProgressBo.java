package com.cyberpeace.cs.service.context.video;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class VideoProgressBo {

	@ApiModelProperty(value = "检查点列表")
	private List<VideoCheckPointBo> points;

	@ApiModelProperty(value = "上次观看到的时间")
	private Integer lastWatchedTime;

	@ApiModelProperty(value = "上次观看到的进度")
	private Integer lastWatchedProgress;
}
