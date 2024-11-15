package com.cyberpeace.situation.dao.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;

@Data
@TableName("situation_tool_vuln")
public class ToolVuln extends BaseTable {
    @TableId(value = "resource_id")
    private String resourceId;

    private String toolNmae;

    private String vulnIds;

    private Integer toolType;

    private String describe;
}
