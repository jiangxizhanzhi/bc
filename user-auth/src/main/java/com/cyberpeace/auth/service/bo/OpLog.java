package com.cyberpeace.auth.service.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 测评项
 * @author SN
 * @date 2023/11/28
 */
@Data
@TableName("op_log")
public class OpLog extends BaseTable {

    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;


    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    String username;


    String createUserId;
    /**
     * 姓名
     */
    @ApiModelProperty("姓名")
    @TableField(value = "`name`")
    String name;

    /**
     * 模块
     */
    @ApiModelProperty("模块")
    Integer module;

    @ApiModelProperty("操作类型")
    Integer method;

    @ApiModelProperty("操作对象")
    String target;

    @ApiModelProperty("详情")
    String detail;
}
