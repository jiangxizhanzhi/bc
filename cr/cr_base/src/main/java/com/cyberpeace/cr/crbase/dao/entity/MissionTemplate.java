package com.cyberpeace.cr.crbase.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("cr_mission_template")
public class MissionTemplate extends MissionBase {

	private String templateId;

	private String attachment;

	private String businessTemplateId;
}
