package com.cyberpeace.rtks.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * @TableName rtks_skill
 */
@TableName(value = "rtks_skill")
@Data
public class Skill extends SkillAndKnowledgeBase {

	private String systemId;

}