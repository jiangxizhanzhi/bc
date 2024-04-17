package com.cyberpeace.cs.service.context.video;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class VideoProgressReportReq extends VideoCheckPointBo {

	@ApiModelProperty(value = "视频路径")
	private String path;

	@ApiModelProperty(value = "业务id")
	private String businessId;
}
