package com.cyberpeace.rtks.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("rtks_skill_mission_relation")
public class SkillMissionRelation {

	@TableId(type = IdType.INPUT, value = "id")
	private Integer id;

	private String skillId;

	private String missionTemplateId;

	private Integer weight;
}
