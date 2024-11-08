package com.cyberpeace.common.model.room;

import lombok.Data;

/**
 * 挡泥板岗位
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class DashboardRole {
    /**
     * 岗位类型
     */
    private String roleType;
    /**
     * 岗位
     */
    private String role;
    /**
     * 岗位代号
     */
    private Integer roleCode;
}
