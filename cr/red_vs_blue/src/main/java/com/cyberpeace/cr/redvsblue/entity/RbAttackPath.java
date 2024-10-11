package com.cyberpeace.cr.redvsblue.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("cr_rb_attack_path")
public class RbAttackPath extends BaseTable {
    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    @ApiModelProperty("模板id")
    private String templateId;

    @ApiModelProperty("场景id")
    private String sceneId;

    @ApiModelProperty("起始节点id")
    private String sourceNodeId;

    @ApiModelProperty("起始节点名称")
    private String sourceNodeName;

    @ApiModelProperty("目标节点id")
    private String targetNodeId;

    @ApiModelProperty("目标节点名称")
    private String targetNodeName;
}
