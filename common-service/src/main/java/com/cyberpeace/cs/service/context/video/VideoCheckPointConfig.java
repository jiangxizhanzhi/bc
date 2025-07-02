package com.cyberpeace.cs.service.context.video;

import java.util.List;

import org.apache.commons.compress.utils.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 视频检查点配置
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class VideoCheckPointConfig {

	/**
	 * 点
	 */
	@ApiModelProperty(value = "检查点列表")
	private List<VideoCheckPointItem> points = Lists.newArrayList();

	/**
	 * 自定义点
	 */
	@ApiModelProperty(value = "自定义检查点列表")
	private List<VideoCheckPointItem> customPoints = Lists.newArrayList();

	public boolean isEmpty() {
		return points.isEmpty() && customPoints.isEmpty();
	}
}
