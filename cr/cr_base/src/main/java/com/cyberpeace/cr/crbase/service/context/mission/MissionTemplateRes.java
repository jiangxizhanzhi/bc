package com.cyberpeace.cr.crbase.service.context.mission;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 任务模板返回体
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class MissionTemplateRes implements Serializable {

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

	/**
	 * 场景类型
	 */
	@ApiModelProperty("场景类型")
	private Integer sceneType;

	/**
	 * 场景难度
	 */
	@ApiModelProperty("场景难度")
	private Integer sceneDifficulty;
}
