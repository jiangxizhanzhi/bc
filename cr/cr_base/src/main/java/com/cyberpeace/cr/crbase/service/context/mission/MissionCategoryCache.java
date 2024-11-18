package com.cyberpeace.cr.crbase.service.context.mission;

import com.cyberpeace.constant.SelectI18nName;

import lombok.Data;

/**
 * 任务类别缓存
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see SelectI18nName
 * @since 1.0.0
 */
@Data
public class MissionCategoryCache implements SelectI18nName {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	private String resourceId;

	/**
	 * 中文名称
	 *
	 * @see String
	 */
	private String cnName;

	/**
	 * 中文名称
	 *
	 * @see String
	 */
	private String enName;
}
