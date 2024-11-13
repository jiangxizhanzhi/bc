package com.cyberpeace.cr.crbase.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 任务模板
 *
 * @author SN
 * @date 2024/11/13
 */
@Data
@TableName("cr_mission_template")
public class MissionTemplate extends MissionBase {

	/**
	 * 模板id
	 */
	private String templateId;

	/**
	 * 附件
	 */
	private String attachment;

	/**
	 * 业务模板id
	 */
	private String businessTemplateId;
}
