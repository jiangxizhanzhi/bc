package com.cyberpeace.rtks.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("rtks_skill_mission_relation")
public class SkillMissionRelation {

	private Integer id;

	private String skillId;

	private String missionTemplateId;

	private Integer weight;
}
