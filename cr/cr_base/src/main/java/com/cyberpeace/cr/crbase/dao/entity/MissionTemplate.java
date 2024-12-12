package com.cyberpeace.cr.crbase.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 任务模板
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
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
