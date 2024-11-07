package com.cyberpeace.rtks.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 知识
 *
 * @author SN
 * @date 2024/11/07
 */
@TableName(value = "rtks_knowledge")
@Data
public class Knowledge extends SkillAndKnowledgeBase {
}