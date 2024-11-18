package com.cyberpeace.cr.crbase.service.context.mission;

import java.io.Serializable;

import lombok.Data;

/**
 * 任务编排模板配置
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class MissionSchemeTemplateConfig implements Serializable {

	/**
	 * 模板
	 *
	 * @see Integer
	 */
	private Integer template;

	/**
	 * 颜色
	 *
	 * @see Integer
	 */
	private Integer color;

	public MissionSchemeTemplateConfig(Integer template, Integer color) {
		this.template = template;
		this.color = color;
	}

}
