package com.cyberpeace.situation.dao.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;

@Data
@TableName("situation_defense")
public class Defense extends BaseTable {
    @TableId(value = "resource_id")
    private String resourceId;

    private String sceneId;

    private String nodeId;

    private String vulnId;

    private String deacription;
}
