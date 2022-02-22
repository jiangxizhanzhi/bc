package com.cyberpeace.cr.hld.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Date;
import java.util.List;

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
				List<CrRole> crRoles = getCrRoleByHldRoom(hldRoom);
				for (CrRole crRole : crRoles) {
					crRole.setBusinessType(BusinessTemplate.BusinessType.HLD.getCode());
					crRoleService.save(crRole);
				}

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
				List<CrRole> crRoles = getCrRoleByHldRoom(hldRoom);
				for (CrRole crRole : crRoles) {
					crRole.setBusinessType(BusinessTemplate.BusinessType.HLD.getCode());
					crRoleService.save(crRole);
				}
			}

		});

		return hldRoom;

	}

	@Override
	public void checkUserPermission(String roomId, String userId) {}

	private List<CrRole> getCrRoleByHldRoom(HldRoom hldRoom) {

		List<CrRole> crRoleList = Lists.newArrayList();

		CrRoleConfigHld crRoleConfigHld = JsonUtil.toJsonObject(hldRoom.getRoleConfig(),
				CrRoleConfigHld.class);

		// 获取红队的角色任务
		List<HldRoleConfig> redRoles = crRoleConfigHld.getRedRoles();
		for (HldRoleConfig hldRoleConfig : redRoles) {
			CrRole crRole = getRoleByConfig(hldRoom.getResourceId(), hldRoleConfig,
					CrRole.HldRoleGroup.RED);
			crRoleList.add(crRole);
		}

		// 获取蓝队的角色任务
		List<HldRoleConfig> blueRoles = crRoleConfigHld.getBlueRoles();
		for (HldRoleConfig hldRoleConfig : blueRoles) {
			CrRole crRole = getRoleByConfig(hldRoom.getResourceId(), hldRoleConfig,
					CrRole.HldRoleGroup.BLUE);
			crRoleList.add(crRole);
		}

		return crRoleList;
	}

	private CrRole getRoleByConfig(String roomId, HldRoleConfig hldRoleConfig,
			CrRole.HldRoleGroup roleGroup) {

		CrRoleNodesData nodesData = new CrRoleNodesData();
		nodesData.setAssetNodes(hldRoleConfig.getAssetNodes());
		nodesData.setVisibleNodes(hldRoleConfig.getVisibleNodes());

		CrRole crRole = new CrRole(hldRoleConfig.getName(), roleGroup.getCode());

		crRole.setRoomId(roomId);
		crRole.setNodesData(nodesData);
		if (hldRoleConfig.getUsers() == null) {
			crRole.setUserIds("[]");
		} else {
			crRole.setUserIds(hldRoleConfig.getUsers().toString());
		}

		return crRole;
	}

	@Override
	public List<HldRoleConfigBase> getHldRoleConfig(HldTaskContext hldTaskContext) {

		List<HldRoleConfigBase> hldRoleConfigBases = Lists.newArrayList();

		// 提取红方角色数据
		List<HldRoleContext> redTrainUser = hldTaskContext.getRedTrainUser();
		List<HldRoleConfig> redRoles = transRoleConfig(redTrainUser);
		HldRoleConfigBase redRoleConfigBase = new HldRoleConfigBase();
		redRoleConfigBase.setKey("");
		redRoleConfigBase.setType("red");
		redRoleConfigBase.setId("red");
		redRoleConfigBase.setName("red");
		redRoleConfigBase.setChildren(redRoles);
		redRoleConfigBase.setAssetNodes(Lists.newArrayList());
		redRoleConfigBase.setVisibleNodes(Lists.newArrayList());
		hldRoleConfigBases.add(redRoleConfigBase);

		// 提取蓝方角色数据
		List<HldRoleContext> blueTrainUser = hldTaskContext.getBlueTrainUser();
		List<HldRoleConfig> blueRoles = transRoleConfig(blueTrainUser);
		HldRoleConfigBase blueRoleConfigBase = new HldRoleConfigBase();
		blueRoleConfigBase.setKey("");
		blueRoleConfigBase.setType("blue");
		blueRoleConfigBase.setId("blue");
		blueRoleConfigBase.setName("blue");
		blueRoleConfigBase.setChildren(blueRoles);
		blueRoleConfigBase.setAssetNodes(Lists.newArrayList());
		blueRoleConfigBase.setVisibleNodes(Lists.newArrayList());
		hldRoleConfigBases.add(blueRoleConfigBase);

		return hldRoleConfigBases;
	}

	private List<HldRoleConfig> transRoleConfig(List<HldRoleContext> hldRoleContexts) {

		List<HldRoleConfig> hldRoleConfigs = Lists.newArrayList();
		for (HldRoleContext hldRoleContext : hldRoleContexts) {
			HldRoleConfig hldRoleConfig = BeanUtils.deepCopy(hldRoleContext, HldRoleConfig.class);
			hldRoleConfig.setAssetNodes(Lists.newArrayList());
			hldRoleConfig.setVisibleNodes(Lists.newArrayList());
			hldRoleConfig.setUsers(getRoleUsers(hldRoleContext.getUserList()));
			hldRoleConfigs.add(hldRoleConfig);
		}
		return hldRoleConfigs;
	}

	private List<String> getRoleUsers(List<HldUserContext> userList) {

		List<String> users = Lists.newArrayList();
		for (HldUserContext hldUserContext : userList) {
			users.add(hldUserContext.getUserCode());
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

}
