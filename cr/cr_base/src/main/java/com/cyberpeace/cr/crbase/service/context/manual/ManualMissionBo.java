package com.cyberpeace.cr.crbase.service.context.manual;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 手动任务 bo
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class ManualMissionBo implements Serializable {

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "任务id")
	private String resourceId;

	/**
	 * 标题
	 */
	@ApiModelProperty(value = "任务名称")
	private String title;

	/**
	 * 写入向上
	 */
	@ApiModelProperty(value = "题解")
	private String writeUp;
}
