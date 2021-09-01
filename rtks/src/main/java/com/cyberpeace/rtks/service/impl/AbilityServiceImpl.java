package com.cyberpeace.rtks.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.exception.ResourceNotFoundException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.rtks.controller.req.AbilityListReq;
import com.cyberpeace.rtks.dao.entity.Ability;
import com.cyberpeace.rtks.dao.mapper.AbilityMapper;
import com.cyberpeace.rtks.service.AbilityService;

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
	public void checkIds(List<String> ids) {
		List<String> existingIds = abilityMapper.filterIds(ids);
		for (String id : ids) {
			if (!existingIds.contains(id)) {
				throw new ResourceNotFoundException("能力" + id + "不存在");
			}
		}
	}
}
