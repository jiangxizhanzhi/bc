package com.cyberpeace.rtks.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 以下数据库表共用一个entity
 * rtks_attack
 * rtks_attack_ics
 */
@Data
@TableName("rtks_attack")
public class Attack {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private String categoryId;

	private String nameZh;

	private String nameEn;
}
