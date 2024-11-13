package com.cyberpeace.cs.service.context.video;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 视频检查点项目
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class VideoCheckPointItem {

	/**
	 * 时间
	 */
	@ApiModelProperty(value = "时间（秒）")
	private Integer time;

	/**
	 * 弹出式
	 */
	private Boolean popUp = false;

}
