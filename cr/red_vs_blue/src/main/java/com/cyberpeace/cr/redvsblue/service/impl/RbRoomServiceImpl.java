package com.cyberpeace.cr.redvsblue.service.impl;

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
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.exception.ResourceNotFoundException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.NameCount;
import com.cyberpeace.common.model.room.DashboardFriend;
import com.cyberpeace.common.model.room.DashboardGroup;
import com.cyberpeace.common.model.room.DashboardInfo;
import com.cyberpeace.common.model.room.DashboardUser;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.crbase.controller.req.ListQuery;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.service.*;
import com.cyberpeace.cr.crbase.service.context.*;
import com.cyberpeace.cr.crbase.service.context.mission.TimeLineData;
import com.cyberpeace.cr.crbase.service.context.mission.TimeLineEvent;
import com.cyberpeace.cr.crbase.service.context.role.*;
import com.cyberpeace.cr.crbase.utils.CrCommonUtil;
import com.cyberpeace.cr.redvsblue.controller.rsp.RbRoomRoleRes;
import com.cyberpeace.cr.redvsblue.controller.rsp.RbRoomRsp;
import com.cyberpeace.cr.redvsblue.dao.entity.RbRoom;
import com.cyberpeace.cr.redvsblue.dao.entity.RbTemplate;
import com.cyberpeace.cr.redvsblue.dao.entity.RbTimeManagement;
import com.cyberpeace.cr.redvsblue.dao.mapper.RbRoomMapper;
import com.cyberpeace.cr.redvsblue.service.RbRoomService;
import com.cyberpeace.cr.redvsblue.service.RbTemplateService;
import com.cyberpeace.cr.redvsblue.service.RbTimeManagementService;
import com.cyberpeace.cr.redvsblue.service.context.RbRoomContext;
import com.cyberpeace.cr.redvsblue.service.context.RbRoomData;
import com.cyberpeace.cr.redvsblue.service.context.RoomRole;
import com.cyberpeace.integratation.model.scene.TopoDigest;
import com.cyberpeace.integratation.service.RemoteTopoService;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

/**
 * RB房间Service实现
 *
 * @author SN
 * @date 2024/11/07
 */
@Service
@Slf4j
public class RbRoomServiceImpl extends ServiceImpl<RbRoomMapper, RbRoom> implements RbRoomService {

	/**
	 * RB房间Mapper
	 */
	@Autowired
	private RbRoomMapper rbRoomMapper;

	/**
	 * RB模板Service
	 */
	@Autowired
	private RbTemplateService rbTemplateService;

	/**
	 * 业务模板Service
	 */
	@Autowired
	private BusinessTemplateService businessTemplateService;

	/**
	 * 任务Service
	 */
	@Autowired
	private MissionService missionService;

	/**
	 * RB时间管理Service
	 */
	@Autowired
	private RbTimeManagementService rbTimeManagementService;

	/**
	 * 远程拓扑Service
	 */
	@Autowired
	private RemoteTopoService remoteTopoService;

	/**
	 * 远程用户Service
	 */
	@Autowired
	private RemoteUserService remoteUserService;

	/**
	 * 靶场角色Service
	 */
	@Autowired
	private CrRoleService crRoleService;

	/**
	 * 事务模板
	 */
	@Autowired
	private TransactionTemplate transactionTemplate;

	/**
	 * 靶场时间轴Service
	 */
	@Autowired
	private CrTimelineService crTimelineService;

	/**
	 * RTKS相关Service
	 */
	@Autowired
	private RtksRelatedService rtksRelatedService;

	/**
	 * 获取训练状态
	 *
	 * @param roomId 房间id
	 * @return {@code Integer }
	 */
	@Override
	public Integer getTrainingStatus(String roomId) {
		RbRoomData teamRoomdata = getRbRoomDataByRoomId(roomId);

		return teamRoomdata.getTrainingStatus();
	}

	/**
	 * 获取由id列表
	 *
	 * @param roomIds 房间id列表
	 * @return {@code List<RbRoom> }
	 */
	@Override
	public List<RbRoom> getByIds(List<String> roomIds) {
		if (roomIds.size() == 0) {
			return Lists.newArrayList();
		}
		return rbRoomMapper.selectBatchIds(roomIds);
	}

