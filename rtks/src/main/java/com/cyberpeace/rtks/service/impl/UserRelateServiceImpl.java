package com.cyberpeace.rtks.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.rtks.constant.RtksBusinessErrorCode;
import com.cyberpeace.rtks.dao.entity.Role;
import com.cyberpeace.rtks.dao.entity.UserRole;
import com.cyberpeace.rtks.dao.mapper.RoleMapper;
import com.cyberpeace.rtks.dao.mapper.UserRoleMapper;
import com.cyberpeace.rtks.service.UserRelateService;

/**
 * 用户与Service实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see UserRelateService
 * @since 1.0.0
 */
@Service
public class UserRelateServiceImpl implements UserRelateService {

    /**
     * 用户岗位Mapper
     *
     * @see UserRoleMapper
     */
    @Autowired
    private UserRoleMapper userRoleMapper;

    /**
     * 岗位Mapper
     *
     * @see RoleMapper
     */
    @Autowired
    private RoleMapper roleMapper;

    /**
     * 岗位与用户
     *
     * @param roleId   岗位id
     * @param userId   用户id
     * @param userName 用户名称
     * @return {@code UserRole }
     * @throws
     * @see String
     * @see String
     * @see String
     * @see UserRole
     */
    @Override
    public UserRole roleRelateUser(String roleId, String userId, String userName) {
        Role role = roleMapper.selectById(roleId);
        if (null == role) {
            throw new BusinessException(RtksBusinessErrorCode.ROLE_NOT_EXIST);
        }
        UserRole userRole = new UserRole();
        userRole.setRoleId(roleId);
        userRole.setRoleName(role.getName());
        userRole.setUserId(userId);
        userRole.setUserName(userName);

        UserRole existUserRole = userRoleMapper.selectById(userId);
        if (null == existUserRole) {
            userRoleMapper.insert(userRole);
        } else {
            userRoleMapper.updateById(userRole);
        }
        return userRole;
    }

    /**
     * 获取用户岗位信息
     *
     * @param userId 用户id
     * @return {@code UserRole }
     * @throws
     * @see String
     * @see UserRole
     */
    @Override
    public UserRole getUserRoleInfo(String userId) {
        UserRole userRole = userRoleMapper.selectById(userId);
        if (null == userRole) {
            return null;
        }

		Role role = roleMapper.selectById(userRole.getRoleId());
		if (null == role) {
			return null;
		}
		// 获取实时的岗位名称
		userRole.setRoleName(role.getName());
        return userRole;
    }
}
