package com.cyberpeace.cr.crbase.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 任务模板
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see MissionBase
 * @since 1.0.0
 */
@Data
@TableName("cr_mission_template")
public class MissionTemplate extends MissionBase {

	/**
	 * 模板id
	 *
	 * @see String
	 */
	private String templateId;

	/**
	 * 附件
	 *
	 * @see String
	 */
	private String attachment;

	/**
	 * 业务模板id
	 *
	 * @see String
	 */
	private String businessTemplateId;
}
