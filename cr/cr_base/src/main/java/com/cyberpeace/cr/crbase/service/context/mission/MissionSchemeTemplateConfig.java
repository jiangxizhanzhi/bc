package com.cyberpeace.cr.crbase.service.context.mission;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 任务编排模板配置
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class MissionSchemeTemplateConfig implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 模板
	 */
	private Integer template;

	/**
	 * 颜色
	 */
	private Integer color;

	public MissionSchemeTemplateConfig(Integer template, Integer color) {
		this.template = template;
		this.color = color;
	}

}
