package com.cyberpeace.oj.rtks.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.oj.rtks.dao.entity.RoleRelated;
import com.cyberpeace.oj.rtks.dao.mapper.RoleRelatedMapper;
import com.cyberpeace.oj.rtks.service.RoleRelatedService;

@Service
public class RoleRelatedServiceImpl extends ServiceImpl<RoleRelatedMapper, RoleRelated> implements RoleRelatedService {

	@Override
	public void addRoleRelated(String roleId, int type, List<String> relatedIds) {
		List<RoleRelated> toSaveList = new ArrayList<>();
		for (String relatedId : relatedIds) {
			RoleRelated roleRelated = new RoleRelated();
			roleRelated.setRoleId(roleId);
			roleRelated.setRelatedId(relatedId);
			roleRelated.setType(type);
			toSaveList.add(roleRelated);
		}
		saveBatch(toSaveList);
	}

	@Override
	public List<String> getRelatedIds(String roleId, int type) {
		List<RoleRelated> roleRelatedList = this.getBaseMapper().listRoleRelated(roleId, type);
		List<String> ret = new ArrayList<>();
		for (RoleRelated roleRelated : roleRelatedList) {
			ret.add(roleRelated.getRelatedId());
		}
		return ret;
	}

}