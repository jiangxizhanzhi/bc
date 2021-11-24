package com.cyberpeace.cr.teamtrain.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.dao.entity.CrRoleNodesData;
import com.cyberpeace.cr.crbase.dao.mapper.CrRoleMapper;
import com.cyberpeace.cr.crbase.service.BusinessTemplateHandler;
import com.cyberpeace.cr.crbase.service.impl.bo.BaseInfo;
import com.cyberpeace.cr.crbase.service.impl.bo.BasicTemplate;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoom;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTemplate;
import com.cyberpeace.cr.teamtrain.dao.mapper.TeamRoomMapper;
import com.cyberpeace.cr.teamtrain.service.TeamTemplateService;
import com.cyberpeace.cr.teamtrain.service.context.TeamTrainTemplateContext;

@Service
public class TeamBusinessTemplateHandler extends BusinessTemplateHandler {

	@Autowired
	TeamTemplateService teamTemplateService;

	@Autowired
	TeamRoomMapper teamRoomMapper;

	@Autowired
	CrRoleMapper crRoleMapper;

	@Override
	public BusinessTemplate.BusinessType setBusinessType() {
		return BusinessTemplate.BusinessType.TRAINING_TEAM;
	}

	@Override
	public String saveRealBusiness(BasicTemplate basicTemplate, String businessId) {

		TeamTrainTemplateContext teamTrainTemplateContext = BeanUtils.copy(basicTemplate,
				TeamTrainTemplateContext.class);
		TeamTemplate teamTemplate = new TeamTemplate();

		BaseInfo baseInfo = basicTemplate.getBaseInfo();

		// 保存团队训练模板
		teamTemplate.setBusinessTemplateId(businessId);
		teamTemplate.setPreSkills(JsonUtil.toJsonString(baseInfo.getPreSkill()));
		teamTemplate.setTrainSkills(JsonUtil.toJsonString(baseInfo.getTrainSkill()));
		teamTemplate.setSubType(baseInfo.getCategory());
		teamTemplate.setPeopleNumber(baseInfo.getPeopleNumber());
		teamTemplate.setDurationTime(baseInfo.getDurationTime());
		teamTemplate.setRoleConfig(teamTrainTemplateContext.getRoleList());

		teamTemplate.setTimeLineConfig(
				JsonUtil.toJsonString(teamTrainTemplateContext.getTimeLineConfig()));

		teamTemplateService.save(teamTemplate);

		return teamTemplate.getResourceId();
	}

	@Override
	public String getRoomIdBySceneManagerId(String sceneManagerId) {
		TeamRoom room = teamRoomMapper.selectbySceneManagerId(sceneManagerId);
		if (room == null) {
			throw new RuntimeException();
		}
		return room.getResourceId();
	}

	@Override
	public CrRoleNodesData getNodes(String userId, String roomId, String roleId) {
		CrRole role = crRoleMapper.getCrRoleByRoomIdAndUserId(roomId, userId);
		if (role.getRoleGroup().equals(CrRole.TeamRoleGroupCode.TEACHER.getValue())) {
			if (StringUtils.isNotBlank(roleId)) {
				// 教员查看学员角色的战争迷雾
				CrRole fakeRole = crRoleMapper.selectById(roleId);
				if (fakeRole.getRoomId().equals(roomId)) {
					return JsonUtil.toJsonObject(fakeRole.getNodes(), CrRoleNodesData.class);
				}
			} else {
				// 教员所有节点可见
				return null;
			}
		}
		return JsonUtil.toJsonObject(role.getNodes(), CrRoleNodesData.class);
	}

}
