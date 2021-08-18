package com.cyberpeace.oj.rtks.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.oj.rtks.controller.req.RoleRelatedInput;
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
    public void addRoleRelated(String roleId, List<RoleRelatedInput> roleRelatedInputList) {
        for (RoleRelatedInput relatedInput : roleRelatedInputList) {
            // todo 校验输入的有效性
            RoleRelated roleRelated = new RoleRelated();
            roleRelated.setRoleId(roleId);
            roleRelated.setRelatedId(relatedInput.getRelatedId());
            roleRelated.setType(relatedInput.getType());
            save(roleRelated);
        }
    }

    @Override
    public Map<Integer, List<RoleRelated>> getByRoleId(String roleId) {
        Map<Integer, List<RoleRelated>> ret = new HashMap<>();

        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("role_id", roleId);
        List<RoleRelated> roleRelatedList = listByMap(columnMap);

        for (RoleRelated roleRelated : roleRelatedList) {
            int type = roleRelated.getType();
            if (!ret.containsKey(type)) {
                ret.put(type, new ArrayList<>());
            }
            List<RoleRelated> items = ret.get(type);
            items.add(roleRelated);
        }
        return ret;
    }

}