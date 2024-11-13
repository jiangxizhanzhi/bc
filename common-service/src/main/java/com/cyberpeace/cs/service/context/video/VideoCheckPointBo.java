package com.cyberpeace.cs.service.context.video;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 视频 Check Point bo
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class VideoCheckPointBo extends VideoCheckPointItem {

	/**
	 * 看
	 */
	@ApiModelProperty(value = "是否观看")
	private Boolean watched = false;
}
