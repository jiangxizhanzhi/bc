package com.cyberpeace.cr.evaluation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

/**
 * eval 模板
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see BaseTable
 * @since 1.0.0
 */
@Data
@TableName("cr_eval_template")
public class EvalTemplate extends BaseTable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 名称
	 *
	 * @see String
	 */
	private String name;

	/**
	 * 描述
	 *
	 * @see String
	 */
	private String description;

	/**
	 * 图标
	 *
	 * @see String
	 */
	private String logo;

	/**
	 * 拓扑id
	 *
	 * @see String
	 */
	private String topoId;

	/**
	 * 类别代号
	 *
	 * @see Integer
	 */
	private Integer categoryCode;

	/**
	 * 步骤配置
	 *
	 * @see String
	 */
	private String stepConfig;

	/**
	 * 启用录制
	 *
	 * @see Boolean
	 */
	private Boolean enableRecording;

	/**
	 * 当前任务id
	 *
	 * @see String
	 */
	private String currentTaskId;

	/**
	 * 创造用户id
	 *
	 * @see String
	 */
	private String createUserId;

	public void setLogoId(String logoId) {
		this.logo = logoId;
	}

}
