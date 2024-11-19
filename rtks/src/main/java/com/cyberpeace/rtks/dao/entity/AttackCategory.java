package com.cyberpeace.rtks.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 以下数据库表共用一个entity
 * rtks_attack_category
 * rtks_attack_ics_category
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */
@Data
@TableName("rtks_attack_category")
public class AttackCategory {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

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
