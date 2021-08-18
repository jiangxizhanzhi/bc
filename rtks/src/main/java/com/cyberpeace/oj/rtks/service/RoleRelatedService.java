package com.cyberpeace.oj.rtks.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.oj.rtks.controller.req.RoleRelatedInput;
import com.cyberpeace.oj.rtks.dao.entity.RoleRelated;

import java.util.List;
import java.util.Map;

public interface RoleRelatedService extends IService<RoleRelated> {

    void addRoleRelated(String roleId, List<RoleRelatedInput> roleRelatedInputList);

    Map<Integer, List<RoleRelated>> getByRoleId(String roleId);

}
