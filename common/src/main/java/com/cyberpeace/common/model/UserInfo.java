package com.cyberpeace.common.model;

import com.google.common.collect.Lists;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserInfo implements Serializable {

    /**
     * 用户id，不要使用自增数字编号（必须）
     */
    private String userId;

    /**
     * 用户姓名（对前端显示）
     */
    private String userName;

    /**
     * 头像地址
     */
    private String avatarUrl;

    /**
     * 组织id
     */
    private String organizationId;

    /**
     * 组织名
     */
    private String organizationData;

    /**
     * 用户角色
     */
    private List<String> roles = Lists.newArrayList();

    /**
     * true表示用户为管理员
     */
    private boolean admin;

    /**
     * true表示用户是超级用户
     */
    private boolean superuser;

}
