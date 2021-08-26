package com.cyberpeace.oj.rtks.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.oj.rtks.controller.req.AbilityListReq;
import com.cyberpeace.oj.rtks.dao.entity.Ability;
import com.cyberpeace.oj.rtks.dao.mapper.AbilityMapper;
import com.cyberpeace.oj.rtks.service.AbilityService;

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

	@Override
	public List<String> filterIds(List<String> ids) {
		return abilityMapper.filterIds(ids);
	}
}
