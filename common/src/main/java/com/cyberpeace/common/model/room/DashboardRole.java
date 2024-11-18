package com.cyberpeace.common.model.room;

import lombok.Data;

/**
 * 仪表板角色
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class DashboardRole {
    /**
     * 岗位类型
     *
     * @see String
     */
    private String roleType;
    /**
     * 岗位
     *
     * @see String
     */
    private String role;
    /**
     * 岗位代号
     *
     * @see Integer
     */
    private Integer roleCode;
}
