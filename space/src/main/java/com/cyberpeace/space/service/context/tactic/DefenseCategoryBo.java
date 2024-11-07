package com.cyberpeace.space.service.context.tactic;

import lombok.Data;

/**
 * 国防类 bo
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class DefenseCategoryBo {

	/**
	 * 资源id
	 */
	private String resourceId;

	/**
	 * 阶段
	 */
	private Integer stage;

	/**
	 * 盖
	 */
	private String cover;
}
