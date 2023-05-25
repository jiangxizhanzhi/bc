package com.cyberpeace.cr.redvsblue.service.impl;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.exception.ResourceNotFoundException;
import com.cyberpeace.common.model.room.DashboardFriend;
import com.cyberpeace.common.model.room.DashboardGroup;
import com.cyberpeace.common.model.room.DashboardInfo;
import com.cyberpeace.common.model.room.DashboardUser;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.EnumUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.controller.req.ListQuery;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.dao.mapper.CrTaskSubmitMapper;
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.CrTaskService;
import com.cyberpeace.cr.crbase.service.MissionService;
import com.cyberpeace.cr.crbase.service.context.*;
import com.cyberpeace.cr.crbase.service.context.role.*;
import com.cyberpeace.cr.redvsblue.dao.entity.RbRoom;
import com.cyberpeace.cr.redvsblue.dao.entity.RbTemplate;
import com.cyberpeace.cr.redvsblue.dao.entity.RbTimeManagement;
import com.cyberpeace.cr.redvsblue.dao.mapper.RbRoomMapper;
import com.cyberpeace.cr.redvsblue.service.RbRoomService;
import com.cyberpeace.cr.redvsblue.service.RbTemplateService;
import com.cyberpeace.cr.redvsblue.service.RbTimeManagementService;
import com.cyberpeace.cr.redvsblue.service.context.*;
import com.cyberpeace.integratation.service.RemoteTopoService;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RbRoomServiceImpl extends ServiceImpl<RbRoomMapper, RbRoom> implements RbRoomService {

	@Autowired
	private RbRoomMapper rbRoomMapper;

	@Autowired
	private RbTemplateService rbTemplateService;

	@Autowired
	private BusinessTemplateService businessTemplateService;

	@Autowired
	private MissionService missionService;

	@Autowired
	private CrTaskService crTaskService;

	@Autowired
	private CrTaskSubmitMapper crTaskSubmitMapper;

	@Autowired
	private RbTimeManagementService rbTimeManagementService;

	@Autowired
	private RemoteTopoService remoteTopoService;

	@Autowired
	private RemoteUserService remoteUserService;

	@Autowired
	private CrRoleService crRoleService;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Override
	public Integer getTrainingStatus(String roomId) {
		RbRoomData teamRoomdata = getRbRoomDataByRoomId(roomId);

		return teamRoomdata.getTrainingStatus();
	}

	@Override
	public List<RbRoom> getByIds(List<String> roomIds) {
		if (roomIds.size() == 0) {
			return Lists.newArrayList();
		}
		return rbRoomMapper.selectBatchIds(roomIds);
	}

	@Override
	public List<RbRoom> getAll() {
		return rbRoomMapper.selectAll();
	}

	@Override
	public IPage<RbRoomData> list(ListQuery listQuery) {
		String userId = UserInfoContext.getUserContext().getUserId();

		Page page = listQuery.toPageParam();
		listQuery.setIds(crRoleService.getRoomIdsByUserId(userId));

		return rbRoomMapper.listRbRoom(page, listQuery);
	}

	@Override
	public Integer countAll() {
		return rbRoomMapper.countAll();
	}

	@Override
	public RbRoom create(RbRoomContext rbRoomContext) {
		String userId = UserInfoContext.getUserContext().getUserId();

		validateName(rbRoomContext.getName());

		RbTemplate template = rbTemplateService.getById(rbRoomContext.getTemplate());
		if (template == null) {
			log.error("create room failed, template not found, template id is {}",
					rbRoomContext.getTemplate());
			throw new ResourceNotFoundException(rbRoomContext.getTemplate());
		}

		BusinessTemplate businessTemplate = businessTemplateService
				.getById(template.getBusinessTemplateId());

		RbRoom rbRoom = new RbRoom(rbRoomContext, template);
		rbRoom.setCreateUserId(userId);
		rbRoom.setCopyedTopoId(remoteTopoService.copyTopo(businessTemplate.getTopoId()));

		// 复制完topo后修改部分启动状态
		remoteTopoService.setSupportPartialStart(rbRoom.getCopyedTopoId(),
				rbRoomContext.getSupportPartialStart());
		rbRoom.setSupportPartialStart(rbRoomContext.getSupportPartialStart());

		rbRoom.setTemplateName(businessTemplate.getName());
		rbRoom.setTrainingStatus(RbRoom.TrainingStatusCode.UNOPENED.getCode());

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {

				save(rbRoom);
				List<CrRole> crRoles = getCrRoleByRbTemplate(template, rbRoom.getResourceId());

				List<RoomRole> users = rbRoomContext.getRoleConfig();
				int index = 0;

				for (CrRole crRole : crRoles) {
					if (index >= users.size()) {
						log.error("role users cannot be empty！rbRoomName=[{}]", rbRoom.getName());
						throw new BusinessException(CrBusinessErrorCode.ROLE_USERS_NOT_BE_EMPTY);
					}

					if (users.get(index).getUsers().size() == 0) {
						index += 2;
					}

					crRole.setUserIdList(users.get(index++).getUsers());
					crRole.setRoomId(rbRoom.getResourceId());
					crRole.setBusinessType(BusinessTemplate.BusinessType.RED_VS_BLUE.getCode());

					crRoleService.save(crRole);

				}

			}

		});

		return rbRoom;
	}

	@Override
	public RbRoomData getRbRoomDataByRoomId(String resourceId) {
		RbRoomData rbRoomData = rbRoomMapper.getRbRoomDataByRoomId(resourceId);
		if (rbRoomData == null) {
			log.error("get room data failed, room not found, room id is {}", resourceId);
			throw new ResourceNotFoundException(resourceId);
		}

		return rbRoomData;
	}

	@Override
	public List<DashboardUser> getMembersInfo(String roleId, String roomId) {
		List<DashboardUser> friend = Lists.newArrayList();
		List<String> userIds = Lists.newArrayList();

		List<CrRole> crRoles = crRoleService.selectCrRoleByRoomIdAndRoleId(roomId, roleId);

		for (CrRole crRole : crRoles) {
			List<String> roleUserIds = crRole.getUserIdList();
			userIds.addAll(roleUserIds);

			for (String userId : roleUserIds) {
				DashboardUser dashboardUser = new DashboardUser();
				dashboardUser.setId(userId);
				dashboardUser.setStatus("online");
				dashboardUser.setSign("");
				dashboardUser.setUsername(getFullRoleName(crRole));

				friend.add(dashboardUser);

			}

		}

		// 获取全部用户
		Map<String, UserInfo> userInfoMap = remoteUserService.getIdUserInfoMap(userIds);

		// 去掉不存在的用户
		Iterator<DashboardUser> userIterator = friend.iterator();
		while (userIterator.hasNext()) {
			DashboardUser dashboardUser = userIterator.next();
			UserInfo userInfo = userInfoMap.get(dashboardUser.getId());
			if (userInfo == null) {
				userIterator.remove();
			} else {
				dashboardUser.setRole(userInfo.getUserName());
				dashboardUser.setAvatar(userInfo.getAvatarUrl());
				dashboardUser
						.setUsername(userInfo.getUserName() + "/" + dashboardUser.getUsername());
			}
		}

		return friend;
	}

	@Override
	public DashboardInfo getDashboardInfo(CrRole myRole, String roomId, String currentUserId) {

		DashboardInfo dashboardInfo = new DashboardInfo();

		crRoleService.combineRoleConst(dashboardInfo);

		RoleQuery roleQuery = new RoleQuery();
		roleQuery.setRoomId(roomId);

		List<CrRole> crRoles = crRoleService.selectCrRoleByRoomId(roleQuery);
		List<String> userIds = Lists.newArrayList();

		DashboardUser mine = new DashboardUser();
		List<DashboardUser> friend = Lists.newArrayList();

		List<String> redMembers = Lists.newArrayList();
		List<String> blueMembers = Lists.newArrayList();
		List<String> whiteMembers = Lists.newArrayList();

		for (CrRole crRole : crRoles) {
			// 如果是红方或蓝方角色
			if (myRole.isRvbStudent()) {
				// 如果自己也是红蓝角色，并且不是己方角色，直接跳过
				if (!crRole.getRoleGroup().equals(myRole.getRoleGroup()) && crRole.isRvbStudent()) {
					continue;
				}
			}

			// 获取所有用户
			List<String> roleUserIds = crRole.getUserIdList();
			userIds.addAll(roleUserIds);
			/*
			if (!crRole.isBlueStudent()) {
				redMembers.addAll(roleUserIds);
			}
			if (!crRole.isRedStudent()) {
				blueMembers.addAll(roleUserIds);
			}
			*/
			if (crRole.isBlueStudent()) {
				blueMembers.addAll(roleUserIds);
			}
			if (crRole.isRedStudent()) {
				redMembers.addAll(roleUserIds);
			}
			if(!crRole.isBlueStudent() && !crRole.isRedStudent()) {
				whiteMembers.addAll(roleUserIds);
			}

			for (String userId : roleUserIds) {
				DashboardUser dashboardUser = new DashboardUser();
				dashboardUser.setId(userId);
				dashboardUser.setStatus("online");
				dashboardUser.setSign("");
				// dashboardUser.setRole(getFullRoleName(crRole));
				if (crRole.isBlueStudent()) {
					dashboardUser.setRoleType(CrRole.RbRoleGroup.BLUE.getEnglish());
					dashboardUser.setRole(CrRole.RbRoleGroup.BLUE.getMsg());
					dashboardUser.setRoleCode(CrRole.RbRoleGroup.BLUE.getCode());
				}
				if (crRole.isRedStudent()) {
					dashboardUser.setRoleType(CrRole.RbRoleGroup.RED.getEnglish());
					dashboardUser.setRole(CrRole.RbRoleGroup.RED.getMsg());
					dashboardUser.setRoleCode(CrRole.RbRoleGroup.RED.getCode());
				}
				if(!crRole.isBlueStudent() && !crRole.isRedStudent()) {
					dashboardUser.setRoleType(CrRole.RbRoleGroup.WHITE.getEnglish());
					dashboardUser.setRole(crRole.getName());
					// dashboardUser.setRoleCode(CrRole.RbRoleGroup.WHITE.getCode());
				}

				// 找到当前用户角色
				if (userId.equals(currentUserId)) {
					mine = dashboardUser;
				} else {
					friend.add(dashboardUser);
				}
			}

		}

		List<DashboardGroup> groups = Lists.newArrayList();

		DashboardGroup redGroup = new DashboardGroup(CrRole.RbRoleGroup.RED_GROUP.getMsg(),
				CrRole.RbRoleGroup.RED.getCode(),
				confirmAvatarFile(CrCommonConstants.RED_GROUP_AVATAR, CrCommonConstants.RED_GROUP_JAR_AVATAR));
		DashboardGroup blueGroup = new DashboardGroup(CrRole.RbRoleGroup.BLUE_GROUP.getMsg(),
				CrRole.RbRoleGroup.BLUE.getCode(),
				confirmAvatarFile(CrCommonConstants.BLUE_GROUP_AVATAR, CrCommonConstants.BLUE_GROUP_JAR_AVATAR));
		DashboardGroup whiteGroup = new DashboardGroup(CrRole.RbRoleGroup.WHITE_GROUP.getMsg(),
				CrRole.RbRoleGroup.WHITE.getCode(),
				confirmAvatarFile(CrCommonConstants.WHITE_GROUP_AVATAR, CrCommonConstants.WHITE_GROUP_JAR_AVATAR));

		redGroup.setMembers(redMembers);
		redGroup.setId(roomId + "_" + CrRole.RbRoleGroup.RED.getCode().toString());
		blueGroup.setMembers(blueMembers);
		blueGroup.setId(roomId + "_" + CrRole.RbRoleGroup.BLUE.getCode().toString());
		whiteGroup.setMembers(whiteMembers);
		whiteGroup.setId(roomId + "_" + CrRole.RbRoleGroup.WHITE.getCode().toString());

		// 自己不是红方，放入蓝方
		if (!myRole.isRedStudent()) {
			groups.add(blueGroup);
		}

		// 自己不是蓝方，放入红方
		if (!myRole.isBlueStudent()) {
			groups.add(redGroup);
		}

		groups.add(whiteGroup);

		// 如果自己是红方或蓝方角色，再放入自己的角色
		/*
		if (myRole.isRvbStudent()) {
			DashboardGroup selfGroup = new DashboardGroup(
					groups.get(0).getGroupname() + "/" + myRole.getName());
			selfGroup.setId(myRole.getResourceId());
			selfGroup.setMembers(myRole.getUserIdList());
			groups.add(selfGroup);
		}
		*/
		// 获取全部用户
		Map<String, UserInfo> userInfoMap = remoteUserService.getIdUserInfoMap(userIds);

		mine.setUsername(userInfoMap.get(mine.getId()).getName());
		mine.setAvatar(userInfoMap.get(mine.getId()).getAvatarUrl());
		// 去掉不存在的用户
		friend = StreamApiUtils.filter(friend,
				dashboardUser -> userInfoMap.containsKey(dashboardUser.getId()));

		for (DashboardUser dashboardUser : friend) {
			UserInfo userInfo = userInfoMap.get(dashboardUser.getId());
			dashboardUser.setAvatar(userInfo.getAvatarUrl());
			dashboardUser.setUsername(userInfo.getName());
		}

		DashboardFriend dashboardFriend = new DashboardFriend();
		dashboardFriend.setId(1);
		dashboardFriend.setGroupname(DashboardFriend.GROUP_NAME.FRIEND_LIST.getDesc());
		dashboardFriend.setList(friend);

		dashboardInfo.setMine(mine);
		dashboardInfo.setFriend(Lists.newArrayList(dashboardFriend));
		dashboardInfo.setGroup(groups);

		return dashboardInfo;
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

	private String getFullRoleName(CrRole crRole) {
		String roleName = EnumUtils.getEnumDescByCode(crRole.getRoleGroup(),
				CrRole.RbRoleGroup.class);

		if (crRole.isRedStudent() || crRole.isBlueStudent()) {
			roleName = crRole.getName();
		}

		if (crRole.isBlueStudent()) {
			roleName = CrRole.RbRoleGroup.BLUE.getDesc() + "/" + roleName;
		}
		if (crRole.isRedStudent()) {
			roleName = CrRole.RbRoleGroup.RED.getDesc() + "/" + roleName;
		}

		return roleName;
	}

	@Override
	public void updateRbRoom(RbRoomContext rbRoomContext, RbRoom rbRoom) {
		if (!StringUtils.isBlank(rbRoomContext.getName())
				&& !rbRoom.getName().equals(rbRoomContext.getName())) {
			validateName(rbRoomContext.getName());
			rbRoom.setName(rbRoomContext.getName());
		}
		if (rbRoomContext.getCategory() != null) {
			rbRoom.setSubType(rbRoomContext.getCategory());
		}
		if (!StringUtils.isBlank(rbRoomContext.getDescription())) {
			rbRoom.setDescription(rbRoomContext.getDescription());
		}
		if (rbRoomContext.getPreSkill() != null && rbRoomContext.getPreSkill().size() > 0) {
			rbRoom.setPreSkill(JsonUtil.toJsonString(rbRoomContext.getPreSkill()));
		}
		if (rbRoomContext.getTrainSkill() != null && rbRoomContext.getTrainSkill().size() > 0) {
			rbRoom.setTrainSkill(JsonUtil.toJsonString(rbRoomContext.getTrainSkill()));
		}
		if (rbRoomContext.getStartTime() != null) {
			rbRoom.setStartTime(rbRoomContext.getStartTime());
		}
		if (rbRoomContext.getEndTime() != null) {
			rbRoom.setEndTime(rbRoomContext.getEndTime());
		}
		if (rbRoomContext.getEnableRecording() != null) {
			rbRoom.setEnableRecording(rbRoomContext.getEnableRecording());
		}
		if (rbRoomContext.getLogo() != null) {
			rbRoom.setLogo(rbRoomContext.getLogo());
		}
		if (rbRoomContext.getSupportPartialStart() != null) {
			// 修改topo的部分启动状态
			remoteTopoService.setSupportPartialStart(rbRoom.getCopyedTopoId(),
					rbRoomContext.getSupportPartialStart());
			rbRoom.setSupportPartialStart(rbRoomContext.getSupportPartialStart());
		}

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				updateById(rbRoom);
				if (rbRoomContext.getRoleConfig() != null
						&& rbRoomContext.getRoleConfig().size() > 0) {

					Map<String, RoomRole> roomRoleMap = rbRoomContext.getRoleConfig().stream()
							.collect(Collectors.toMap(RoomRole::getResourceId, bo -> bo));

					RoleQuery roleQuery = new RoleQuery();
					roleQuery.setRoomId(rbRoom.getResourceId());
					List<CrRole> crRoleList = crRoleService.selectCrRoleByRoomId(roleQuery);

					crRoleList.forEach(crRole -> {
						List<String> users = roomRoleMap.get(crRole.getResourceId()).getUsers();
						if (users.size() == 0) {
							log.error("role users cannot be empty！rbRoomName=[{}]",
									rbRoom.getName());
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

	private void permissionCheck(String roomId, String userId, CrRole.RbRoleGroup roleGroup) {
		RbRoom rbRoom = getById(roomId);
		if (rbRoom == null) {
			log.error("failed to get RbRoom with Id [{}]", roomId);
			throw new BusinessException(CrBusinessErrorCode.ROOM_NOT_EXIST);
		}

		CrRole crRole = crRoleService.getCrRoleByRoomIdAndUserId(roomId, userId);
		if (!crRole.isInGroup(roleGroup)) {
			log.error("user does not have RbRoom Permission！roomId=[{}], userId=[{}], role=[{}]",
					roomId, userId, roleGroup.getDesc());
			throw new BusinessException(CrBusinessErrorCode.PERMISSION_DENIED);
		}
	}

	@Override
	public void checkDirectorPermission(String roomId, String userId) {
		permissionCheck(roomId, userId, CrRole.RbRoleGroup.DIRECTOR);
	}

	@Override
	public void checkRefereePermission(String roomId, String userId) {
		permissionCheck(roomId, userId, CrRole.RbRoleGroup.REFEREE);
	}

	@Override
	public void checkRoomOpening(String roomId) {
		RbRoom rbRoom = getById(roomId);

		if (rbRoom == null) {
			log.error("failed to get RbRoom with Id [{}]", roomId);
			throw new BusinessException(CrBusinessErrorCode.ROOM_NOT_EXIST);
		}

		if (rbRoom.isUnopened()) {
			log.error("RedVsBlueTrain not begin! roomId=[{}]", roomId);
			throw new BusinessException(CrBusinessErrorCode.TRAIN_NOT_BEGIN);

		}

		if (rbRoom.isPaused()) {
			log.error("RedVsBlueTrain is paused! roomId=[{}]", roomId);
			throw new BusinessException(CrBusinessErrorCode.ALREADY_PAUSED);

		}
		if (rbRoom.isEnded()) {
			log.error("RedVsBlueTrain is over! roomId=[{}]", roomId);
			throw new BusinessException(CrBusinessErrorCode.TRAIN_IS_OVER);

		}
	}

	@Override
	public void checkRoomUnclosed(String roomId) {
		RbRoom rbRoom = getById(roomId);

		if (rbRoom == null) {
			log.error("failed to get RbRoom with Id [{}]", roomId);
			throw new BusinessException(CrBusinessErrorCode.ROOM_NOT_EXIST);
		}

		if (rbRoom.isEnded()) {
			log.error("RedVsBlueTrain is over! roomId=[{}]", roomId);
			throw new BusinessException(CrBusinessErrorCode.TRAIN_IS_OVER);

		}
	}

	@Override
	public List<CrRole> getStudentsRoles(String roomId) {
		return crRoleService.getRolesByRoomAndGroups(roomId, Lists
				.newArrayList(CrRole.RbRoleGroup.RED.getCode(), CrRole.RbRoleGroup.BLUE.getCode()));
	}

	@Override
	public Integer getDuration(String roomId) {
		RbRoom rbRoom = getById(roomId);
		RbTemplate template = rbTemplateService.getById(rbRoom.getTemplateId());
		if (null != template) {
			return template.getDurationTime();
		} else {
			return (int) ((rbRoom.getEndTime().getTime() - rbRoom.getStartTime().getTime())
					/ 3600000);
		}
	}

    @Override
	public void checkStudentPermission(String roomId, String userId) {
		RbRoom rbRoom = getById(roomId);
		if (rbRoom == null) {
			log.error("failed to get RbRoom with Id [{}]", roomId);
			throw new BusinessException(CrBusinessErrorCode.ROOM_NOT_EXIST);
		}

		CrRole crRole = crRoleService.getCrRoleByRoomIdAndUserId(roomId, userId);
		if (!crRole.isRedStudent() && !crRole.isBlueStudent()) {
			log.error("user does not have RbRoom student Permission！roomId=[{}], userId=[{}]",
					roomId, userId);
			throw new BusinessException(CrBusinessErrorCode.PERMISSION_DENIED);
		}

	}

	@Override
	public List<UserRole> getUserList(String roomId) {
		List<UserRole> userRoleList = Lists.newArrayList();

		List<String> userList = Lists.newArrayList();

		List<CrRole> crRoles = crRoleService.selectRbStudentsRoleByRoomId(roomId);

		for (CrRole crRole : crRoles) {
			List<String> users = crRole.getUserIdList();
			users.forEach(user -> {
				UserRole userRole = new UserRole();
				userRole.setResourceId(user);
				userRole.setRoleName(crRole.getName());
				userRole.setRoleDesc(
						crRole.getDescription() == null ? "" : crRole.getDescription());
				userRoleList.add(userRole);
				userList.add(user);
			});
		}

		// 获取全部用户
		Map<String, UserInfo> userInfoMap = remoteUserService.getIdUserInfoMap(userList);

		// 去掉不存在的用户
		Iterator<UserRole> userIterator = userRoleList.iterator();
		while (userIterator.hasNext()) {
			UserRole userRole = userIterator.next();
			UserInfo userInfo = userInfoMap.get(userRole.getResourceId());
			if (userInfo == null) {
				userIterator.remove();
			} else {
				userRole.setName(userInfo.getName());
				userRole.setLogo(userInfo.getAvatarUrl());
			}
		}

		return userRoleList;
	}

	@Override
	public RbTimeLineData getMissionTimeline(String roomId) {
		RbTimeLineData rbTimeLineData = new RbTimeLineData();
		// get start time and endtime
		RbRoom rbRoom = getById(roomId);
		Date startTime = rbRoom.getStartTime();
		Date endTime = rbRoom.getEndTime();

		RbTimeManagement rbTimeManagement = rbTimeManagementService.getByRoomId(roomId);
		if (rbTimeManagement != null) {
			startTime = rbTimeManagement.getCreateTime();

			if (RbTimeManagement.TimeManagementStatus.STOP.getCode()
					.equals(rbTimeManagement.getStatus())) {
				endTime = rbTimeManagement.getUpdateTime();
			} else {
				endTime = new Date();
			}
		}
		rbTimeLineData.setStartTime(startTime);
		rbTimeLineData.setEndTime(endTime);

		List<RbTimeLineEvent> rbTimeLineEventList = new ArrayList<>();

		// 构造角色分组map
		RoleQuery roleQuery = new RoleQuery();
		roleQuery.setRoomId(roomId);

		List<CrRole> crRoles = crRoleService.selectCrRoleByRoomId(roleQuery);
		// collect dynamic missions
		List<String> dynamicMissionIds = Lists.newArrayList();
		for (CrRole role : crRoles) {
			if (role.isRvbStudent()) {
				List<CrRoleMissionSchemeStage> stages = JsonUtil
						.toJsonObjectArray(role.getMissionScheme(), CrRoleMissionSchemeStage.class);
				dynamicMissionIds.addAll(
						StreamApiUtils.flatMap(stages, CrRoleMissionSchemeStage::getDynamic));
			}
		}

		Map<String, Integer> crRoleMap = crRoles.stream()
				.collect(Collectors.toMap(CrRole::getResourceId, bo -> bo.getRoleGroup()));

		List<TaskContext> crTasks = crTaskService.getTaskContextByRoomId(roomId);
		List<String> userIds = crTasks.stream().filter(a -> StringUtils.isNotBlank(a.getUserId()))
				.map(a -> a.getUserId()).collect(Collectors.toList());
		Map<String, UserInfo> userInfoMap = remoteUserService.getIdUserInfoMap(userIds);

		for (TaskContext task : crTasks) {

			if (task.getLastSubmitTime() != null) {
				// 获取任务下发用户id
				UserInfo userInfo = userInfoMap.get(task.getUserId());
				RbTimeLineEvent rbTimeLineEvent = new RbTimeLineEvent();
				rbTimeLineEvent.setRoleGroup(crRoleMap.get(task.getRoleId()));
				rbTimeLineEvent.setTitle(task.getMission().getTitle());
				if (null != userInfo) {
					rbTimeLineEvent.setUserName(userInfo.getName());
					rbTimeLineEvent.setAvatarUrl(userInfo.getAvatarUrl());
				}
				rbTimeLineEvent.setTime(task.getLastSubmitTime());
				rbTimeLineEvent.setStatus(task.getStatus());
				rbTimeLineEvent.setIsDynamic(dynamicMissionIds.contains(task.getMissionId()));

				rbTimeLineEventList.add(rbTimeLineEvent);
			}

		}
		rbTimeLineData.setData(rbTimeLineEventList);

		return rbTimeLineData;
	}

	@Override
	public CrRole checkRoomPermission(String roomId, String userId) {
		RbRoom rbRoom = getById(roomId);
		if (rbRoom == null) {
			log.error("failed to get RbRoom with Id [{}]", roomId);
			throw new BusinessException(CrBusinessErrorCode.ROOM_NOT_EXIST);
		}

		CrRole crRole = crRoleService.getCrRoleByRoomIdAndUserId(roomId, userId);
		if (crRole == null) {
			log.error("user does not have RbRoom Permission！roomId=[{}], userId=[{}]", roomId,
					userId);
			throw new BusinessException(CrBusinessErrorCode.PERMISSION_DENIED);
		}
		return crRole;
	}

	@Override
	public void checkTeacherPermission(String roomId, String userId) {
		CrRole role = crRoleService.getCrRoleByRoomIdAndUserId(roomId, userId);
		if (role.isRvbStudent()) {
			log.error("user does not have RbRoom teacher Permission！roomId=[{}], userId=[{}]",
					roomId, userId);
			throw new BusinessException(CrBusinessErrorCode.PERMISSION_DENIED);
		}
	}

	private List<CrRole> getCrRoleByRbTemplate(RbTemplate rbTemplateData, String roomId) {
		List<CrRole> crRoleList = Lists.newArrayList();

		// 获取红蓝的角色组
		String roleString = rbTemplateData.getRoleConfig();

		crRoleList.add(new CrRole(CrRole.RbRoleGroup.ADMIN));
		crRoleList.add(new CrRole(CrRole.RbRoleGroup.DIRECTOR));
		crRoleList.add(new CrRole(CrRole.RbRoleGroup.REFEREE));

		// 获取红队的角色任务
		JSONObject rolejsonObject = JsonUtil.toJsonObject(roleString, JSONObject.class);
		JSONArray redRoles = (JSONArray) rolejsonObject.get("red_roles");
		for (int i = 0; i < redRoles.size(); i++) {
			CrRole crRole = getRoleByJson(roomId, i, redRoles, CrRole.RbRoleGroup.RED);
			crRoleList.add(crRole);
		}

		// 获取蓝队的角色任务
		JSONArray blueRoles = (JSONArray) rolejsonObject.get("blue_roles");
		for (int i = 0; i < blueRoles.size(); i++) {
			CrRole crRole = getRoleByJson(roomId, i, blueRoles, CrRole.RbRoleGroup.BLUE);
			crRoleList.add(crRole);
		}

		return crRoleList;
	}

	private CrRole getRoleByJson(String roomId, int index, JSONArray redRoles,
			CrRole.RbRoleGroup roleGroup) {
		JSONObject rolesJSONObject = redRoles.getJSONObject(index);

		CrRoleNodesData nodesData = new CrRoleNodesData();

		String assetNodes = rolesJSONObject.get("asset_nodes").toString();
		nodesData.setAssetNodes(JsonUtil.toJsonObjectArray(assetNodes, CrRoleNode.class));

		String visibleNodes = rolesJSONObject.get("visible_nodes").toString();
		nodesData.setVisibleNodes(JsonUtil.toJsonObjectArray(visibleNodes, CrRoleNode.class));

		CrRole crRole = new CrRole(roleGroup, rolesJSONObject.get("name").toString());

		crRole.setMissionStageSchemes(
				getNewRoomScheme(rolesJSONObject.get("missions_scheme").toString(), roomId));
		crRole.setNodesData(nodesData);

		return crRole;
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

	private void validateName(String name) {
		// check if duplicate
		Integer roomExist = rbRoomMapper.countByName(name);
		if (roomExist > 0) {
			log.error("room name [{}] already exist", name);
			throw new BusinessException(CrBusinessErrorCode.TRAINING_NAME_DUPLICATE);
		}
	}
}
