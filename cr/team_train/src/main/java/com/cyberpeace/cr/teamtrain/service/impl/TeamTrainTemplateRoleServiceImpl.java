package com.cyberpeace.cr.teamtrain.service.impl;

import java.util.Collection;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTrainTemplateRole;
import com.cyberpeace.cr.teamtrain.dao.mapper.TeamTrainTemplateRoleMapper;
import com.cyberpeace.cr.teamtrain.service.TeamTrainTemplateRoleService;

@Service
public class TeamTrainTemplateRoleServiceImpl extends ServiceImpl<TeamTrainTemplateRoleMapper, TeamTrainTemplateRole> implements TeamTrainTemplateRoleService {

	@Override
	@Transactional(rollbackFor = { Exception.class }, transactionManager = "crTransactionManager")
	public boolean saveBatch(Collection<TeamTrainTemplateRole> entityList) {
		return super.saveBatch(entityList);
	}

}
