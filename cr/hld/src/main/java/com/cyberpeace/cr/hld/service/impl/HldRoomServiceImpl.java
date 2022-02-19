package com.cyberpeace.cr.hld.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.cr.crbase.service.impl.bo.SceneConfigData;
import com.cyberpeace.cr.hld.dao.entity.HldRoom;
import com.cyberpeace.cr.hld.dao.mapper.HldRoomMapper;
import com.cyberpeace.cr.hld.service.HldRoomService;
import com.cyberpeace.cr.hld.service.context.HldRoomContext;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.integratation.service.RemoteTopoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class HldRoomServiceImpl extends ServiceImpl<HldRoomMapper, HldRoom> implements HldRoomService {

	@Autowired
	private LocalSceneService localSceneService;

	@Autowired
	private RemoteTopoService remoteTopoService;

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

		save(hldRoom);

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

}
