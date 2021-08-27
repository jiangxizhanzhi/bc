package com.cyberpeace.rtks.service;

import com.cyberpeace.rtks.dao.entity.UserRole;

public interface UserRelateService {

    /**
     * 岗位编号
     * @param roleId 岗位编号
     * @param userId 用户编号
     * @return
     */
    UserRole roleRelateUser(String roleId, String userId, String userName);

    /**
     * 查询用户和岗位绑定信息
     * @param userId
     * @return
     */
    UserRole getUserRoleInfo(String userId);
}
