package com.cyberpeace.cr.teamtrain.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.exception.ResourceNotFoundException;
import com.cyberpeace.common.model.room.DashboardFriend;
import com.cyberpeace.common.model.room.DashboardGroup;
import com.cyberpeace.common.model.room.DashboardInfo;
import com.cyberpeace.common.model.room.DashboardUser;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.crbase.controller.req.ListQuery;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.dao.mapper.CrTaskLogMapper;
import com.cyberpeace.cr.crbase.service.*;
import com.cyberpeace.cr.crbase.service.context.AddTemplateMission;
import com.cyberpeace.cr.crbase.service.context.ColorMission;
import com.cyberpeace.cr.crbase.service.context.MissionSchemeObject;
import com.cyberpeace.cr.crbase.service.context.MissionStageScheme;
import com.cyberpeace.cr.crbase.service.context.evaluation.EvaluationListReq;
import com.cyberpeace.cr.crbase.service.context.evaluation.EvaluationListRsp;
import com.cyberpeace.cr.crbase.service.context.mission.TimeLineData;
import com.cyberpeace.cr.crbase.service.context.mission.TimeLineEvent;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleNodesData;
import com.cyberpeace.cr.crbase.service.context.role.RoleQuery;
import com.cyberpeace.cr.crbase.service.context.role.UserRole;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoom;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoomData;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTemplate;
import com.cyberpeace.cr.teamtrain.dao.mapper.TeamRoomMapper;
import com.cyberpeace.cr.teamtrain.dao.mapper.TeamTemplateMapper;
import com.cyberpeace.cr.teamtrain.service.TeamRoomService;
import com.cyberpeace.cr.teamtrain.service.TeamTemplateService;
import com.cyberpeace.cr.teamtrain.service.context.RoleListContext;
import com.cyberpeace.cr.teamtrain.service.context.RoomRole;
import com.cyberpeace.cr.teamtrain.service.context.TeamTrainRoomContext;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.integratation.service.RemoteTopoService;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

/**
 *
 */
@Service
@Slf4j
public class TeamRoomServiceImpl extends ServiceImpl<TeamRoomMapper, TeamRoom> implements TeamRoomService {

	@Autowired
	private TeamRoomMapper teamRoomMapper;

	@Autowired
	private TeamTemplateMapper teamTemplateMapper;

	@Autowired
	private TrainRecordService trainRecordService;

	@Autowired
	private TeamTemplateService teamTemplateService;

	@Autowired
	private BusinessTemplateService businessTemplateService;

	@Autowired
	private MissionService missionService;

	@Autowired
	private CrRoleService crRoleService;

	@Autowired
	private CrTaskService crTaskService;

	@Autowired
	private RemoteUserService remoteUserService;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private CrTaskLogMapper crTaskLogMapper;

	@Autowired
	private RemoteTopoService remoteTopoService;

	@Autowired
	private CrCommonService crCommonService;

	@Autowired
	private CrTimelineService crTimelineService;

	@Override
	public TeamRoomData getTeamRoomDataByRoomId(String resourceId) {

		TeamRoomData teamRoomData = teamRoomMapper.getTeamRoomRspByRoomId(resourceId);
		if (teamRoomData == null) {
			throw new ResourceNotFoundException(resourceId);
		}

		return teamRoomData;
	}

