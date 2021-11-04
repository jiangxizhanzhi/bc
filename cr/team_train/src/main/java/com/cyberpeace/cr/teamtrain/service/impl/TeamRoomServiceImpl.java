package com.cyberpeace.cr.teamtrain.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.exception.ResourceNotFoundException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.dao.mapper.CrRoleMapper;
import com.cyberpeace.cr.crbase.model.role.BaseRole;
import com.cyberpeace.cr.crbase.model.role.RoleQuery;
import com.cyberpeace.cr.crbase.model.role.RoleUsers;
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoom;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTemplate;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTrainTemplateRole;
import com.cyberpeace.cr.teamtrain.dao.mapper.TeamRoomMapper;
import com.cyberpeace.cr.teamtrain.model.room.RoomRole;
import com.cyberpeace.cr.teamtrain.model.room.TeamRoomData;
import com.cyberpeace.cr.teamtrain.model.room.TeamRoomRsp;
import com.cyberpeace.cr.teamtrain.model.template.BaseInfo;
import com.cyberpeace.cr.teamtrain.service.TeamRoomService;
import com.cyberpeace.cr.teamtrain.service.TeamTemplateService;
import com.cyberpeace.cr.teamtrain.service.context.ListQuery;
import com.cyberpeace.cr.teamtrain.service.context.TeamTrainRoomContext;
import com.cyberpeace.cr.teamtrain.service.context.TeamTrainTemplateContext;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.integratation.model.SceneConfig;
import com.google.common.collect.Lists;

/**
 *
 */
@Service
public class TeamRoomServiceImpl extends ServiceImpl<TeamRoomMapper, TeamRoom> implements TeamRoomService {

	@Autowired
	private TeamRoomMapper teamRoomMapper;

	@Autowired
	private CrRoleMapper crRoleMapper;

	@Autowired
	TeamTemplateService teamTemplateService;

	@Autowired
	BusinessTemplateService businessTemplateService;

	@Autowired
	CrRoleService crRoleService;

	@Autowired
	LocalSceneService localSceneService;

	@Autowired
	@Qualifier("ojTransactionTemplate")
	private TransactionTemplate transactionTemplate;

	@Override
	public TeamRoomRsp getTeamRoomRspByRoomId(String resourceId) {
		String userId = UserInfoContext.getUserContext().getUserId();
		TeamRoomData teamRoomdata = teamRoomMapper.getTeamRoomRspByRoomId(resourceId);
		if (teamRoomdata == null) {
			throw new ResourceNotFoundException(resourceId);
		}

		RoleQuery roleQuery = new RoleQuery();
		roleQuery.setRoomId(teamRoomdata.getResourceId());

		List<CrRole> crRoles = crRoleMapper.selectCrRoleByRoomId(roleQuery);
		teamRoomdata.setTrainingStatus(setTrainingStatus(teamRoomdata));

		TeamRoomRsp teamRoomRsp = getTeamRoomOutputFromTeamRoomData(teamRoomdata);
		teamRoomRsp.setTrainingRoleData(getSelfRole(crRoles, userId));
		teamRoomRsp.setRolesData(crRoles);

		return teamRoomRsp;
	}

	private int setTrainingStatus(TeamRoomData teamRoomdata) {
		if (null == teamRoomdata.getSceneStartTime()) {
			return TeamRoom.TrainingStatusCode.UNOPENED.getCode();
		} else if (null == teamRoomdata.getSceneEndTime()) {
			return TeamRoom.TrainingStatusCode.OPENING.getCode();
		} else {
			return TeamRoom.TrainingStatusCode.ENDED.getCode();
		}
	}

	// 获取单个实例中自己的角色，只包括自己角色的基本信息
	private BaseRole getSelfRole(List<CrRole> crRoles, String userId) {
		BaseRole baseRole = new BaseRole();
		crRoles.forEach(crRole -> {
			if (crRole.getUserIds().contains(userId)) {
				baseRole.setResourceId(crRole.getResourceId());
				baseRole.setName(crRole.getName());

				baseRole.setDescription(crRole.getDescription());
				baseRole.setRoleGroup(crRole.getRoleGroup());
			}
		});

		return baseRole;
	}
	// 先获取实例列表中自己的角色，再获取所有的学员角色

