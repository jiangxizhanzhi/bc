package com.cyberpeace.oj.rtks.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.oj.common.model.ListRsp;
import com.cyberpeace.oj.rtks.controller.req.ListReq;
import com.cyberpeace.oj.rtks.controller.req.RoleAddReq;
import com.cyberpeace.oj.rtks.dao.entity.Role;

public interface RoleService extends IService<Role> {
    /**
     * 根据名称模糊查询岗位
     */
    ListRsp<Role> searchRolesByName(ListReq<Role> listReq);

    /**
     * 数据库中新建一条岗位记录
     */
    Role addRole(RoleAddReq roleAddReq);


    /**
     * 数据库中更新一条岗位记录
     */
    Role modifyRole(RoleAddReq roleAddReq);

}
