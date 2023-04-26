package com.cyberpeace.rtks.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 以下数据库表共用一个entity
 * rtks_attack_category
 * rtks_attack_ics_category
 */
@Data
@TableName("rtks_attack_category")
public class AttackCategory {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private String nameZh;

	private String nameEn;
}