	private BaseRole getSelfRoleUsers(List<CrRole> crRoles, String userId) {
		BaseRole baseRole = new BaseRole();
		List<RoleUsers> roleUsersList = Lists.newArrayList();
		crRoles.forEach(crRole -> {
			if (crRole.getUserIds().contains(userId)) {
				baseRole.setResourceId(crRole.getResourceId());
				baseRole.setName(crRole.getName());

				baseRole.setDescription(crRole.getDescription());
				baseRole.setRoleGroup(crRole.getRoleGroup());
			}
			if (crRole.getRoleGroup().intValue() != CrRole.RoleGroupCodeInteger.TEACHER
					.getValue()) {
				RoleUsers roleUsers = new RoleUsers();
				roleUsers.setRoleName(crRole.getName());
				roleUsers.setRoleGroup(crRole.getRoleGroup());
				roleUsers.setUsers(JsonUtil.toJsonObjectArray(crRole.getUserIds(), String.class));
				roleUsersList.add(roleUsers);
			}
		});

		baseRole.setRoleDataList(roleUsersList);
		return baseRole;
	}

	@Override
	public TeamTemplate create(TeamTrainRoomContext input) {
		TeamTemplate template = teamTemplateService.getById(input.getTemplate());
		if (template == null) {
			throw new ResourceNotFoundException(input.getTemplate());
		}

		BusinessTemplate businessTemplate = businessTemplateService
				.getById(template.getBusinessTemplateId());

		TeamRoom teamRoom = new TeamRoom(input, template, businessTemplate);
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {

				save(teamRoom);

				List<TeamTrainTemplateRole> teamTrainTemplateRoleList = JsonUtil
						.toJsonObjectArray(template.getRoleConfig(), TeamTrainTemplateRole.class);
				List<RoomRole> users = input.getRoleConfig();
				int index = 0;
				for (TeamTrainTemplateRole teamTrainTemplateRole : teamTrainTemplateRoleList) {

					CrRole crRole = BeanUtils.deepCopy(teamTrainTemplateRole, CrRole.class);
					// crRole.setBusinessInstanceId();
					crRole.setUserIds(JsonUtil.toJsonString(users.get(index++).getUsers()));
					teamTrainTemplateRole.getResourceId();
					JsonUtil.toJsonString(teamTrainTemplateRoleList);
					crRole.setBusinessInstanceId(teamRoom.getResourceId());
					crRole.setBusinessType(BusinessTemplate.BusinessType.TRAINING_TEAM.getCode());
					crRoleService.save(crRole);

				}

				// todo保存任务角色信息
			}
		});

		return template;
	}

	@Override
	public TeamTemplate updateTeamRoom(TeamTrainTemplateContext input,
			BusinessTemplate businessTemplate) {
		TeamTemplate teamTemplate = new TeamTemplate();
		String userId = UserInfoContext.getUserContext().getUserId();

		BaseInfo baseInfo = input.getBaseInfo();

		SceneConfig sceneConfig = input.getSceneConfigData();

		businessTemplate.setTopoId("");
		businessTemplate.setCreateUserId(UserInfoContext.getUserContext().getUserId());
		businessTemplate.setResourceId(businessTemplate.getResourceId());
		businessTemplate.setName(baseInfo.getName());
		businessTemplate.setDescription(baseInfo.getDescription());
		businessTemplate.setDifficulty(baseInfo.getDifficult());

		if (sceneConfig != null) {
			String sceneConfigId = "todo";
			businessTemplate.setTopoId(sceneConfigId);
		}

		// 保存团队训练模板
		teamTemplate.setResourceId(input.getResourceId());
		teamTemplate.setBusinessTemplateId(businessTemplate.getResourceId());
		teamTemplate.setPreSkills(JsonUtil.toJsonString(baseInfo.getPreSkill()));
		teamTemplate.setTrainSkills(JsonUtil.toJsonString(baseInfo.getTrainSkill()));
		teamTemplate.setSubType(baseInfo.getCategory());
		teamTemplate.setTimeLineConfig(JsonUtil.toJsonString(input.getTimeLineConfig()));
		teamTemplate.setDurationTime(baseInfo.getDurationTime());
		teamTemplate.setPeopleNumber(baseInfo.getPeopleNumber());

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				businessTemplateService.updateById(businessTemplate);

				teamTemplateService.updateById(teamTemplate);
				// todo任务相关

			}
		});

		return teamTemplate;
	}

	@Override
	public ListRsp<TeamRoomRsp> list(ListQuery listQuery) {
		String userId = UserInfoContext.getUserContext().getUserId();
		listQuery.setSort("-create_time");
		Page page = listQuery.toPageParam();
		listQuery.setIds(crRoleService.getRoomIdsByUserId(userId));
		IPage<TeamRoomData> data = teamRoomMapper.listTeamRoom(page, listQuery);

		ListRsp ret = new ListRsp();

		List<TeamRoomRsp> teamRoomRspList = Lists.newArrayList();

		for (TeamRoomData teamRoomData : data.getRecords()) {
			RoleQuery roleQuery = new RoleQuery();
			roleQuery.setRoomId(teamRoomData.getResourceId());

			List<CrRole> crRoles = crRoleMapper.selectCrRoleByRoomId(roleQuery);

			TeamRoomRsp teamRoomRsp = getTeamRoomOutputFromTeamRoomData(teamRoomData);
			teamRoomRsp.setTrainingRoleData(getSelfRoleUsers(crRoles, userId));
			teamRoomRsp.setRolesData(crRoles);

			teamRoomRspList.add(teamRoomRsp);
		}

		ret.setList(teamRoomRspList);
		ret.setTotal(data.getTotal());
		ret.setLastPage(data.getPages());
		ret.setCurrentPage(data.getCurrent());

		return ret;
	}

	@Override
	public Integer changeTrainingStatus(String resourceId) {
		TeamRoomData teamRoomdata = teamRoomMapper.getTeamRoomRspByRoomId(resourceId);
		teamRoomdata.setTrainingStatus(setTrainingStatus(teamRoomdata));

		// 未开始，新增local_scene数据
		if (teamRoomdata.isUnopened()) {
			LocalScene localScene = new LocalScene();
			localScene.setStartTime(new Date());
			localScene.setBusinessInstanceId(teamRoomdata.getResourceId());
			localSceneService.save(localScene);
		}
		// 已开始，新增local_scene数据
		if (teamRoomdata.isOpening()) {
			com.cyberpeace.cs.dao.entity.LocalScene localScene = localSceneService
					.getByBusinessInstanceId(teamRoomdata.getResourceId());
			localScene.setEndTime(new Date());
			localSceneService.save(localScene);
		}

		if (teamRoomdata.isOpening()) {
			// todo 日志
		}

		else if (teamRoomdata.isEnded()) {
			// todo 日志
		}
		return teamRoomdata.getTrainingStatus();

	}

	@Override
	public Integer getTrainingStatus(String resourceId) {
		TeamRoomData teamRoomdata = teamRoomMapper.getTeamRoomRspByRoomId(resourceId);
		teamRoomdata.setTrainingStatus(setTrainingStatus(teamRoomdata));
		return setTrainingStatus(teamRoomdata);
	}

	private TeamRoomRsp getTeamRoomOutputFromTeamRoomData(TeamRoomData teamRoomdata) {
		TeamRoomRsp teamRoomRsp = BeanUtils.copy(teamRoomdata, TeamRoomRsp.class);

		return teamRoomRsp;
	}
}
