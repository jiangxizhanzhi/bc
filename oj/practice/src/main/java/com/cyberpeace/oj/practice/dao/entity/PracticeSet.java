package com.cyberpeace.oj.practice.dao.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.cyberpeace.oj.practice.dao.constants.Auth;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@TableName("practice_practiceset")
public class PracticeSet {

    @JsonIgnore
    private Integer id;

    @TableId(type = IdType.UUID, value = "resource_id")
    @ApiModelProperty(hidden = true)
    private String resourceId;

    private boolean builtin;

    private Integer status = 1;

    /**
     * 名称
     */
    private String name;

    /**
     * 封面 TODO 如何上传?
     */
    private String logo;

    /**
     * 练习类型
     */
    private Integer type;

    /**
     * 是否公开
     */
    @TableField("public")
    private boolean isPublic = false;

    /**
     * 授权访问
     */
    private Integer auth = Auth.CUSTOM.ordinal();

    /**
     * 授权操作
     */
    private Integer share = Auth.CUSTOM.ordinal();

    /**
     * 已授权访问组织
     */
    private String authOrganization = "[]";

    /**
     * 已授权访问用户
     */
    private String shareTeachers = "[]";

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 原表存在的不能为null fields
     */
    private Date lastEditTime = new Date();
}
