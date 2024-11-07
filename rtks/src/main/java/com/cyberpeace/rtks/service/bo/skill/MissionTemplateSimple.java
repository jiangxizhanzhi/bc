package com.cyberpeace.rtks.service.bo.skill;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务模板 简单
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class MissionTemplateSimple implements Serializable {

	/**
	 * 资源id
	 */
	@ApiModelProperty("任务id")
	private String resourceId;

	/**
	 * 标题
	 */
	@ApiModelProperty("任务名称")
	private String title;

	/**
	 * 场景名称
	 */
	@ApiModelProperty("场景名称")
	private String sceneName;
}
