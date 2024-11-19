package com.cyberpeace.rtks.service.bo.career;

import java.io.Serializable;

import lombok.Data;

/**
 * 攻击上下文
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class AttackContext implements Serializable {

	/**
	 * 类别id
	 *
	 * @see String
	 */
	private String categoryId;

	/**
	 * 类别名称zh
	 *
	 * @see String
	 */
	private String categoryNameZh;

	/**
	 * 类别名称英文
	 *
	 * @see String
	 */
	private String categoryNameEn;

	/**
	 * 攻击id
	 *
	 * @see String
	 */
	private String attackId;

	/**
	 * 攻击名称zh
	 *
	 * @see String
	 */
	private String attackNameZh;

	/**
	 * 攻击名称英文
	 *
	 * @see String
	 */
	private String attackNameEn;
}
