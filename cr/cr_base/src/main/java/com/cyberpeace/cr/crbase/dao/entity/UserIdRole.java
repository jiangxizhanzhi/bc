package com.cyberpeace.cr.crbase.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 用户id角色
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
public class UserIdRole {

    /**
     * 用户id
     *
     * @see String
     */
    private String userId;
    /**
     * 角色
     *
     * @see CrRole
     */
    private CrRole role;
}
