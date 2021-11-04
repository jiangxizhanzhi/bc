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
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoom;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoomData;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTemplate;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTrainTemplateRole;
import com.cyberpeace.cr.teamtrain.dao.mapper.TeamRoomMapper;
import com.cyberpeace.cr.teamtrain.service.TeamRoomService;
import com.cyberpeace.cr.teamtrain.service.TeamTemplateService;
import com.cyberpeace.cr.teamtrain.service.context.ListQuery;
import com.cyberpeace.cr.teamtrain.service.context.RoomRole;
import com.cyberpeace.cr.teamtrain.service.context.TeamTrainRoomContext;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.LocalSceneService;

/**
 *
 */
@Service
public class TeamRoomServiceImpl extends ServiceImpl<TeamRoomMapper, TeamRoom> implements TeamRoomService {

	@Autowired
	private TeamRoomMapper teamRoomMapper;


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
	public TeamRoomData getTeamRoomRspByRoomId(String resourceId) {

		TeamRoomData teamRoomData = teamRoomMapper.getTeamRoomRspByRoomId(resourceId);
		if (teamRoomData == null) {
			throw new ResourceNotFoundException(resourceId);
		}
		teamRoomData.setTrainingStatus(setTrainingStatus(teamRoomData));

		return teamRoomData;
	}

	@Override
	public TeamTemplate create(TeamTrainRoomContext teamTrainRoomContext) {
		TeamTemplate template = teamTemplateService.getById(teamTrainRoomContext.getTemplate());
		if (template == null) {
			throw new ResourceNotFoundException(teamTrainRoomContext.getTemplate());
		}

		BusinessTemplate businessTemplate = businessTemplateService
				.getById(template.getBusinessTemplateId());

		TeamRoom teamRoom = new TeamRoom(teamTrainRoomContext, template, businessTemplate);
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {

				save(teamRoom);

				List<TeamTrainTemplateRole> teamTrainTemplateRoleList = JsonUtil
						.toJsonObjectArray(template.getRoleConfig(), TeamTrainTemplateRole.class);
				List<RoomRole> users = teamTrainRoomContext.getRoleConfig();
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

				// todo 保存任务角色信息
			}
		});

		return template;
	}

	@Override
	public TeamTemplate updateTeamRoom(TeamTrainRoomContext teamTrainRoomContext,
			TeamRoom teamRoom) {
		TeamTemplate teamTemplate = new TeamTemplate();
		String userId = UserInfoContext.getUserContext().getUserId();

		teamRoom.setCreateUserId(UserInfoContext.getUserContext().getUserId());
		teamRoom.setResourceId(teamTrainRoomContext.getResourceId());
		teamRoom.setName(teamTrainRoomContext.getName());
		teamRoom.setDescription(teamTrainRoomContext.getDescription());
		teamRoom.setDifficulty(teamTrainRoomContext.getDifficult());

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				updateById(teamRoom);

				// todo 任务相关

			}
		});

		return teamTemplate;
	}

	@Override
	public IPage<TeamRoomData> list(ListQuery listQuery) {
		String userId = UserInfoContext.getUserContext().getUserId();

		Page page = listQuery.toPageParam();
		listQuery.setIds(crRoleService.getRoomIdsByUserId(userId));

		IPage<TeamRoomData> data = teamRoomMapper.listTeamRoom(page, listQuery);

		return data;
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

	private int setTrainingStatus(TeamRoomData teamRoomdata) {
		if (null == teamRoomdata.getSceneStartTime()) {
			return TeamRoom.TrainingStatusCode.UNOPENED.getCode();
		} else if (null == teamRoomdata.getSceneEndTime()) {
			return TeamRoom.TrainingStatusCode.OPENING.getCode();
		} else {
			return TeamRoom.TrainingStatusCode.ENDED.getCode();
		}
	}

}
