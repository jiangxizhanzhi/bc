package com.cyberpeace.rtks.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 参与子类别
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
@TableName("rtks_engage_sub_category")
public class EngageSubCategory {

	/**
	 * 资源id
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 类别id
	 */
	private String categoryId;

	/**
	 * 名称zh
	 */
	private String nameZh;

	/**
	 * 名称中文
	 */
	private String nameEn;
}
