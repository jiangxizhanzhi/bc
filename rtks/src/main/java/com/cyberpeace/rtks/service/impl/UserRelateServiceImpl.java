package com.cyberpeace.rtks.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.rtks.dao.entity.Role;
import com.cyberpeace.rtks.dao.entity.UserRole;
import com.cyberpeace.rtks.dao.mapper.RoleMapper;
import com.cyberpeace.rtks.dao.mapper.UserRoleMapper;
import com.cyberpeace.rtks.service.UserRelateService;

@Service
public class UserRelateServiceImpl implements UserRelateService {

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public UserRole roleRelateUser(String roleId, String userId, String userName) {
        Role role = roleMapper.selectById(roleId);
        if (null == role) {
            throw new UnsupportedOperationException("岗位非法！");
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

    @Override
    public UserRole getUserRoleInfo(String userId) {
        UserRole userRole = userRoleMapper.selectById(userId);
        return userRole;
    }
}
