package com.cyberpeace.cr.crbase.service.context.mission;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务模板返回体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class MissionTemplateRes implements Serializable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty("任务id")
	private String resourceId;

	/**
	 * 标题
	 *
	 * @see String
	 */
	@ApiModelProperty("任务名称")
	private String title;

	/**
	 * 场景名称
	 *
	 * @see String
	 */
	@ApiModelProperty("场景名称")
	private String sceneName;

	/**
	 * 场景类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("场景类型")
	private Integer sceneType;

	/**
	 * 场景难度
	 *
	 * @see Integer
	 */
	@ApiModelProperty("场景难度")
	private Integer sceneDifficulty;
}
