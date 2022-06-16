package com.cyberpeace.cr.hld.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.dao.mapper.CrRoleMapper;
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleNodesData;
import com.cyberpeace.cr.crbase.service.impl.bo.BaseInfo;
import com.cyberpeace.cr.hld.constant.HldRoleName;
import com.cyberpeace.cr.hld.dao.entity.HldRoom;
import com.cyberpeace.cr.hld.dao.mapper.HldRoomMapper;
import com.cyberpeace.cr.hld.service.HldRoomService;
import com.cyberpeace.cr.hld.service.context.*;
import com.cyberpeace.integratation.service.HldTaskService;
import com.cyberpeace.integratation.service.RemoteTopoService;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class HldRoomServiceImpl extends ServiceImpl<HldRoomMapper, HldRoom> implements HldRoomService {

	@Autowired
	private RemoteTopoService remoteTopoService;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private CrRoleService crRoleService;

	@Autowired
	private CrRoleMapper crRoleMapper;

	@Autowired
	private BusinessTemplateService businessTemplateService;

	@Autowired
	private HldTaskService hldTaskService;

	@Override
	public HldRoom create(HldRoomContext hldRoomContext) {

		HldRoom hldRoom = new HldRoom();

		// 设置基础信息
		String userId = UserInfoContext.getUserContext().getUserId();
		hldRoom.setCreateUserId(userId);
		hldRoom.setTaskId(hldRoomContext.getTaskId());

		// 设置任务名称
		String taskName = hldRoomContext.getBaseInfo().getName();
		hldRoom.setTaskName(taskName);
		hldRoomContext.getBaseInfo().setName(taskName + "_" + System.currentTimeMillis());

		// 设置状态信息
		hldRoom.setStatus(HldRoom.StatusCode.UNOPENED.getCode());

		// 设置角色信息
		hldRoom.setRoleConfig(hldRoomContext.getRoleConfig());

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {

				// 创建业务模板
				String businessTemplateId = businessTemplateService
						.addTemplate(hldRoomContext.getBaseInfo());
				BusinessTemplate businessTemplate = businessTemplateService
						.getById(businessTemplateId);

				// 设置业务模板信息
				hldRoom.setTemplateId(businessTemplateId);
				hldRoom.setCopyedTopoId(remoteTopoService.copyTopo(businessTemplate.getTopoId()));

				// 保存hld房间
				save(hldRoom);

				// 创建靶场角色记录
				saveCrRoles(hldRoom);
			}

		});

		return hldRoom;
	}

	@Override
	public HldRoom update(HldRoomContext hldRoomContext, HldRoom hldRoom) {

		// 设置基础信息
		String userId = UserInfoContext.getUserContext().getUserId();
		hldRoom.setModifyUserId(userId);

		// 修改任务名称
		String taskName = hldRoomContext.getBaseInfo().getName();
		hldRoom.setTaskName(taskName);
		hldRoomContext.getBaseInfo().setName(taskName + "_" + System.currentTimeMillis());

		// 设置角色信息
		hldRoom.setRoleConfig(hldRoomContext.getRoleConfig());

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {

				// 更新业务模板
				BusinessTemplate businessTemplate = businessTemplateService
						.getById(hldRoom.getTemplateId());
				businessTemplateService.updateTemplate(businessTemplate,
						hldRoomContext.getBaseInfo());

				// 更新房间数据
				hldRoom.setCopyedTopoId(remoteTopoService.copyTopo(businessTemplate.getTopoId()));
				updateById(hldRoom);

				// 更新角色数据
				crRoleService.clearRoomRoles(hldRoom.getResourceId());
				saveCrRoles(hldRoom);
			}

		});

		return hldRoom;

	}

	@Override
	public void checkUserPermission(String roomId, String userId) {

		// 只有导演允许操作场景
		CrRole directorRole = crRoleMapper.getCrRoleByRoomIdAndRoleName(roomId,
				HldRoleName.DIRECTOR.getCrName());

		if (!directorRole.getUserIdList().contains(userId)) {
			throw new BusinessException(CrBusinessErrorCode.INSUFFICIENT_ROLE_PERMISSIONS);
		}
	}

	private void addCrRoles(List<CrRole> crRoleList, List<HldRoleConfig> hldRoleConfigs,
			String roomId, CrRole.HldRoleGroup hldRoleGroup) {
		for (HldRoleConfig hldRoleConfig : hldRoleConfigs) {
			CrRole crRole = getCrRoleByConfig(roomId, hldRoleConfig, hldRoleGroup);
			crRoleList.add(crRole);
		}
	}

	private List<CrRole> getCrRoles(HldRoom hldRoom) {

		List<CrRole> crRoleList = Lists.newArrayList();

		HldRolesConfig hldRolesConfig = JsonUtil.toJsonObject(hldRoom.getRoleConfig(),
				HldRolesConfig.class);
		String roomId = hldRoom.getResourceId();

		// 获取红队的角色
		addCrRoles(crRoleList, hldRolesConfig.getRedRoles(), roomId, CrRole.HldRoleGroup.RED);

		// 获取蓝队的角色
		addCrRoles(crRoleList, hldRolesConfig.getBlueRoles(), roomId, CrRole.HldRoleGroup.BLUE);

		// 获取白队的角色
		addCrRoles(crRoleList, hldRolesConfig.getWhiteRoles(), roomId, CrRole.HldRoleGroup.WHITE);

		return crRoleList;
	}

	private void saveCrRoles(HldRoom hldRoom) {
		List<CrRole> crRoles = getCrRoles(hldRoom);
		for (CrRole crRole : crRoles) {
			crRole.setBusinessType(BusinessTemplate.BusinessType.HLD.getCode());
			crRoleService.save(crRole);
		}
	}

	private CrRole getCrRoleByConfig(String roomId, HldRoleConfig hldRoleConfig,
			CrRole.HldRoleGroup roleGroup) {

		// 设置节点数据
		CrRoleNodesData nodesData = new CrRoleNodesData();
		nodesData.setAssetNodes(hldRoleConfig.getAssetNodes());
		nodesData.setVisibleNodes(hldRoleConfig.getVisibleNodes());

		CrRole crRole = new CrRole(hldRoleConfig.getName(), roleGroup.getCode());

		crRole.setRoomId(roomId);
		crRole.setNodesData(nodesData);

		// 设置角色用户数据
		if (hldRoleConfig.getUsers() == null) {
			crRole.setUserIds("[]");
		} else {
			crRole.setUserIdList(hldRoleConfig.getUsers());
		}

		return crRole;
	}

	private void addTaskRoleConfig(List<TaskRoleConfig> taskRoleConfigs,
			List<TaskRoleContext> taskRoleContexts, String name) {

		List<HldRoleConfig> hldRoleConfigs = transRoleConfig(taskRoleContexts);
		taskRoleConfigs.add(new TaskRoleConfig(name, hldRoleConfigs));

	}

	@Override
	public List<TaskRoleConfig> getTaskRoleConfig(HldTaskContext hldTaskContext) {

		List<TaskRoleConfig> taskRoleConfigs = Lists.newArrayList();

		// 提取红方角色数据
		addTaskRoleConfig(taskRoleConfigs, hldTaskContext.getRedTrainUser(), "red");

		// 提取蓝方角色数据
		addTaskRoleConfig(taskRoleConfigs, hldTaskContext.getBlueTrainUser(), "blue");

		// 构建管理员数据
		UserInfo userInfo = UserInfoContext.getUserContext();
		TaskUserInfo admin = new TaskUserInfo();
		admin.setName(userInfo.getName());
		admin.setId(userInfo.getUserId());
		admin.setUserName(userInfo.getUserName());
		List<TaskUserInfo> admins = Lists.newArrayList(admin);
		TaskRoleContext adminRoleContext = getRoleFromUsers(admins, HldRoleName.ADMIN.getCrName());

		// 获取导演数据
		List<TaskUserInfo> directors = hldTaskContext.getControlUser();
		TaskRoleContext directorRoleContext = getRoleFromUsers(directors,
				HldRoleName.DIRECTOR.getCrName());

		// 获取裁判数据
		List<TaskUserInfo> referees = hldTaskContext.getAssessUser();
		TaskRoleContext refereeRoleContext = getRoleFromUsers(referees,
				HldRoleName.REFEREE.getCrName());

		// 提取白方角色数据
		List<TaskRoleContext> whiteUsers = Lists.newArrayList();
		whiteUsers.add(adminRoleContext);
		whiteUsers.add(directorRoleContext);
		whiteUsers.add(refereeRoleContext);

		addTaskRoleConfig(taskRoleConfigs, whiteUsers, "white");

		return taskRoleConfigs;
	}

	@Override
	public List<TaskRoleConfig> getRoomRoleConfig(HldRolesConfig hldRolesConfig) {

		List<TaskRoleConfig> taskRoleConfigs = Lists.newArrayList();

		taskRoleConfigs.add(new TaskRoleConfig("red", hldRolesConfig.getRedRoles()));
		taskRoleConfigs.add(new TaskRoleConfig("blue", hldRolesConfig.getBlueRoles()));
		taskRoleConfigs.add(new TaskRoleConfig("white", hldRolesConfig.getWhiteRoles()));

		return taskRoleConfigs;
	}

	private TaskRoleContext getRoleFromUsers(List<TaskUserInfo> taskUserInfos, String roleName) {

		TaskRoleContext taskRoleContext = new TaskRoleContext();
		taskRoleContext.setUserList(taskUserInfos);
		taskRoleContext.setName(roleName);

		return taskRoleContext;
	}

	private List<HldRoleConfig> transRoleConfig(List<TaskRoleContext> taskRoleContexts) {

		List<HldRoleConfig> hldRoleConfigs = Lists.newArrayList();
		for (TaskRoleContext taskRoleContext : taskRoleContexts) {
			HldRoleConfig hldRoleConfig = BeanUtils.deepCopy(taskRoleContext, HldRoleConfig.class);
			hldRoleConfig.setAssetNodes(Lists.newArrayList());
			hldRoleConfig.setVisibleNodes(Lists.newArrayList());
			hldRoleConfig.setUsers(getRoleUsers(taskRoleContext.getUserList()));
			hldRoleConfigs.add(hldRoleConfig);
		}

		return hldRoleConfigs;
	}

	private List<String> getRoleUsers(List<TaskUserInfo> userList) {

		List<String> users = Lists.newArrayList();
		for (TaskUserInfo taskUserInfo : userList) {
			users.add(taskUserInfo.getId());
		}

		return users;
	}

	@Override
	public BaseInfo getHldBaseInfo(HldTaskContext hldTaskContext) {

		BaseInfo baseInfo = new BaseInfo();

		baseInfo.setType(BusinessTemplate.BusinessType.HLD.getCode());

		// 设置任务名称
		if (hldTaskContext.getTaskName() == null || hldTaskContext.getTaskName().isEmpty()) {
			baseInfo.setName("task");
		} else {
			baseInfo.setName(hldTaskContext.getTaskName());
		}

		// 设置任务描述
		if (hldTaskContext.getAttackTaskSceneDes() == null) {
			baseInfo.setDescription("");
		} else {
			baseInfo.setDescription(hldTaskContext.getAttackTaskSceneDes());
		}
		baseInfo.setDifficult(0);

		return baseInfo;
	}

	@Override
	public CrRole getUserRole(String roomId, String userId) {

		List<CrRole> crRoles = Lists.newArrayList();
		if (userId != null) {
			crRoles = crRoleMapper.selectCrRoleByRoomIdAndUserId(roomId, userId);
		}

		// 取不到用户角色信息时抛出异常
		if (crRoles.isEmpty()) {
			throw new BusinessException(CrBusinessErrorCode.ROLE_NOT_EXIST_ERROR);
		}

		// 只取到一个角色时直接返回
		if (crRoles.size() == 1) {
			return crRoles.get(0);
		}

		// 当用户角色中不包含管理员且角色数大于2时，
		CrRole adminRole = crRoleMapper.getCrRoleByRoomIdAndRoleName(roomId,
				HldRoleName.ADMIN.getCrName());
		if (adminRole != null && crRoles.remove(adminRole) && crRoles.size() == 1) {
			return crRoles.get(0);
		} else {
			throw new BusinessException(CrBusinessErrorCode.TASK_ROLE_CONFLICT);
		}
	}

	@Override
	public JSONObject getTaskInfo(String taskId) {

		JSONObject taskInfo = hldTaskService.getTaskInfo(taskId);

		// 获取不到数据时抛出异常
		if (null == taskInfo) {
			throw new BusinessException(CrBusinessErrorCode.FAILED_TO_GET_TASK_INFO);
		}

		return taskInfo;
	}

	@Override
	public String getHldRoleName(String crRoleName) {
		if (HldRoleName.DIRECTOR.getCrName().equals(crRoleName)) {
			return HldRoleName.DIRECTOR.getHldName();
		} else if (HldRoleName.REFEREE.getCrName().equals(crRoleName)) {
			return HldRoleName.REFEREE.getHldName();
		} else {
			return crRoleName;
		}
	}

	@Override
	public HldRoom getHldRoom(String roomId) {

		HldRoom hldRoom = getById(roomId);

		if (hldRoom == null) {
			throw new BusinessException(CrBusinessErrorCode.ROOM_NOT_EXIST);
		}

		return hldRoom;
	}

}
