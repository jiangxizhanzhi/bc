package com.cyberpeace.cr.crbase.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 用户id角色
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
@AllArgsConstructor
public class UserIdRole {

    /**
     * 用户id
     */
    private String userId;
    /**
     * 角色
     */
    private CrRole role;
}
