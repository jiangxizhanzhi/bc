package com.cyberpeace.cs.service.context.video;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class VideoProgressReportReq {

	@ApiModelProperty(value = "视频路径")
	private String path;

	@ApiModelProperty(value = "时间（秒）")
	private Integer time;

	@ApiModelProperty(value = "业务id")
	private String businessId;
}
