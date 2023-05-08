package com.cyberpeace.cr.crbase.service.context.mission;

import java.io.Serializable;

import lombok.Data;

/**
 *
 * 任务编排模板配置
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class MissionSchemeTemplateConfig implements Serializable {

	private Integer template;

	private Integer color;

	public MissionSchemeTemplateConfig(Integer template, Integer color) {
		this.template = template;
		this.color = color;
	}

}
