package com.cyberpeace.cr.redvsblue.service.impl;

import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.EnumUtils;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cr.crbase.controller.rsp.CrLabelGroup;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.dao.entity.Mission;
import com.cyberpeace.cr.crbase.dao.entity.MissionEventDevice;
import com.cyberpeace.cr.crbase.dao.mapper.CrRoleMapper;
import com.cyberpeace.cr.crbase.dao.mapper.MissionEventDeviceMapper;
import com.cyberpeace.cr.crbase.dao.mapper.MissionMapper;
import com.cyberpeace.cr.crbase.service.impl.CrTaskSituationHandler;
import com.cyberpeace.cr.redvsblue.dao.entity.RbRoom;
import com.cyberpeace.cr.redvsblue.service.RbRoomService;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.dao.mapper.LocalSceneMapper;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.situation.constant.ws.ActionTypeZHL;
import com.cyberpeace.situation.model.ws.WsMissionDynamic;
import com.cyberpeace.situation.service.DynamicSchemeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RbTaskSituationHandler extends CrTaskSituationHandler {

	@Autowired
	private MissionMapper missionMapper;

	@Autowired
	private MissionEventDeviceMapper missionEventDeviceMapper;

	@Autowired
	private RbRoomService rbRoomService;

	@Autowired
	private DynamicSchemeService dynamicSchemeService;

	@Autowired
	private LocalSceneMapper localSceneMapper;

	@Override
	public BusinessTemplate.BusinessType getBusinessType() {
		return BusinessTemplate.BusinessType.RED_VS_BLUE;
	}

	@Override
	public void pushMissionToSituation(String missionId, Integer score) {
		Mission mission = missionMapper.selectById(missionId);
		RbRoom rbRoom = rbRoomService.getById(mission.getRoomId());
		if (rbRoom == null){
			return;
		}
		List<WsMissionDynamic> wsMissionDynamicList = new ArrayList<>();
		String sceneId;
		LocalScene localScene = localSceneMapper.getByBusinessInstanceId(mission.getRoomId());
		if (localScene == null) {
			sceneId = mission.getRoomId();
		} else {
			sceneId = localScene.getMpSceneId();
		}
		List<String> targetIds = missionEventDeviceMapper.getDeviceIdsByMissionId(missionId);
		String userName = UserInfoContext.getUserContext().getUserName();
		String roleType = "";
		if (mission.getEventType() > 2 && mission.getEventType() < 7) {
			roleType = "red";
		} else if (mission.getEventType() > 9 && mission.getEventType() < 13) {
			roleType = "blue";
		}
		List<CrLabelGroup> crLabelGroupList = rbRoomService.missionEventDevice(mission.getRoomId());
		Map<String, String> elementMap = StreamApiUtils.toMap(crLabelGroupList, CrLabelGroup::getValue, CrLabelGroup::getLabel);
		List<String> targetName = StreamApiUtils.map(targetIds,nodeId -> elementMap.get(nodeId));
		for (String targetId: targetIds) {
			if (wsMissionDynamicList.size() < 1 && mission.getEventType() < 7){
				//首个攻击
				StringBuilder description = new StringBuilder("红队队员【" + userName + "】对【")
						.append(StringUtils.join(targetName, ","))
						.append("】发起【")
						.append(EnumUtils.getEnumByCode(mission.getEventType(), ActionTypeZHL.class))
						.append("】攻击，成功获得")
						.append(score)
						.append("分。");
				wsMissionDynamicList.add(new WsMissionDynamic(description.toString(), roleType, sceneId, targetId, mission.getEventType()));
			} else if (wsMissionDynamicList.size() < 1 && mission.getEventType() > 6){
				//首个防御
				StringBuilder description = new StringBuilder("蓝队队员【" + userName + "】对【")
						.append(StringUtils.join(targetName, ","))
						.append("】进行【")
						.append(EnumUtils.getEnumByCode(mission.getEventType(), ActionTypeZHL.class))
						.append("】防护，成功获得")
						.append(score)
						.append("分。");
				wsMissionDynamicList.add(new WsMissionDynamic(description.toString(), roleType, sceneId, targetId, mission.getEventType()));
			}else {
				wsMissionDynamicList.add(new WsMissionDynamic("", roleType, sceneId, targetId, mission.getEventType()));
			}
		}
		dynamicSchemeService.pushMissionDynamicToRoom(wsMissionDynamicList, sceneId);
	}
}
