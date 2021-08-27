package com.cyberpeace.rtks.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 
 * @TableName rtks_knowledge
 */
@TableName(value = "rtks_knowledge")
@Data
public class Knowledge extends SkillAndKnowledgeBase {
}