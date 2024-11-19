package com.cyberpeace.rtks.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 技能任务关系
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */
@Data
@TableName("rtks_skill_mission_relation")
public class SkillMissionRelation {

	/**
	 * id
	 *
	 * @see Integer
	 */
	@TableId(type = IdType.INPUT, value = "id")
	private Integer id;

	/**
	 * 技能id
	 *
	 * @see String
	 */
	private String skillId;

	/**
	 * 任务模板id
	 *
	 * @see String
	 */
	private String missionTemplateId;

	/**
	 * 重量
	 *
	 * @see Integer
	 */
	private Integer weight;
}
