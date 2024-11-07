package com.cyberpeace.rtks.service.bo.career;

import java.io.Serializable;

import lombok.Data;

/**
 * 场景模板和计数的关联
 * attack， engage
 *
 * @author SN
 * @date 2023/05/10
 */
@Data
public class TechRelation implements Serializable {

	/**
	 * 业务模板id
	 */
	private String businessTemplateId;

	/**
	 * 技术id
	 */
	private String techId;
}
