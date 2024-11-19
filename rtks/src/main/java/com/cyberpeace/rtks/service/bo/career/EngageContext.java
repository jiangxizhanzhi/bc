package com.cyberpeace.rtks.service.bo.career;

import java.io.Serializable;

import lombok.Data;

/**
 * Engage上下文
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class EngageContext implements Serializable {

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
	 * 子类别id
	 *
	 * @see String
	 */
	private String subCategoryId;

	/**
	 * 子类别名称zh
	 *
	 * @see String
	 */
	private String subCategoryNameZh;

	/**
	 * 子类别名称英文
	 *
	 * @see String
	 */
	private String subCategoryNameEn;

	/**
	 * Engageid
	 *
	 * @see String
	 */
	private String engageId;

	/**
	 * Engage名称zh
	 *
	 * @see String
	 */
	private String engageNameZh;

	/**
	 * Engage名称英文
	 *
	 * @see String
	 */
	private String engageNameEn;
}
