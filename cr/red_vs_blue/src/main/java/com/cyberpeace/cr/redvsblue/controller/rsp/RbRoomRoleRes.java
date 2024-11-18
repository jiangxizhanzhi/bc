package com.cyberpeace.cr.redvsblue.controller.rsp;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.cr.crbase.service.context.UserData;
import com.google.common.collect.Lists;
import lombok.Data;

/**
 * RB房间角色结果
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class RbRoomRoleRes implements Serializable {

    /**
     * 资源id
     *
     * @see String
     */
    private String resourceId;

    /**
     * 名称
     *
     * @see String
     */
    private String name;

    /**
     * 角色组
     *
     * @see Integer
     */
    private Integer roleGroup;

    /**
     * 用户
     *
     * @see List<String>
     */
    private List<String> users;

    /**
     * 用户数据
     *
     * @see List<UserData>
     */
    private List<UserData> usersData;

    /**
     * 子级
     *
     * @see List<RbRoomRoleRes>
     */
    private List<RbRoomRoleRes> children = Lists.newArrayList();

}
