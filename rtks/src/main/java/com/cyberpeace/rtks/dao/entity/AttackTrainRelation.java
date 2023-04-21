package com.cyberpeace.rtks.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

/**
 * 以下数据库表共用一个entity
 * rtks_attack_train_relation
 * rtks_attack_ics_train_relation
 */
@Data
@TableName("rtks_attack_train_relation")
public class AttackTrainRelation extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private String attackId;

	private String businessTemplateId;
}
