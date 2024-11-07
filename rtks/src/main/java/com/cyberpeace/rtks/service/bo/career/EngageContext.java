package com.cyberpeace.rtks.service.bo.career;

import java.io.Serializable;

import lombok.Data;

/**
 * 参与上下文
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class EngageContext implements Serializable {

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
	 * 子类别id
	 */
	private String subCategoryId;

	/**
	 * 子类别名称zh
	 */
	private String subCategoryNameZh;

	/**
	 * 子类别名称中文
	 */
	private String subCategoryNameEn;

	/**
	 * 聘id
	 */
	private String engageId;

	/**
	 * 聘名称zh
	 */
	private String engageNameZh;

	/**
	 * 聘名称中文
	 */
	private String engageNameEn;
}
