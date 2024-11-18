package com.cyberpeace.cr.crbase.service.context.manual;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 手动任务 bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class ManualMissionBo implements Serializable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "任务id")
	private String resourceId;

	/**
	 * 标题
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "任务名称")
	private String title;

	/**
	 * 写入向上
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "题解")
	private String writeUp;
}
