package com.cyberpeace.cr.redvsblue.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

/**
 * 红蓝对抗模板
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@TableName("cr_rb_template")
public class RbTemplate extends BaseTable {

	/**
	 * 资源id
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 子类型
	 */
	private Integer subType;

	/**
	 * 持续时间
	 */
	private Integer durationTime;

	/**
	 * 预技能
	 */
	private String preSkill;

	/**
	 * 训练技能
	 */
	private String trainSkill;

	/**
	 * 岗位配置
	 */
	private String roleConfig;

	/**
	 * 业务模板id
	 */
	private String businessTemplateId;

}
