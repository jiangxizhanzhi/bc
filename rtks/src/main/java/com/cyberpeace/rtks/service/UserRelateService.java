package com.cyberpeace.rtks.service;

import com.cyberpeace.rtks.dao.entity.UserRole;

/**
 * 用户与Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */
public interface UserRelateService {

    /**
     * 岗位编号
     *
     * @param roleId   岗位编号
     * @param userId   用户编号
     * @param userName 用户名称
     * @return
     * @throws
     * @see String
     * @see String
     * @see String
     * @see UserRole
     */
    UserRole roleRelateUser(String roleId, String userId, String userName);

    /**
     * 查询用户和岗位绑定信息
     *
     * @param userId
     * @return
     * @throws
     * @see String
     * @see UserRole
     */
    UserRole getUserRoleInfo(String userId);
}
