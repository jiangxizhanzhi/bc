package com.cyberpeace.oj.rtks.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.oj.common.model.BaseTable;
import lombok.Data;

@Data
@TableName("rtks_role")
public class Role extends BaseTable {

    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    private String name;

    private String description;

    /**
     * 岗位所属层级的id
     */
    @TableField(value = "level_id")
    private String levelId;

    /**
     * 技能体系
     */
    private int category;

}
