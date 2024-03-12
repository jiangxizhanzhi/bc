package com.cyberpeace.cr.evaluation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;

@Data
@TableName("cr_eval_host_template")
public class EvalHostTemplate extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 主机名称
	 */
	private String hostName;

	/**
	 * 主机id
	 */
	private String hostId;

	/**
	 * 类型
	 */
	private String type;

	/**
	 * ip地址
	 */
	private String ip;

	/**
	 * 规则id
	 */
	private String ruleId;

	/**
	 * 云模板id
	 */
	private String cloudTemplateId;

}