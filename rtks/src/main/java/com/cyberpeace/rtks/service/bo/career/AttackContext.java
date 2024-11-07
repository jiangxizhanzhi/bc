package com.cyberpeace.rtks.service.bo.career;

import java.io.Serializable;

import lombok.Data;

/**
 * 攻击上下文
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class AttackContext implements Serializable {

	/**
	 * 类别id
	 */
	private String categoryId;

	/**
	 * 类别名称zh
	 */
	private String categoryNameZh;

	/**
	 * 类别名称中文
	 */
	private String categoryNameEn;

	/**
	 * 攻击id
	 */
	private String attackId;

	/**
	 * 攻击名称zh
	 */
	private String attackNameZh;

	/**
	 * 攻击名称中文
	 */
	private String attackNameEn;
}
