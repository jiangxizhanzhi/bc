package com.cyberpeace.cr.hld.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfig;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleNodesData;
import com.cyberpeace.cr.crbase.service.impl.bo.SceneConfigData;
import com.cyberpeace.cr.hld.dao.entity.HldRoom;
import com.cyberpeace.cr.hld.dao.mapper.HldRoomMapper;
import com.cyberpeace.cr.hld.service.HldRoomService;
import com.cyberpeace.cr.hld.service.context.CrRoleConfigHld;
import com.cyberpeace.cr.hld.service.context.HldRoomContext;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.LocalSceneService;
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
	private LocalSceneService localSceneService;

	@Autowired
	private RemoteTopoService remoteTopoService;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private CrRoleService crRoleService;

	@Override
	public HldRoom create(HldRoomContext hldRoomContext) {

		String userId = UserInfoContext.getUserContext().getUserId();

		HldRoom hldRoom = new HldRoom();

		// 设置拓扑id
		setCopyedTopoId(hldRoomContext, hldRoom);

		// 设置基础信息
		hldRoom.setCreateUserId(userId);
		hldRoom.setCreateTime(new Date());

		// 设置状态信息
		hldRoom.setStatus(HldRoom.StatusCode.UNOPENED.getCode());

		// 设置角色信息
		hldRoom.setRoleConfig(hldRoomContext.getRoleConfig());

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {

				save(hldRoom);

				List<CrRole> crRoles = getCrRoleByHldRoom(hldRoom);
				for (CrRole crRole : crRoles) {
					crRole.setBusinessType(BusinessTemplate.BusinessType.HLD.getCode());
					crRole.setUserIds("[]");
					crRoleService.save(crRole);
				}

			}

		});

		return hldRoom;
	}

	@Override
	public HldRoom update(HldRoomContext hldRoomContext, HldRoom hldRoom) {

		String userId = UserInfoContext.getUserContext().getUserId();

		// 设置拓扑id
		setCopyedTopoId(hldRoomContext, hldRoom);

		// 设置基础信息
		hldRoom.setModifyUserId(userId);
		hldRoom.setUpdateTime(new Date());

		// 设置角色信息
		hldRoom.setRoleConfig(hldRoomContext.getRoleConfig());

		return hldRoom;

	}

	@Override
	public void checkUserPermission(String roomId, String userId) {}

	private void setCopyedTopoId(HldRoomContext hldRoomContext, HldRoom hldRoom) {
		SceneConfigData topoData = hldRoomContext.getSceneConfigData();
		if (!topoData.getSceneConfig().isEmpty()) {
			JSONObject topoJson = new JSONObject();
			topoJson.put("scene_config", topoData.getSceneConfig());
			String topoId = localSceneService.saveTopo(topoJson,
					LocalScene.BusinessCode.HLD.getCode());
			hldRoom.setCopyedTopoId(remoteTopoService.copyTopo(topoId));
		}
	}

	private List<CrRole> getCrRoleByHldRoom(HldRoom hldRoom) {

		List<CrRole> crRoleList = Lists.newArrayList();

		CrRoleConfigHld crRoleConfigHld = JsonUtil.toJsonObject(hldRoom.getRoleConfig(),
				CrRoleConfigHld.class);

		// 获取红队的角色任务
		List<CrRoleConfig> redRoles = crRoleConfigHld.getRedRoles();
		for (CrRoleConfig crRoleConfig : redRoles) {
			CrRole crRole = getRoleByConfig(hldRoom.getResourceId(), crRoleConfig,
					CrRole.HldRoleGroup.RED);
			crRoleList.add(crRole);
		}

		// 获取蓝队的角色任务
		List<CrRoleConfig> blueRoles = crRoleConfigHld.getBlueRoles();
		for (CrRoleConfig crRoleConfig : blueRoles) {
			CrRole crRole = getRoleByConfig(hldRoom.getResourceId(), crRoleConfig,
					CrRole.HldRoleGroup.BLUE);
			crRoleList.add(crRole);
		}

		return crRoleList;
	}

	private CrRole getRoleByConfig(String roomId, CrRoleConfig crRoleConfig,
			CrRole.HldRoleGroup roleGroup) {

		CrRoleNodesData nodesData = new CrRoleNodesData();
		nodesData.setAssetNodes(crRoleConfig.getAssetNodes());
		nodesData.setVisibleNodes(crRoleConfig.getVisibleNodes());

		CrRole crRole = new CrRole(crRoleConfig.getName(), roleGroup.getCode());

		crRole.setRoomId(roomId);
		crRole.setNodesData(nodesData);

		return crRole;
	}

}
