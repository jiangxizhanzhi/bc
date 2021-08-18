package com.cyberpeace.oj.rtks.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.oj.common.model.BaseTable;
import lombok.Data;

/**
 * @TableName rtks_skill_system
 */
@TableName(value = "rtks_skill_system")

@Data
public class SkillSystem extends BaseTable {

    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    private String content;
}