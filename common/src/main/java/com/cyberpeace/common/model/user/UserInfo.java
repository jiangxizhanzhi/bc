package com.cyberpeace.common.model.user;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;

import lombok.Data;

/**
 * 用户信息
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class UserInfo implements Serializable {

    /**
     * 用户id，不要使用自增数字编号（必须）
     *
     * @see String
     */
    private String userId;

    /**
     * 姓名（文字显示）
     *
     * @see String
     */
    private String name;

    /**
     * 用户名（对前端显示，登录使用）
     *
     * @see String
     */
    private String userName;

    /**
     * 头像地址
     *
     * @see String
     */
    private String avatarUrl;

    /**
     * 组织id
     *
     * @see String
     */
    private String organizationId;

    /**
     * 组织名
     *
     * @see String
     */
    private String organizationData;

    /**
     * 用户角色(用来做权限校验，和rtks的岗位角色不相关)
     *
     * @see List<JSONObject>
     */
    private List<JSONObject> roles = Lists.newArrayList();

    /**
     * 位置id
     *
     * @see String
     */
    private String positionId;

    /**
     * 位置名称
     *
     * @see String
     */
    private String positionName;

    /**
     * true表示用户为管理员
     */
    private boolean admin;

    /**
     * true表示用户是超级用户
     */
    private boolean superuser;

    /**
     * 岗位编号
     *
     * @see String
     */
    private String roleId;

    /**
     * 当前岗位名称（rtks相关，与训练的角色无关）
     *
     * @see String
     */
    private String roleName;

    /**
     * 组织结构信息
     *
     * @see List<List<String>>
     */
    private List<List<String>> orgpaths;

    /**
     * 用户菜单权限
     *
     * @see Set<String>
     */
    private Set<String> permissions;

}
