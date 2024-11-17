package com.cyberpeace.situation.dao.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;

@Data
@TableName("situation_vuln")
public class Vuln extends BaseTable {
    @TableId(value = "resource_id")
    private String resourceId;

    // 漏洞编号
    private String number;

    // 漏洞名称
    private String name;

    // cvss评分
    private Double cvss;

    // 漏洞类型 0 无， 1 低， 2 中， 3 高， 4 严重
    private Integer type;

    // 适用平台
    private String application;
}
