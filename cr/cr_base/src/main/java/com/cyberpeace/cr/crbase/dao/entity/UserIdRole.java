package com.cyberpeace.cr.crbase.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 用户id岗位
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
@AllArgsConstructor
public class UserIdRole {

    /**
     * 用户id
     */
    private String userId;
    /**
     * 岗位
     */
    private CrRole role;
}
