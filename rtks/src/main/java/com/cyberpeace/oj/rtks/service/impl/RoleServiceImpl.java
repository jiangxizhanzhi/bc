package com.cyberpeace.oj.rtks.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.oj.common.exception.ResourceNotFoundException;
import com.cyberpeace.oj.common.model.ListRsp;
import com.cyberpeace.oj.common.util.PageUtil2;
import com.cyberpeace.oj.rtks.controller.req.ListReq;
import com.cyberpeace.oj.rtks.controller.req.RoleAddReq;
import com.cyberpeace.oj.rtks.dao.entity.Level;
import com.cyberpeace.oj.rtks.dao.entity.Role;
import com.cyberpeace.oj.rtks.dao.mapper.RoleMapper;
import com.cyberpeace.oj.rtks.service.LevelService;
import com.cyberpeace.oj.rtks.service.RoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private LevelService levelService;

    @Override
    public ListRsp<Role> searchRolesByName(ListReq<Role> listReq) {
        IPage<Role> page = this.getBaseMapper().selectPage(listReq.toPageParam(),
                new QueryWrapper<Role>().like("name", listReq.getKeyword()));
        return PageUtil2.getListResult(page);
    }

    @Override
    public Role addRole(RoleAddReq roleAddReq) {
        roleAddReq.setResourceId(null);

        ensureLevelIdValid(roleAddReq);

        Role role = new Role();
        BeanUtils.copyProperties(roleAddReq, role);

        save(role);

        return role;
    }

    @Override
    public Role modifyRole(RoleAddReq roleAddReq) {
        ensureLevelIdValid(roleAddReq);

        Role role = new Role();
        BeanUtils.copyProperties(roleAddReq, role);

        updateById(role);

        return role;
    }

    @Override
    public void ensureRoleExists(String roleId) {
        Role role = getById(roleId);
        if (role == null) {
            throw new ResourceNotFoundException("岗位" + roleId + "未找到");
        }
    }

    /**
     * 检查输入的level_id是否合法
     */
    private void ensureLevelIdValid(RoleAddReq roleAddReq) {
        String levelId = roleAddReq.getLevelId();
        Level level = levelService.getById(roleAddReq.getLevelId());
        if (level == null) {
            throw new ResourceNotFoundException("层级" + levelId + "未找到");
        }
    }
}