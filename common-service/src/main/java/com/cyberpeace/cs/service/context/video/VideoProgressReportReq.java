package com.cyberpeace.cs.service.context.video;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 视频进展报告请求体
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class VideoProgressReportReq {

	/**
	 * 路径
	 */
	@ApiModelProperty(value = "视频路径")
	private String path;

	/**
	 * 时间
	 */
	@ApiModelProperty(value = "时间（秒）")
	private Integer time;

	/**
	 * 业务id
	 */
	@ApiModelProperty(value = "业务id")
	private String businessId;
}
