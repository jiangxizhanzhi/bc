package com.cyberpeace.cr.redvsblue.dao.entity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;

/**
 * cr标记
 *
 * @author SN
 * @date 2023/05/24
 */
@Data
@TableName(value = "cr_unite_business_node")
public class CrUniteBusinessNode extends BaseTable {

    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    @JSONField(name = "task_id")
    private String taskId;

    @JSONField(name = "number_id")
    private String numberId;

    @JSONField(name = "ident_relationship")
    private String identRelationship;

    @JSONField(name = "number_name")
    private String numberName;

    @JSONField(name = "node_type")
    private Integer nodeType;

    @TableField(exist = false)
    private JSONArray attackTools;

    @TableField(exist = false)
    private Boolean isAttacker;
}
