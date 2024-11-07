package com.cyberpeace.rtks.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 技能任务关系
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
@TableName("rtks_skill_mission_relation")
public class SkillMissionRelation {

	/**
	 * id
	 */
	@TableId(type = IdType.INPUT, value = "id")
	private Integer id;

	/**
	 * 技能id
	 */
	private String skillId;

	/**
	 * 任务模板id
	 */
	private String missionTemplateId;

	/**
	 * 重量
	 */
	private Integer weight;
}
