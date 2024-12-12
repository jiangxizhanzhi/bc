package com.cyberpeace.cr.crbase.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户id岗位
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
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
