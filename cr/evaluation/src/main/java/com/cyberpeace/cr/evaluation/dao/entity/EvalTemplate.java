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
 * @date 2024/11/07
 */
@Data
@TableName("cr_eval_template")
public class EvalTemplate extends BaseTable {

	/**
	 * 资源id
	 */
	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 图标
	 */
	private String logo;

	/**
	 * 拓扑id
	 */
	private String topoId;

	/**
	 * 类别代号
	 */
	private Integer categoryCode;

	/**
	 * 步骤配置
	 */
	private String stepConfig;

	/**
	 * 启用录制
	 */
	private Boolean enableRecording;

	/**
	 * 当前任务id
	 */
	private String currentTaskId;

	/**
	 * 创造用户id
	 */
	private String createUserId;

	public void setLogoId(String logoId) {
		this.logo = logoId;
	}

}
