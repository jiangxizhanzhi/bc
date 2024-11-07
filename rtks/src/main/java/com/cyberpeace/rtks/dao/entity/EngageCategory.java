package com.cyberpeace.rtks.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 参与类别
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
@TableName("rtks_engage_category")
public class EngageCategory {

	/**
	 * 资源id
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 名称zh
	 */
	private String nameZh;

	/**
	 * 名称中文
	 */
	private String nameEn;
}
