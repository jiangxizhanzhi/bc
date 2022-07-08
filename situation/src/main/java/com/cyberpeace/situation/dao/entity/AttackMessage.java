package com.cyberpeace.situation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;

@Data
@TableName("situation_attack_message")
public class AttackMessage extends BaseTable {

    @TableId(value = "resource_id")
    private String resourceId;

    private String name;

    private String description;

    private String sourceId;

    private String sourceName;

    private String targetId;

    private String targetName;

    private Integer intervalTime;

    private Boolean lookAtTarget;

    private String lineStyle;

    private String mediaAnimation;

}
