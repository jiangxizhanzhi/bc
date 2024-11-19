package com.cyberpeace.rtks.service.bo.career;

import java.io.Serializable;

import lombok.Data;

/**
 * 场景模板和计数的关联
 * attack， engage
 *
 * @author SN
 * @version 1.0.0
 * @date 2023/05/10
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class TechRelation implements Serializable {

	/**
	 * 业务模板id
	 *
	 * @see String
	 */
	private String businessTemplateId;

	/**
	 * 技术id
	 *
	 * @see String
	 */
	private String techId;
}