	/**
	 * 获取都
	 *
	 * @return {@code List<RbRoom> }
	 */
	@Override
	public List<RbRoom> getAll() {
		return rbRoomMapper.selectAll();
	}

	/**
	 * 列表
	 *
	 * @param listQuery 列表查询
	 * @return {@code IPage<RbRoomData> }
	 */
	@Override
	public IPage<RbRoomData> list(ListQuery listQuery) {
		String userId = UserInfoContext.getUserContext().getUserId();

		Page page = listQuery.toPageParam();
		listQuery.setIds(crRoleService.getRoomIdsByUserId(userId));

		return rbRoomMapper.listRbRoom(page, listQuery);
	}

	/**
	 * 计数都
	 *
	 * @return {@code Integer }
	 */
	@Override
	public Integer countAll() {
		return rbRoomMapper.countAll();
	}

	/**
	 * 创造
	 *
	 * @param rbRoomContext RB 房间上下文
	 * @return {@code RbRoom }
	 */
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
					// 前端传的数据的问题
					if (users.get(index).getUsers().size() == 0) {
						index += 2;
					}

					crRole.setUserIdList(users.get(index++).getUsers());
					crRole.setRoomId(rbRoom.getResourceId());
					crRole.setBusinessType(BusinessTemplate.BusinessType.RED_VS_BLUE.getCode());
					// 创建房间时保存原始节点配置快照，用于动态更新场景后恢复
					crRole.setNodesSnapshot(crRole.getNodes());
				}
				crRoleService.saveBatch(crRoles);

			}

		});

		return rbRoom;
	}

	/**
	 * 获取RB房间数据由房间id
	 *
	 * @param resourceId 资源id
	 * @return {@code RbRoomData }
	 */
	@Override
	public RbRoomData getRbRoomDataByRoomId(String resourceId) {
		RbRoomData rbRoomData = rbRoomMapper.getRbRoomDataByRoomId(resourceId);
		if (rbRoomData == null) {
			log.error("get room data failed, room not found, room id is {}", resourceId);
			throw new ResourceNotFoundException(resourceId);
		}

		return rbRoomData;
	}

	/**
	 * 获取成员信息
	 *
	 * @param roleId 角色id
	 * @param roomId 房间id
	 * @return {@code List<DashboardUser> }
	 */
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

	/**
	 * 获取挡泥板信息
	 *
	 * @param myRole        我角色
	 * @param roomId        房间id
	 * @param currentUserId 当前用户id
	 * @return {@code DashboardInfo }
	 */
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
				// 红蓝群需要把白方的人加入
				blueMembers.addAll(roleUserIds);
				redMembers.addAll(roleUserIds);
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

		// 如果既不是红方也不是蓝方
		if(!myRole.isRedStudent() && !myRole.isBlueStudent()) {
			groups.add(whiteGroup);
		}

		// 如果自己是红方或蓝方角色，再放入自己的角色
		if (myRole.isRvbStudent()) {
			String groupRole = groups.get(0).getGroupname() + "/" + myRole.getName();
			String groupAvatar = "";
			if(groups.get(0).getGroupname().equals(CrRole.RbRoleGroup.RED_GROUP.getMsg())) {
				groupAvatar = confirmAvatarFile(CrCommonConstants.RED_GROUP_AVATAR, CrCommonConstants.RED_GROUP_JAR_AVATAR);
			}
			if(groups.get(0).getGroupname().equals(CrRole.RbRoleGroup.BLUE_GROUP.getMsg())) {
				groupAvatar = confirmAvatarFile(CrCommonConstants.BLUE_GROUP_AVATAR, CrCommonConstants.BLUE_GROUP_JAR_AVATAR);
			}

			DashboardGroup selfGroup = new DashboardGroup(groupRole);
			selfGroup.setAvatar(groupAvatar);
			selfGroup.setId(myRole.getResourceId());
			selfGroup.setMembers(myRole.getUserIdList());
			groups.add(selfGroup);
		}

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

	/**
	 * 确认头像文件
	 *
	 * @param filePath    文件路径
	 * @param jarFilePath jar 文件路径
	 * @return {@code String }
	 */
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

	/**
	 * 获取满角色名称
	 *
	 * @param crRole 靶场角色
	 * @return {@code String }
	 */
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

	/**
	 * 更新 RB 房间
	 *
	 * @param rbRoomContext RB 房间上下文
	 * @param rbRoom        RB 会议室
	 */
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

	/**
	 * 权限检查
	 *
	 * @param roomId    房间id
	 * @param userId    用户id
	 * @param roleGroup 角色组
	 */
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

	/**
	 * 检查导演许可
	 *
	 * @param roomId 房间id
	 * @param userId 用户id
	 */
	@Override
	public void checkDirectorPermission(String roomId, String userId) {
		permissionCheck(roomId, userId, CrRole.RbRoleGroup.DIRECTOR);
	}

	/**
	 * 检查被推荐人权限
	 *
	 * @param roomId 房间id
	 * @param userId 用户id
	 */
	@Override
	public void checkRefereePermission(String roomId, String userId) {
		permissionCheck(roomId, userId, CrRole.RbRoleGroup.REFEREE);
	}

	/**
	 * 检查房间开放情况
	 *
	 * @param roomId 房间id
	 */
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

	/**
	 * 检查室未关闭
	 *
	 * @param roomId 房间id
	 */
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

	/**
	 * 获取学员角色
	 *
	 * @param roomId 房间id
	 * @return {@code List<CrRole> }
	 */
	@Override
	public List<CrRole> getStudentsRoles(String roomId) {
		return crRoleService.getRolesByRoomAndGroups(roomId, Lists
				.newArrayList(CrRole.RbRoleGroup.RED.getCode(), CrRole.RbRoleGroup.BLUE.getCode()));
	}

	/**
	 * 获取期间
	 *
	 * @param roomId 房间id
	 * @return {@code Integer }
	 */
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

	/**
	 * 获取或例外由id
	 *
	 * @param roomId 房间id
	 * @return {@code RbRoom }
	 */
	@Override
	public RbRoom getOrExceptionById(String roomId) {
		RbRoom room = getById(roomId);
		if (null == room) {
			log.error("failed to get rvb room by room_id: [{}]", roomId);
			throw new BusinessException(CrBusinessErrorCode.ROOM_NOT_EXIST);
		}
		return room;
	}

	/**
	 * 获取复盘时间轴
	 *
	 * @param roomId 房间id
	 * @param userId 用户id
	 * @return {@code TimeLineData }
	 */
	@Override
	public TimeLineData getReplayTimeline(String roomId, String userId) {
		TimeLineData timeLineData = new TimeLineData();
		setTime(timeLineData, roomId);
		// 传入用户id，则展示事件，否则只提供开始时间、结束时间
		if (StringUtils.isNotBlank(userId)) {
			// get events
			List<TimeLineEvent> eventList = crTimelineService.getReplayEvents(roomId, userId);
			timeLineData.setEvents(eventList);
		}
		return timeLineData;
	}

	/**
	 * 获取学员时间线
	 *
	 * @param roomId 房间id
	 * @return {@code TimeLineData }
	 */
	@Override
	public TimeLineData getStudentsTimeLine(String roomId) {
		TimeLineData timeLineData = new TimeLineData();
		// set start time , end time
		setTime(timeLineData, roomId);
		// get event list
		List<TimeLineEvent> timeLineEventList = crTimelineService.getReplayEvents(roomId,
				UserInfoContext.getUserContext().getUserId());
		timeLineData.setData(timeLineEventList);
		return timeLineData;
	}

	/**
	 * 获取房间响应
	 *
	 * @param roomId 房间id
	 * @return {@code RbRoomRsp }
	 */
	@Override
	public RbRoomRsp getRoomRsp(String roomId) {

		RbRoomData rbRoomData = this.getRbRoomDataByRoomId(roomId);

		RoleQuery roleQuery = new RoleQuery();
		roleQuery.setRoomId(rbRoomData.getResourceId());

		List<CrRole> crRoles = crRoleService.selectCrRoleByRoomId(roleQuery);
		// get user info
		List<String> userIds = Lists.newArrayList();
		for (CrRole role : crRoles) {
			userIds.addAll(role.getUserIdList());
		}
		Map<String, UserInfo> userInfoMap = remoteUserService.getIdUserInfoMap(userIds);

		RbRoomRsp rbRoomRsp = BeanUtils.copy(rbRoomData, RbRoomRsp.class);
		rbRoomRsp.setRolePermission(this.getSelfRoleUsers(crRoles,
				UserInfoContext.getUserContext().getUserId(), userInfoMap));
		rbRoomRsp.setRolesData(this.getRbRoomRoleRes(crRoles, userInfoMap));
		rbRoomRsp.setPreSkill(JsonUtil.toJsonObjectArray(rbRoomData.getPreSkill(), String.class));
		rbRoomRsp.setTrainSkill(
				JsonUtil.toJsonObjectArray(rbRoomData.getTrainSkill(), String.class));

		return rbRoomRsp;
	}

	/**
	 * 列表室
	 *
	 * @param listQuery 列表查询
	 * @return {@code ListRsp<RbRoomRsp> }
	 */
	@Override
	public ListRsp<RbRoomRsp> listRoom(ListQuery listQuery) {
		String userId = UserInfoContext.getUserContext().getUserId();
		// 默认时间排序
		listQuery.setSort("-cr_rb_room.create_time");

		IPage<RbRoomData> data = this.list(listQuery);

		// attack engage relation
		List<String> businessTemplateIds = data.getRecords().stream()
				.map(RbRoomData::getBusinessTemplateId).collect(Collectors.toList());

		Map<String, List<NameCount>> attackRelCount = rtksRelatedService
				.countAttackRel(businessTemplateIds);
		Map<String, List<NameCount>> icsRelCount = rtksRelatedService
				.countIcsRel(businessTemplateIds);
		Map<String, List<NameCount>> engageRelCount = rtksRelatedService
				.countEngageRel(businessTemplateIds);

		// 训练场景拓扑节点数量获取
		Map<String, TopoDigest> topologyNodesStats = remoteTopoService.batchGetTopoDigest(
				data.getRecords().stream().map(rbRoomData -> rbRoomData.getCopyedTopoId())
						.collect(Collectors.toList()));

		// get roles and users
		List<String> roomIdList = data.getRecords().stream().map(RbRoomData::getResourceId)
				.collect(Collectors.toList());
		Map<String, List<CrRole>> roomRoleMap = crRoleService
				.getRoomRoleMapByRoomIdList(roomIdList);
		List<String> userIds = Lists.newArrayList();
		for (List<CrRole> roleList : roomRoleMap.values()) {
			for (CrRole role : roleList) {
				userIds.addAll(role.getUserIdList());
			}
		}
		Map<String, UserInfo> userInfoMap = remoteUserService.getIdUserInfoMap(userIds);

		data.convert(rbRoomData -> {
			RoleQuery roleQuery = new RoleQuery();
			roleQuery.setRoomId(rbRoomData.getResourceId());

			List<CrRole> crRoles = roomRoleMap.get(rbRoomData.getResourceId());

			RbRoomRsp rbRoomRsp = BeanUtils.copy(rbRoomData, RbRoomRsp.class);
			rbRoomRsp.setNodesCount(
					topologyNodesStats.get(rbRoomData.getCopyedTopoId()).getNodeCounts());
			rbRoomRsp.setResourceOccupiedNums(
					topologyNodesStats.get(rbRoomData.getCopyedTopoId()).getResourceOccupiedNums());
			rbRoomRsp.setRolePermission(getSelfRoleUsers(crRoles, userId, userInfoMap));
			rbRoomRsp.setPreSkill(
					JsonUtil.toJsonObjectArray(rbRoomData.getPreSkill(), String.class));
			rbRoomRsp.setTrainSkill(
					JsonUtil.toJsonObjectArray(rbRoomData.getTrainSkill(), String.class));

			// set attack engage relation
			rbRoomRsp.setIsAttackRelated(
					attackRelCount.containsKey(rbRoomRsp.getBusinessTemplateId()));
			rbRoomRsp.setAttackCount(attackRelCount.getOrDefault(rbRoomRsp.getBusinessTemplateId(),
					Lists.newArrayList()));
			rbRoomRsp.setIsIcsRelated(icsRelCount.containsKey(rbRoomRsp.getBusinessTemplateId()));
			rbRoomRsp.setIcsCount(icsRelCount.getOrDefault(rbRoomRsp.getBusinessTemplateId(),
					Lists.newArrayList()));
			rbRoomRsp.setIsEngageRelated(
					engageRelCount.containsKey(rbRoomRsp.getBusinessTemplateId()));
			rbRoomRsp.setEngageCount(engageRelCount.getOrDefault(rbRoomRsp.getBusinessTemplateId(),
					Lists.newArrayList()));

			return rbRoomRsp;
		});

		return PageUtil.getListResult(data);
	}

	/**
	 * 检查学员许可
	 *
	 * @param roomId 房间id
	 * @param userId 用户id
	 */
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

	/**
	 * 获取用户列表
	 *
	 * @param roomId 房间id
	 * @return {@code List<UserRole> }
	 */
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
				userRole.setType(crRole.getRoleGroup());
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

	/**
	 * 获取任务时间轴
	 *
	 * @param roomId 房间id
	 * @return {@code TimeLineData }
	 */
	@Override
	public TimeLineData getMissionTimeline(String roomId) {
		TimeLineData timeLineData = new TimeLineData();
		setTime(timeLineData, roomId);
		List<TimeLineEvent> timeLineEventList = crTimelineService.getTimelineEventsByRoomId(roomId);
		timeLineData.setData(timeLineEventList);
		return timeLineData;
	}

	/**
	 * 检查房间权限
	 *
	 * @param roomId 房间id
	 * @param userId 用户id
	 * @return {@code CrRole }
	 */
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

	/**
	 * 检查教员许可
	 *
	 * @param roomId 房间id
	 * @param userId 用户id
	 */
	@Override
	public void checkTeacherPermission(String roomId, String userId) {
		CrRole role = crRoleService.getCrRoleByRoomIdAndUserId(roomId, userId);
		if (role.isRvbStudent()) {
			log.error("user does not have RbRoom teacher Permission！roomId=[{}], userId=[{}]",
					roomId, userId);
			throw new BusinessException(CrBusinessErrorCode.PERMISSION_DENIED);
		}
	}

	/**
	 * 先获取实例列表中自己的角色，再获取所有角色(查看列表用到)
	 *
	 * @param crRoles
	 * @param userId
	 * @param userInfoMap 用户信息地图
	 * @return
	 */
	private BaseRole getSelfRoleUsers(List<CrRole> crRoles, String userId,
			Map<String, UserInfo> userInfoMap) {
		BaseRole baseRole = new BaseRole();
		List<RoleUsers> roleUsersList = Lists.newArrayList();

		crRoles.forEach(crRole -> {
			if (crRole.containsUser(userId)) {
				baseRole.setResourceId(crRole.getResourceId());
				baseRole.setName(crRole.getName());
				// ???
				baseRole.setParentHash(crRole.getRoleGroup());
				baseRole.setDescription(crRole.getDescription());
				baseRole.setRoleGroup(crRole.getRoleGroup());
			}

			RoleUsers roleUsers = new RoleUsers();
			roleUsers.setRoleName(crRole.getName());
			roleUsers.setRoleGroup(crRole.getRoleGroup());
			roleUsers.setUsers(
					CrCommonUtil.getUserNamesByUserIds(crRole.getUserIdList(), userInfoMap));
			List<UserData> userInfoList = CrCommonUtil.getUserDataList(crRole.getUserIdList(),
					userInfoMap);
			roleUsers.setUserInfos(userInfoList);
			roleUsersList.add(roleUsers);

		});

		ChartInfo chartInfo = new ChartInfo();
		chartInfo.setInfoType(ChartInfo.ChartInfoType.NOT_RED_OR_BLUE_ONGOING.getCode());
		chartInfo.setRoleDataList(roleUsersList);

		baseRole.setChartInfo(chartInfo);

		return baseRole;
	}

	/**
	 * 获取rb房间角色结果
	 *
	 * @param crRoles     cr角色
	 * @param userInfoMap
	 * @return {@code List<RbRoomRoleRes>}
	 */
	private List<RbRoomRoleRes> getRbRoomRoleRes(List<CrRole> crRoles,
			Map<String, UserInfo> userInfoMap) {
		List<RbRoomRoleRes> rbRoomRoleResList = Lists.newArrayList();
		List<RbRoomRoleRes> redRoles = Lists.newArrayList();
		List<RbRoomRoleRes> blueRoles = Lists.newArrayList();

		for (CrRole crRole : crRoles) {
			RbRoomRoleRes rbRoomRoleRes = BeanUtils.copy(crRole, RbRoomRoleRes.class);
			rbRoomRoleRes.setUsersData(
					CrCommonUtil.getUserDataList(crRole.getUserIdList(), userInfoMap));
			rbRoomRoleRes.setUsers(crRole.getUserIdList());
			rbRoomRoleRes.setRoleGroup(crRole.getRoleGroup());

			if (crRole.isRedStudent()) {
				redRoles.add(rbRoomRoleRes);
			} else if (crRole.isBlueStudent()) {
				blueRoles.add(rbRoomRoleRes);
			} else {
				rbRoomRoleResList.add(rbRoomRoleRes);
			}
		}
		RbRoomRoleRes redRoleRes = new RbRoomRoleRes();
		redRoleRes.setName(CrRole.RbRoleGroup.RED.getDesc());
		redRoleRes.setResourceId("redResourceId");
		redRoleRes.setRoleGroup(CrRole.RbRoleGroup.RED.getCode());
		redRoleRes.setChildren(redRoles);
		rbRoomRoleResList.add(redRoleRes);

		RbRoomRoleRes blueRoleRes = new RbRoomRoleRes();
		blueRoleRes.setName(CrRole.RbRoleGroup.BLUE.getDesc());
		blueRoleRes.setResourceId("blueResourceId");
		blueRoleRes.setRoleGroup(CrRole.RbRoleGroup.BLUE.getCode());
		blueRoleRes.setChildren(blueRoles);
		rbRoomRoleResList.add(blueRoleRes);

		return rbRoomRoleResList;

	}

	/**
	 * 获取靶场角色由RB模板
	 *
	 * @param rbTemplateData RB 模板数据
	 * @param roomId         房间id
	 * @return {@code List<CrRole> }
	 */
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

	/**
	 * 获取角色由JSON 格式
	 *
	 * @param roomId    房间id
	 * @param index     指数
	 * @param redRoles  RED 角色
	 * @param roleGroup 角色组
	 * @return {@code CrRole }
	 */
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

	/**
	 * 根据模板的任务id，复制任务并获取新的id，返回新的json字符串
	 *
	 * @param missionsScheme
	 * @param roomId         房间id
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

	/**
	 * 验证名称
	 *
	 * @param name 名称
	 */
	private void validateName(String name) {
		// check if duplicate
		Integer roomExist = rbRoomMapper.countByName(name);
		if (roomExist > 0) {
			log.error("room name [{}] already exist", name);
			throw new BusinessException(CrBusinessErrorCode.TRAINING_NAME_DUPLICATE);
		}
	}

	/**
	 * 设置时间轴开始时间和结束时间
	 *
	 * @param timeLineData 时间线数据
	 * @param roomId       房间id
	 */
	private void setTime(TimeLineData timeLineData, String roomId) {
		// get start time and endtime
		RbRoom rbRoom = getOrExceptionById(roomId);
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
		timeLineData.setStartTime(startTime);
		timeLineData.setEndTime(endTime);
		timeLineData.setCurrentTime(new Date());
	}
}
