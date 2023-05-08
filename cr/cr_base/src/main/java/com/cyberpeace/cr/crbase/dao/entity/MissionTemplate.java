package com.cyberpeace.cr.crbase.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 任务模板
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
@TableName("cr_mission_template")
public class MissionTemplate extends MissionBase {

	private String templateId;

	private String attachment;

	private String businessTemplateId;
}
