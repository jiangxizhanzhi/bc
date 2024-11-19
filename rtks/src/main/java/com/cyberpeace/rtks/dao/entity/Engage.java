package com.cyberpeace.rtks.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * Engage
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */
@Data
@TableName("rtks_engage")
public class Engage {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 类别id
	 *
	 * @see String
	 */
	private String categoryId;

	/**
	 * 子类别id
	 *
	 * @see String
	 */
	private String subCategoryId;

	/**
	 * 名称zh
	 *
	 * @see String
	 */
	private String nameZh;

	/**
	 * 名称英文
	 *
	 * @see String
	 */
	private String nameEn;
}