	@Override
	public TeamRoom create(TeamTrainRoomContext teamTrainRoomContext) {
		String userId = UserInfoContext.getUserContext().getUserId();

		TeamTemplate template = teamTemplateService.getById(teamTrainRoomContext.getTemplate());
		if (template == null) {
			throw new ResourceNotFoundException(teamTrainRoomContext.getTemplate());
		}
		validateName(teamTrainRoomContext.getName());

		BusinessTemplate businessTemplate = businessTemplateService
				.getById(template.getBusinessTemplateId());

		TeamRoom teamRoom = new TeamRoom(teamTrainRoomContext, template);
		teamRoom.setCreateUserId(userId);
		teamRoom.setCopyedTopoId(remoteTopoService.copyTopo(businessTemplate.getTopoId()));
		teamRoom.setTeamTemplateName(businessTemplate.getName());
		teamRoom.setTrainingStatus(TeamRoom.TrainingStatusCode.UNOPENED.getCode());

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				save(teamRoom);

				List<RoleListContext> originRoles = JsonUtil
						.toJsonObjectArray(template.getRoleConfig(), RoleListContext.class);
				List<RoomRole> users = teamTrainRoomContext.getRoleConfig();
				int index = 0;
				RoleListContext teacherRole = new RoleListContext();
				// 教员
				teacherRole.setName(CrRole.TeamRoleGroup.TEACHER.getDesc());
				teacherRole.setRoleGroup(CrRole.TeamRoleGroup.TEACHER);
				originRoles.add(0, teacherRole);

				for (RoleListContext originRole : originRoles) {
					originRole.setId(null);

					CrRole crRole = BeanUtils.deepCopy(originRole, CrRole.class);
					if (users.get(index).getUsers().size() == 0) {
						log.error("role users cannot be empty！teamRoomName=[{}]",
								teamRoom.getName());
						throw new BusinessException(CrBusinessErrorCode.ROLE_USERS_NOT_BE_EMPTY);
					}
					crRole.setUserIdList(users.get(index).getUsers());
					crRole.setRoomId(teamRoom.getResourceId());
					crRole.setBusinessType(BusinessTemplate.BusinessType.TRAINING_TEAM.getCode());
					List<MissionStageScheme> copyMission = getNewRoomScheme(
							JsonUtil.toJsonString(originRole.getMissionsScheme()),
							teamRoom.getResourceId());
					crRole.setMissionStageSchemes(copyMission);
					// 学员
					if (crRole.getRoleGroup() == null) {
						crRole.setRoleGroup(CrRole.TeamRoleGroup.STUDENT);
					}
					CrRoleNodesData nodesData = new CrRoleNodesData();
					nodesData.setAssetNodes(originRole.getAssetNodes());
					nodesData.setVisibleNodes(originRole.getVisibleNodes());
					crRole.setNodesData(nodesData);
					// 创建房间时保存原始节点配置快照，用于动态更新场景后恢复
					crRole.setNodesSnapshot(crRole.getNodes());
					crRoleService.save(crRole);

					index++;
				}

			}

		});

		return teamRoom;
	}

	/** 根据模板的任务id，复制任务并获取新的id，返回新的json字符串
	 * @param missionsScheme
	 * @return
	 */
	private List<MissionStageScheme> getNewRoomScheme(String missionsScheme, String roomId) {
		ColorMission<AddTemplateMission> colorMission = JSON.parseObject(missionsScheme,
				new TypeReference<ColorMission<AddTemplateMission>>() {});
		if (null == colorMission || null == colorMission.getSchemeData()) {
			return new ArrayList<>();
		}

		List<MissionStageScheme> missionStageSchemes = Lists.newArrayList();
		List<String> originMissionIds = Lists.newArrayList();
		// 遍历每个阶段
		for (MissionSchemeObject<AddTemplateMission> schemeDatum : colorMission.getSchemeData()) {

			List<AddTemplateMission> missions = schemeDatum.getMissions();
			// 遍历每个任务
			for (AddTemplateMission mission : missions) {
				originMissionIds.add(mission.getResourceId());
			}
		}

		Map<String, String> originMissionMaps = missionService.copyScheme(originMissionIds,
				UserInfoContext.getUserContext().getUserId(), roomId);

		// 遍历每个阶段设置新的任务id
		for (MissionSchemeObject<AddTemplateMission> schemeDatum : colorMission.getSchemeData()) {
			MissionStageScheme missionStageScheme = new MissionStageScheme();

			List<String> targetMissionIds = Lists.newArrayList();

			// 遍历每个任务设置新的任务id
			for (AddTemplateMission mission : schemeDatum.getMissions()) {
				targetMissionIds.add(originMissionMaps.getOrDefault(mission.getResourceId(),
						mission.getResourceId()));
			}
			missionStageScheme.setStage(schemeDatum.getStage());
			missionStageScheme.setDynamic(schemeDatum.getDynamic());
			missionStageScheme.setMissions(targetMissionIds);

			missionStageSchemes.add(missionStageScheme);
		}
		return missionStageSchemes;
	}

	@Override
	public void updateTeamRoom(TeamTrainRoomContext teamTrainRoomContext, TeamRoom teamRoom) {

		if (!StringUtils.isBlank(teamTrainRoomContext.getName())
				&& !teamRoom.getName().equals(teamTrainRoomContext.getName())) {
			validateName(teamTrainRoomContext.getName());
			teamRoom.setName(teamTrainRoomContext.getName());
		}
		if (teamTrainRoomContext.getCategory() != null) {
			teamRoom.setSubType(teamTrainRoomContext.getCategory());
		}
		if (!StringUtils.isBlank(teamTrainRoomContext.getDescription())) {
			teamRoom.setDescription(teamTrainRoomContext.getDescription());
		}
		if (teamTrainRoomContext.getPreSkill() != null
				&& teamTrainRoomContext.getPreSkill().size() > 0) {
			teamRoom.setPreSkills(JsonUtil.toJsonString(teamTrainRoomContext.getPreSkill()));
		}
		if (teamTrainRoomContext.getTrainSkill() != null
				&& teamTrainRoomContext.getTrainSkill().size() > 0) {
			teamRoom.setTrainSkills(JsonUtil.toJsonString(teamTrainRoomContext.getTrainSkill()));
		}
		if (teamTrainRoomContext.getStartTime() != null) {
			teamRoom.setStartTime(teamTrainRoomContext.getStartTime());
		}
		if (teamTrainRoomContext.getEndTime() != null) {
			teamRoom.setEndTime(teamTrainRoomContext.getEndTime());
		}
		if (teamTrainRoomContext.getEnableRecording() != null) {
			teamRoom.setEnableRecording(teamTrainRoomContext.getEnableRecording());
		}
		if (teamTrainRoomContext.getLogo() != null) {
			teamRoom.setLogo(teamTrainRoomContext.getLogo());
		}

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				updateById(teamRoom);
				if (teamTrainRoomContext.getRoleConfig() != null
						&& teamTrainRoomContext.getRoleConfig().size() > 0) {

					Map<String, RoomRole> roomRoleMap = teamTrainRoomContext.getRoleConfig()
							.stream().collect(Collectors.toMap(RoomRole::getResourceId, bo -> bo));

					RoleQuery roleQuery = new RoleQuery();
					roleQuery.setRoomId(teamRoom.getResourceId());
					List<CrRole> crRoleList = crRoleService.selectCrRoleByRoomId(roleQuery);

					crRoleList.forEach(crRole -> {
						List<String> users = roomRoleMap.get(crRole.getResourceId()).getUsers();
						if (users.size() == 0) {
							log.error("role users cannot be empty！teamRoomName=[{}]",
									teamRoom.getName());
							throw new BusinessException(
									CrBusinessErrorCode.ROLE_USERS_NOT_BE_EMPTY);
						}
						crRole.setUserIdList(users);
						crRoleService.updateById(crRole);

					});
				}
			}
		});

	}

	@Override
	public IPage<TeamRoomData> list(ListQuery listQuery) {
		String userId = UserInfoContext.getUserContext().getUserId();

		Page page = listQuery.toPageParam();
		listQuery.setIds(crRoleService.getRoomIdsByUserId(userId));

		return teamRoomMapper.listTeamRoom(page, listQuery);
	}

	@Override
	public Integer changeTrainingStatus(String roomId) {
		TeamRoom teamRoom = getById(roomId);

		if (teamRoom.isUnopened()) {
			teamRoom.setTrainingStatus(TeamRoom.TrainingStatusCode.OPENING.getCode());
		} else if (teamRoom.isOpening()) {
			teamRoom.setTrainingStatus(TeamRoom.TrainingStatusCode.ENDED.getCode());
		} else if (teamRoom.isEnded()) {
			log.error("teamTrain is over! roomId=[{}]", roomId);
			throw new BusinessException(CrBusinessErrorCode.TRAIN_IS_OVER);
		}
		// 获取团队训练业务模板id
		TeamTemplate teamTemplate = teamTemplateMapper.selectById(teamRoom.getTeamTemplateId());
		String businessTemplateId;
		// 模板可能已被删除。
		if (teamTemplate == null) {
			businessTemplateId = teamRoom.getTeamTemplateId();
		} else {
			businessTemplateId = teamTemplate.getBusinessTemplateId();
		}
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				updateById(teamRoom);
				if (teamRoom.isOpening()) {
					trainRecordService.saveRecord(teamRoom.getName(), businessTemplateId, roomId,
							teamRoom.getCreateUserId(),
							BusinessTemplate.BusinessType.TRAINING_TEAM.getCode());
				} else if (teamRoom.isEnded()) {
					trainRecordService.updateEndTimeByRoomId(roomId, new Date());
				}
			}
		});
		return teamRoom.getTrainingStatus();
	}

	@Override
	public Integer getTrainingStatus(String roomId) {
		TeamRoomData teamRoomdata = getTeamRoomDataByRoomId(roomId);

		return teamRoomdata.getTrainingStatus();
	}

	@Override
	public List<UserRole> getUserList(String roomId) {
		List<UserRole> userRoleList = Lists.newArrayList();

		RoleQuery roleQuery = new RoleQuery();
		roleQuery.setRoomId(roomId);
		List<String> userList = Lists.newArrayList();

		List<CrRole> crRoles = crRoleService.selectCrRoleByRoomId(roleQuery);
		for (CrRole crRole : crRoles) {
			// 获取每个学员用户，以及学员id
			if (crRole.isTeamStudent()) {
				List<String> users = crRole.getUserIdList();
				users.forEach(user -> {
					UserRole userRole = new UserRole();
					userRole.setResourceId(user);
					userRole.setRoleName(crRole.getName());
					userRole.setRoleDesc(crRole.getDescription());
					userRoleList.add(userRole);
					userList.add(user);
				});

			}
		}

		List<UserInfo> userInfoList = remoteUserService.getByIdList(userList);
		if (userInfoList.size() != userList.size()) {
			log.error("user_info_missing！userList=[{}]", JsonUtil.toJsonString(userList));
			throw new BusinessException(ErrorCode.USER_INFO_MISSING);
		}

		Map<String, UserInfo> userInfoMap = userInfoList.stream()
				.collect(Collectors.toMap(UserInfo::getUserId, bo -> bo));

		for (UserRole userRole : userRoleList) {
			userRole.setName(userInfoMap.get(userRole.getResourceId()).getName());
			userRole.setLogo(userInfoMap.get(userRole.getResourceId()).getAvatarUrl());
		}

		return userRoleList;
	}

	@Override
	public TeamRoom getByLocalSceneId(String localSceneId) {
		TeamRoom room = teamRoomMapper.selectByLocalSceneId(localSceneId);
		if (room == null) {
			log.error("failed to get TeamRoom with localSceneId [{}]", localSceneId);
			throw new BusinessException(CrBusinessErrorCode.ROOM_NOT_EXIST);
		}
		return room;
	}

	@Override
	public TeamRoom getById(String roomId) {
		TeamRoom room = teamRoomMapper.selectById(roomId);
		if (room == null) {
			log.error("failed to get TeamRoom with Id [{}]", roomId);
			throw new BusinessException(CrBusinessErrorCode.ROOM_NOT_EXIST);
		}
		return room;
	}

	@Override
	public TimeLineData getStudentsTimeLine(String roomId) {
		TimeLineData timeLineData = new TimeLineData();
		setTime(timeLineData, roomId);

		List<TimeLineEvent> timeLineEventList = crTimelineService.getReplayEvents(roomId,
				UserInfoContext.getUserContext().getUserId());

		timeLineData.setData(timeLineEventList);
		return timeLineData;
	}

	@Override
	public TimeLineData getTimeLine(String roomId) {
		TimeLineData timeLineData = new TimeLineData();
		// 设置开始时间、结束时间
		setTime(timeLineData, roomId);
		// 获取事件列表
		List<TimeLineEvent> timeLineEventList = crTimelineService.getTimelineEventsByRoomId(roomId);
		timeLineData.setUserTimeLineConfig(timeLineEventList);
		return timeLineData;
	}

	@Override
	public void checkStudentPermission(String roomId, String userId) {
		permissionCheck(roomId, userId);
	}

	@Override
	public List<CrRole> getStudentsRoles(String roomId) {
		return crRoleService.getRolesByRoomAndGroups(roomId,
				Lists.newArrayList(CrRole.TeamRoleGroup.STUDENT.getCode()));
	}

	@Override
	public List<EvaluationListRsp> getEvaluationList(EvaluationListReq req) {
		return crTaskService.getEvaluationList(req);
	}

	/**
	 * 获取持续时间
	 * 取模板里的设置时长，模板被删除则取创建房间时设置的小时数
	 *
	 * @param roomId 房间id
	 * @return {@code Integer}
	 */
	@Override
	public Integer getDuration(String roomId) {
		TeamRoom room = getById(roomId);
		TeamTemplate template = teamTemplateService.getById(room.getTeamTemplateId());
		if (null != template) {
			return template.getDurationTime();
		} else {
			return (int) ((room.getEndTime().getTime() - room.getStartTime().getTime()) / 3600000);
		}
	}

	@Override
	public DashboardInfo getDashboardInfo(CrRole myRole, String roomId, String currentUserId) {
		DashboardInfo dashboardInfo = new DashboardInfo();
		// 添加常量供前端使用
		crRoleService.combineRoleConst(dashboardInfo);

		RoleQuery roleQuery = new RoleQuery();
		roleQuery.setRoomId(roomId);

		List<CrRole> crRoles = crRoleService.selectCrRoleByRoomId(roleQuery);
		List<String> userIds = Lists.newArrayList();
		crRoles.forEach(role -> userIds.addAll(role.getUserIdList()));
		// 获取全部用户
		Map<String, UserInfo> userInfoMap = remoteUserService.getIdUserInfoMap(userIds);

		// 自己
		final DashboardUser[] mineArr = new DashboardUser[1];
		// 好友列表
	 	final List<DashboardUser> friend = Lists.newArrayList();
		// 团队群组
		List<String> teamMembers = Lists.newArrayList();

		crRoles.forEach(role -> {
			List<String> userIdList = role.getUserIdList();
			// 过滤不存在用户
			userIdList = userIdList.stream()
					.filter(userInfoMap::containsKey)
					.collect(Collectors.toList());
			role.setUserIdList(userIdList);

			teamMembers.addAll(userIdList);

			/*
			// 只添加自己角色类型的用户作为群组
			if(myRole.isTeamStudent() && role.isTeamStudent() ||
					myRole.isTeamTeacher() && role.isTeamTeacher()) {

				teamMembers.addAll(userIdList);

				// 将自己的信息单独列出来
				Optional<String> first = userIdList.stream()
						.filter(userId -> userId.equals(currentUserId))
						.findFirst();
				String myUserId = first.get();
				mineArr[0] = (buildDashboardUser(role, myUserId, userInfoMap));
			}
			*/
			// 将自己的信息单独列出来
			Optional<String> first = userIdList.stream()
					.filter(userId -> userId.equals(currentUserId))
					.findFirst();
			String myUserId = first.orElse("");
			if(StringUtils.isNotEmpty(myUserId)) {
				mineArr[0] = (buildDashboardUser(role, myUserId, userInfoMap));
			}

			userIds.addAll(role.getUserIdList());

			userIdList = userIdList.stream()
					.filter(item -> !currentUserId.equals(item))
					.collect(Collectors.toList());
			role.setUserIdList(userIdList);
			// 添加所有人作为好友列表
			friend.addAll(buildDashboardUser(role, userInfoMap));
		});

		DashboardUser mine = mineArr[0];
		mine.username(userInfoMap.get(mine.getId()).getName())
				.avatar(userInfoMap.get(mine.getId()).getAvatarUrl());

		// 好友列表封装
		DashboardFriend dashboardFriend = new DashboardFriend();
		dashboardFriend.setId(1);
		dashboardFriend.setGroupname(DashboardFriend.GROUP_NAME.FRIEND_LIST.getDesc());
		dashboardFriend.setList(friend);

		// 群组封装
		List<DashboardGroup> groups = Lists.newArrayList();
		DashboardGroup teamGroup = new DashboardGroup(CrRole.RbRoleGroup.TEAM_GROUP.getMsg(),
				CrRole.RbRoleGroup.TEAM.getCode(),
				confirmAvatarFile(CrCommonConstants.TEAM_GROUP_AVATAR, CrCommonConstants.TEAM_GROUP_JAR_AVATAR));
		teamGroup.setMembers(teamMembers);
		teamGroup.setId(roomId);
		groups.add(teamGroup);


		dashboardInfo.setMine(mine);
		dashboardInfo.setFriend(Lists.newArrayList(dashboardFriend));
		dashboardInfo.setGroup(groups);

		return dashboardInfo;
	}

	@Override
	public CrRole checkRoomPermission(String roomId, String userId) {
		TeamRoom rbRoom = getById(roomId);
		if (rbRoom == null) {
			log.error("failed to get TeamRoom with Id [{}]", roomId);
			throw new BusinessException(CrBusinessErrorCode.ROOM_NOT_EXIST);
		}

		CrRole crRole = crRoleService.getCrRoleByRoomIdAndUserId(roomId, userId);
		if (crRole == null) {
			log.error("user does not have TeamRoom Permission！roomId=[{}], userId=[{}]", roomId,
					userId);
			throw new BusinessException(CrBusinessErrorCode.PERMISSION_DENIED);
		}
		return crRole;
	}

	@Override
	public List<DashboardUser> getMembersInfo(String roleId, String roomId) {
		List<DashboardUser> friend = Lists.newArrayList();

		List<CrRole> crRoles = crRoleService.selectCrRoleByRoomIdAndRoleId(roomId, null);
		final List<String> userIds = new ArrayList<>();
		crRoles.forEach(role -> userIds.addAll(role.getUserIdList()));

		// 获取全部用户
		Map<String, UserInfo> userInfoMap = remoteUserService.getIdUserInfoMap(userIds);

		crRoles.forEach(role -> {
			List<String> userIdList = role.getUserIdList();
			// 过滤不存在用户
			userIdList = userIdList.stream()
					.filter(userInfoMap::containsKey)
					.collect(Collectors.toList());
			role.setUserIdList(userIdList);

			List<DashboardUser> dashboardUsers = buildDashboardUser(role, userInfoMap);
			friend.addAll(dashboardUsers);
		});

		return friend;
	}

	@Override
	public TeamRoom getOrExceptionById(String roomId) {
		TeamRoom room = getById(roomId);
		if (null == room) {
			log.error("failed to get team room by room_id: [{}]", roomId);
			throw new BusinessException(CrBusinessErrorCode.ROOM_NOT_EXIST);
		}
		return room;
	}

	@Override
	public TimeLineData getTimeLine(String roomId, String userId) {
		TimeLineData timeLineData = new TimeLineData();
		setTime(timeLineData, roomId);
		// 传入用户id，则展示事件，否则只提供开始时间、结束时间
		if (StringUtils.isNotBlank(userId)) {
			List<TimeLineEvent> eventList = crTimelineService.getReplayEvents(roomId, userId);
			timeLineData.setEvents(eventList);
		}
		return timeLineData;
	}

	private String confirmAvatarFile(String filePath, String jarFilePath) {
		File file = new File(filePath);
		if(file.exists()) {
			return filePath;
		}
		if(!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}

		try(
				InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(jarFilePath);
				OutputStream outputStream = new FileOutputStream(file);
		) {
			int read = 0;
			byte[] bytes = new byte[2048];
			while((read = inputStream.read(bytes)) > 0) {
				outputStream.write(bytes, 0, read);
			}
		} catch (Exception e) {
			log.error("file not found in directory{}",
					filePath);
			throw new BusinessException(
					CrBusinessErrorCode.NOT_PATH);
		}
		return filePath;
	}

	private List<DashboardUser> buildDashboardUser(CrRole role, Map<String, UserInfo> userInfoMap) {
		return role.getUserIdList().stream()
				.map(userId -> buildDashboardUser(role, userId, userInfoMap))
				.collect(Collectors.toList());
	}

	private DashboardUser buildDashboardUser(CrRole role, String userId, Map<String, UserInfo> userInfoMap) {
		UserInfo userInfo = userInfoMap.get(userId);

		DashboardUser dashboardUser = new DashboardUser();
		dashboardUser
				.id(userId)
				.status("online")
				.sign("")
				.avatar(userInfo.getAvatarUrl())
				.username(userInfo.getName());
		if(role.isTeamTeacher()) {
			dashboardUser.role(CrRole.TeamRoleGroup.TEACHER.getMsg());
		}
		if(role.isTeamStudent()) {
			dashboardUser.role(CrRole.TeamRoleGroup.STUDENT.getMsg());
		}
		return dashboardUser;
	}

	@Override
	public void checkTeacherPermission(String roomId, String userId) {
		permissionCheck(roomId, userId, CrRole.TeamRoleGroup.TEACHER.getCode());

	}

	@Override
	public void checkRoomOpening(String roomId) {
		TeamRoom teamRoom = getById(roomId);

		if (teamRoom.isUnopened()) {
			log.error("teamTrain not begin! roomId=[{}]", roomId);
			throw new BusinessException(CrBusinessErrorCode.TRAIN_NOT_BEGIN);

		}
		if (teamRoom.isEnded()) {
			log.error("teamTrain is over! roomId=[{}]", roomId);
			throw new BusinessException(CrBusinessErrorCode.TRAIN_IS_OVER);

		}
	}

	private void permissionCheck(String roomId, String userId) {
		TeamRoom teamRoom = getById(roomId);
		if (teamRoom == null) {
			log.error("failed to get TeamRoom with Id [{}]", roomId);
			throw new BusinessException(CrBusinessErrorCode.ROOM_NOT_EXIST);
		}

		try {
			crRoleService.getCrRoleByRoomIdAndUserId(roomId, userId);
		} catch (Exception e) {
			log.error("user does not have TeamRoom Permission！roomId=[{}], userId=[{}]", roomId,
					userId);
			throw new BusinessException(CrBusinessErrorCode.PERMISSION_DENIED);
		}
	}

	private void permissionCheck(String roomId, String userId, Integer roleGroup) {
		TeamRoom teamRoom = getById(roomId);
		if (teamRoom == null) {
			log.error("failed to get TeamRoom with Id [{}]", roomId);
			throw new BusinessException(CrBusinessErrorCode.ROOM_NOT_EXIST);
		}

		try {
			CrRole crRole = crRoleService.getCrRoleByRoomIdAndUserId(roomId, userId);

			if (!roleGroup.equals(crRole.getRoleGroup())) {
				log.error("permission denied！roomId=[{}]", roomId);
				throw new BusinessException(CrBusinessErrorCode.PERMISSION_DENIED);
			}

		} catch (Exception e) {
			log.error("user does not have TeamRoom Permission！roomId=[{}], userId=[{}]", roomId,
					userId);
			throw new BusinessException(CrBusinessErrorCode.PERMISSION_DENIED);
		}

	}

	private LocalScene getLocalScene(String teamRoomId) {
		TeamRoom teamRoom = teamRoomMapper.selectById(teamRoomId);

		LocalScene localScene = new LocalScene();
		localScene.setUserId(UserInfoContext.getUserContext().getUserId());
		localScene.setSceneStatus(LocalScene.SceneStatusEnum.INIT.getCode());
		localScene.setBusinessCode(LocalScene.BusinessCode.TEAM_TRAINING.getCode());

		localScene.setDestroyMode(LocalScene.DestroyMode.MANUAL.getCode());
		localScene.setBusinessInstanceId(teamRoomId);
		localScene.setTopoId(teamRoom.getCopyedTopoId());

		return localScene;
	}

	private void setTime(TimeLineData timeLineData, String roomId) {
		TeamRoomData teamRoom = getTeamRoomDataByRoomId(roomId);
		// 团队训练结束后，时间轴中蓝条（current_time）设为结束时间
		timeLineData.setCurrentTime(new Date());
		timeLineData.setStartTime(teamRoom.getSceneStartTime() == null ? teamRoom.getStartTime()
				: teamRoom.getSceneStartTime());

		Date endTime = teamRoom.getEndTime();

		if (teamRoom.getSceneStartTime() != null) {
			if (teamRoom.getSceneEndTime() == null) {
				long startToEnd = teamRoom.getEndTime().getTime()
						- teamRoom.getStartTime().getTime();
				long now = new Date().getTime();
				long sceneStartToNow = new Date().getTime()
						- teamRoom.getSceneStartTime().getTime();
				// 训练时长大于实际开始时间到当前时间的时长，时间间隔取训练时长,否则取当前时间
				if (startToEnd >= sceneStartToNow) {
					endTime = new Date(startToEnd + teamRoom.getSceneStartTime().getTime());
				} else {
					endTime = new Date(now);
				}
			}
			if (teamRoom.getSceneEndTime() != null
					&& teamRoom.getSceneEndTime().compareTo(teamRoom.getSceneStartTime()) > 0) {
				endTime = teamRoom.getSceneEndTime();

			}
		}

		timeLineData.setEndTime(endTime);
	}

	private void validateName(String name) {
		// check if duplicate
		if (teamRoomMapper.existByName(name)) {
			log.error("training name duplicate！name=[{}]", name);
			throw new BusinessException(CrBusinessErrorCode.TRAINING_NAME_DUPLICATE);
		}
	}

}
