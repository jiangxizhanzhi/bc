package com.cyberpeace.oj.practice.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import com.cyberpeace.oj.practice.constant.Auth;
import com.cyberpeace.oj.practice.constant.Share;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
@TableName("practice_set")
public class PracticeSet extends BaseTable {

    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    private boolean builtin;

    /**
     * 名称
     */
    private String name;

    /**
     * 封面
     */
    private String logo;

    /**
     * 练习类型
     */
    private Integer practiceType;

    /**
     * 是否公开
     */
    @TableField("public")
    private boolean isPublic = false;

    /**
     * 创建者
     */
    private String createUser;

    /**
     * 授权访问
     */
    private Integer auth = Auth.CUSTOM.getCode();

    /**
     * 授权操作
     */
    private Integer share = Share.CUSTOM.getCode();

    /**
     * 已授权访问组织
     */
    private String authOrganization = "[]";

    /**
     * 已授权访问用户
     */
    private String shareTeachers = "[]";

}
