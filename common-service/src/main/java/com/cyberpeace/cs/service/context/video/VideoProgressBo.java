package com.cyberpeace.cs.service.context.video;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 视频进度 bo
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class VideoProgressBo {

	/**
	 * 点
	 */
	@ApiModelProperty(value = "检查点列表")
	private List<VideoCheckPointBo> points;

	/**
	 * 自定义点
	 */
	@ApiModelProperty(value = "自定义检查点列表")
	private List<VideoCheckPointBo> customPoints;

	/**
	 * 上次观看时间
	 */
	@ApiModelProperty(value = "上次观看到的时间")
	private Integer lastWatchedTime;

	/**
	 * 上次观看进度
	 */
	@ApiModelProperty(value = "上次观看到的进度")
	private Integer lastWatchedProgress;
}
