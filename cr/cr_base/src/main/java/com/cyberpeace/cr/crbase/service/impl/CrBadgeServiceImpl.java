package com.cyberpeace.cr.crbase.service.impl;

import java.util.List;

import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.cr.crbase.dao.entity.CrBadge;
import com.cyberpeace.cr.crbase.dao.mapper.CrBadgeMapper;
import com.cyberpeace.cr.crbase.service.CrBadgeService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CrBadgeServiceImpl implements CrBadgeService {

	@Autowired
	private CrBadgeMapper crBadgeMapper;

	@Override
	public void createBatch(String businessId, Integer type, List<String> userIds) {
		List<CrBadge> crBadgeList = Lists.newArrayList();
		for (String userId : userIds) {
			CrBadge badge = new CrBadge();
			badge.setBusinessId(businessId);
			badge.setUserId(userId);
			badge.setType(type);

			crBadgeList.add(badge);
		}
		crBadgeMapper.insertBatch(crBadgeList);
	}

	@Override
	public Boolean ifExist(String businessId, Integer type, String userId) {
		return crBadgeMapper.existByBusinessIdAndTypeAndUserId(businessId, type, userId);
	}

	@Override
	public void removeBadge(String businessId, Integer type, String userId) {
		crBadgeMapper.removeBatchByBusinessIdAndTypeAndUserId(businessId, type, userId);
	}
}
