package com.cyberpeace.situation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;

@Data
@TableName("situation_dynamic_scheme")
public class DynamicScheme extends BaseTable {

    @TableId(type = IdType.UUID,value = "resource_id")
    private String resourceId;

    private String sceneId;

    private String name;

    private String schemeData;

}
