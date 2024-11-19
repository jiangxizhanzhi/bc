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
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see BaseTable
 * @since 1.0.0
 */
@Data
@TableName("rtks_attack_train_relation")
public class AttackTrainRelation extends BaseTable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 攻击id
	 *
	 * @see String
	 */
	private String attackId;

	/**
	 * 业务模板id
	 *
	 * @see String
	 */
	private String businessTemplateId;
}
