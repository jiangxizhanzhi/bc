package com.cyberpeace.oj.rtks.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.oj.rtks.controller.req.RoleRelatedAddReq;
import com.cyberpeace.oj.rtks.dao.entity.RoleRelated;

import java.util.List;

public interface RoleRelatedService extends IService<RoleRelated> {

    /**
     * 给定清单类型 利用请求中的岗位id和清单中id列表创建关联关系
     */
    void addRoleRelated(String roleId, int type, List<String> relatedIds);

    /**
     * 给定岗位id 清单类型 获取清单中的id列表
     */
    List<String> getRelatedIds(String roleId, int type);

}
