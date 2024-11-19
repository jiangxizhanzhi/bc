package com.cyberpeace.rtks.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 知识点
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see SkillAndKnowledgeBase
 * @since 1.0.0
 */
@TableName(value = "rtks_knowledge")
@Data
public class Knowledge extends SkillAndKnowledgeBase {
}