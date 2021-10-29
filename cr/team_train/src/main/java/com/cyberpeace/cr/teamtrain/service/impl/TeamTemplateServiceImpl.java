package com.cyberpeace.cr.teamtrain.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.teamtrain.dao.entity.BaseRole;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTemplate;
import com.cyberpeace.cr.teamtrain.dao.mapper.TeamRoomMapper;
import com.cyberpeace.cr.teamtrain.dao.mapper.TeamTemplateMapper;
import com.cyberpeace.cr.teamtrain.model.template.BaseInfoData;
import com.cyberpeace.cr.teamtrain.model.template.BaseInfoOutput;
import com.cyberpeace.cr.teamtrain.model.template.TeamTemplateReq;
import com.cyberpeace.cr.teamtrain.model.template.TeamTrainTemplateOutput;
import com.cyberpeace.cr.teamtrain.service.TeamTemplateService;
import com.google.common.collect.Lists;

/**
 *
 */
@Service
public class TeamTemplateServiceImpl extends ServiceImpl<TeamTemplateMapper, TeamTemplate> implements TeamTemplateService {

	@Autowired
	private TeamTemplateMapper teamTemplateMapper;
	@Autowired
	private TeamRoomMapper teamTrainTemplateMapper;
	@Override
	public ListRsp<TeamTrainTemplateOutput> page(TeamTemplateReq listReq) {
		Page page = listReq.toPageParam();

		IPage<BaseInfoData> data = teamTemplateMapper.listTeamTemplate(page, listReq);

		ListRsp ret = new ListRsp();

		List<TeamTrainTemplateOutput> teamTrainTemplateOutputList = Lists.newArrayList();

		for (BaseInfoData baseInfoData : data.getRecords()) {
			teamTrainTemplateOutputList.add(getTeamTrainTemplateOutputByBaseInfoData(baseInfoData));
		}

		ret.setList(teamTrainTemplateOutputList);
		ret.setTotal(data.getTotal());
		ret.setLastPage(data.getPages());
		ret.setCurrentPage(data.getCurrent());

		return ret;
	}

	public TeamTrainTemplateOutput
			getTeamTrainTemplateOutputByBaseInfoData(BaseInfoData baseInfoData) {
		TeamTrainTemplateOutput teamTrainTemplateOutput = new TeamTrainTemplateOutput();

		BaseInfoOutput baseInfoOutput = new BaseInfoOutput(baseInfoData);

		teamTrainTemplateOutput.setBaseInfo(baseInfoOutput);
		String roleString = baseInfoData.getRoleConfig();

		List<BaseRole> teamTrainTemplateRoleList = JsonUtil
				.toJsonObjectArray(roleString, BaseRole.class);
		teamTrainTemplateOutput.setRoleStructure(teamTrainTemplateRoleList);
		return teamTrainTemplateOutput;
	}

}
