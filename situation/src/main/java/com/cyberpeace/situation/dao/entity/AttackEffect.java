package com.cyberpeace.situation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;

@Data
@TableName("situation_attack_effect")
public class AttackEffect extends BaseTable {

    @TableId(type = IdType.UUID,value = "resource_id")
    private String resourceId;

    private String elementId;

    private String areaId;

    private Integer mediaType;

    private String mediaUrl;

    private String description;

    private String lineStyle;

    private Integer direction;

}
