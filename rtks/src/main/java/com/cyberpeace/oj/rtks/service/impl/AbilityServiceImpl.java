package com.cyberpeace.oj.rtks.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.oj.common.model.ListRsp;
import com.cyberpeace.oj.common.util.PageUtil;
import com.cyberpeace.oj.rtks.controller.req.AbilityListReq;
import com.cyberpeace.oj.rtks.dao.entity.Ability;
import com.cyberpeace.oj.rtks.dao.mapper.AbilityMapper;
import com.cyberpeace.oj.rtks.service.AbilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AbilityServiceImpl extends ServiceImpl<AbilityMapper, Ability> implements AbilityService {

    @Autowired
    private AbilityMapper abilityMapper;

    @Override
    public ListRsp<Ability> page(AbilityListReq abilityListReq) {
        Page page = abilityListReq.toPageParam();

        IPage<Ability> result = abilityMapper.listAbility(page, abilityListReq.getPattern());

        return PageUtil.getListResult(result);
    }

}
