package com.cyberpeace.cr.crbase.service.context.mission;

import com.cyberpeace.constant.SelectI18nName;

import lombok.Data;

/**
 * 任务类别缓存
 *
 * @author SN
 * @date 2024/11/13
 */
@Data
public class MissionCategoryCache implements SelectI18nName {

	/**
	 * 资源id
	 */
	private String resourceId;

	/**
	 * 中文名称
	 */
	private String cnName;

	/**
	 * 中文名称
	 */
	private String enName;
}
