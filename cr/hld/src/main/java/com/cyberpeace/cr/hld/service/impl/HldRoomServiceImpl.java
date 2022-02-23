package com.cyberpeace.cr.hld.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleNodesData;
import com.cyberpeace.cr.crbase.service.impl.bo.BaseInfo;
import com.cyberpeace.cr.hld.dao.entity.HldRoom;
import com.cyberpeace.cr.hld.dao.mapper.HldRoomMapper;
import com.cyberpeace.cr.hld.service.HldRoomService;
import com.cyberpeace.cr.hld.service.context.*;
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
	private BusinessTemplateService businessTemplateService;

	@Override
	public HldRoom create(HldRoomContext hldRoomContext) {

		HldRoom hldRoom = new HldRoom();

		// 设置基础信息
		String userId = UserInfoContext.getUserContext().getUserId();
		hldRoom.setCreateUserId(userId);
		hldRoom.setTaskId(hldRoomContext.getTaskId());

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
				updateById(hldRoom);

				// 更新角色数据
				crRoleService.clearRoomRoles(hldRoom.getResourceId());
				saveCrRoles(hldRoom);
			}

		});

		return hldRoom;

	}

	@Override
	public void checkUserPermission(String roomId, String userId) {}

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
			crRole.setUserIds(hldRoleConfig.getUsers().toString());
		}

		return crRole;
	}

	private void addTaskRoleConfig(List<TaskRoleConfig> taskRoleConfigs,
			List<TaskRoleContext> taskRoleContexts, String name) {

		List<HldRoleConfig> hldRoleConfigs = transRoleConfig(taskRoleContexts);

		TaskRoleConfig taskRoleConfig = new TaskRoleConfig();
		taskRoleConfig.setKey("");
		taskRoleConfig.setType(name);
		taskRoleConfig.setId(name);
		taskRoleConfig.setName(name);
		taskRoleConfig.setChildren(hldRoleConfigs);
		taskRoleConfig.setAssetNodes(Lists.newArrayList());
		taskRoleConfig.setVisibleNodes(Lists.newArrayList());
		taskRoleConfigs.add(taskRoleConfig);

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
		admin.setUserCode(userInfo.getUserId());
		admin.setUserName(userInfo.getUserName());
		TaskRoleContext adminRoleContext = getRoleFromUser(admin, "admin");

		// 获取导演数据
		TaskUserInfo director = hldTaskContext.getControlUser();
		TaskRoleContext directorRoleContext = getRoleFromUser(director, "director");

		// 获取裁判数据
		TaskUserInfo referee = hldTaskContext.getAssessUser();
		TaskRoleContext refereeRoleContext = getRoleFromUser(referee, "referee");

		// 提取白方角色数据
		List<TaskRoleContext> whiteUsers = Lists.newArrayList();
		whiteUsers.add(adminRoleContext);
		whiteUsers.add(directorRoleContext);
		whiteUsers.add(refereeRoleContext);

		addTaskRoleConfig(taskRoleConfigs, whiteUsers, "white");

		return taskRoleConfigs;
	}

	private TaskRoleContext getRoleFromUser(TaskUserInfo taskUserInfo, String roleName) {

		TaskRoleContext taskRoleContext = new TaskRoleContext();
		List<TaskUserInfo> taskUserInfos = Lists.newArrayList();
		taskUserInfos.add(taskUserInfo);
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
			users.add(taskUserInfo.getUserCode());
		}

		return users;
	}

	@Override
	public BaseInfo getHldBaseInfo(HldTaskContext hldTaskContext) {

		BaseInfo baseInfo = new BaseInfo();
		baseInfo.setType(BusinessTemplate.BusinessType.HLD.getCode());
		baseInfo.setName(hldTaskContext.getTaskName());
		baseInfo.setDescription(hldTaskContext.getAttackTaskSenceDes());
		baseInfo.setDifficult(0);

		return baseInfo;
	}

	@Override
	public CrRole getUserRole(String roomId, String userId) {

		return crRoleService.getCrRoleByRoomIdAndUserId(roomId, userId);

	}

}
