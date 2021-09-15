package com.cyberpeace.cr.teamtrain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTrainTemplate;
import com.cyberpeace.cr.teamtrain.dao.mapper.TeamTrainTemplateMapper;
import com.cyberpeace.cr.teamtrain.dao.mapper.TeamTrainTemplateRoleMapper;
import com.cyberpeace.cr.teamtrain.service.TeamTrainTemplateService;

@Service
public class TeamTrainTemplateServiceImpl extends ServiceImpl<TeamTrainTemplateMapper, TeamTrainTemplate> implements TeamTrainTemplateService {

	@Autowired
	private TeamTrainTemplateRoleMapper teamTrainTemplateRoleMapper;

}
