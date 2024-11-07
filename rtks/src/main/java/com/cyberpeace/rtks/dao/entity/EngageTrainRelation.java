package com.cyberpeace.rtks.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

/**
 * 聘训练关系
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
@TableName("rtks_engage_train_relation")
public class EngageTrainRelation extends BaseTable {

	/**
	 * 资源id
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 聘id
	 */
	private String engageId;

	/**
	 * 业务模板id
	 */
	private String businessTemplateId;
}
