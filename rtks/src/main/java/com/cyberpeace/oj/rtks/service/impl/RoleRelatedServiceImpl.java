package com.cyberpeace.oj.rtks.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.oj.rtks.controller.req.RoleRelatedAddReq;
import com.cyberpeace.oj.rtks.dao.entity.RoleRelated;
import com.cyberpeace.oj.rtks.dao.mapper.RolerelatedMapper;
import com.cyberpeace.oj.rtks.service.RoleRelatedService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoleRelatedServiceImpl extends ServiceImpl<RolerelatedMapper, RoleRelated> implements RoleRelatedService {


    @Override
    public void addRoleRelated(String roleId, int type, List<String> relatedIds) {
        for (String relatedId : relatedIds) {
            RoleRelated roleRelated = new RoleRelated();
            roleRelated.setRoleId(roleId);
            roleRelated.setRelatedId(relatedId);
            roleRelated.setType(type);
            save(roleRelated);
        }
    }

    @Override
    public List<String> getRelatedIds(String roleId, int type) {
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("role_id", roleId);
        columnMap.put("type", type);
        List<RoleRelated> roleRelatedList = listByMap(columnMap);
        List<String> ret = new ArrayList<>();
        for (RoleRelated roleRelated : roleRelatedList) {
            ret.add(roleRelated.getRelatedId());
        }
        return ret;
    }

}