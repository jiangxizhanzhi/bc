package com.cyberpeace.cr.crbase.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
@TableName("cr_title_level")
public class CrTitleLevel extends BaseTable {
    @JsonIgnore
    private Integer id;

    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    private String name;

    private String createUserId;
}
