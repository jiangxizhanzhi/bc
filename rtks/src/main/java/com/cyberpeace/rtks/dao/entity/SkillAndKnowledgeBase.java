package com.cyberpeace.rtks.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;

import java.io.Serializable;

@Data
public class SkillAndKnowledgeBase extends BaseTable {

    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    private String categoryId;

    private String content;

    private String courses;

    private Integer weight;

}
