package com.cyberpeace.cr.redvsblue.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.EnumUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cr.crbase.controller.rsp.CrLabelGroup;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.dao.entity.CrRoleAssetNode;
import com.cyberpeace.cr.crbase.dao.entity.Mission;
import com.cyberpeace.cr.crbase.dao.mapper.CrRoleAssetNodeMapper;
import com.cyberpeace.cr.crbase.dao.mapper.MissionEventDeviceMapper;
import com.cyberpeace.cr.crbase.dao.mapper.MissionMapper;
import com.cyberpeace.cr.crbase.service.CrRoleAssetNodeService;
import com.cyberpeace.cr.crbase.service.impl.CrTaskSituationHandler;
import com.cyberpeace.cr.redvsblue.dao.entity.RbRoom;
import com.cyberpeace.cr.redvsblue.service.RbRoomService;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.dao.mapper.LocalSceneMapper;
import com.cyberpeace.situation.constant.ws.ActionTypeZHL;
import com.cyberpeace.situation.model.ws.WsMissionDynamic;
import com.cyberpeace.situation.service.DynamicSchemeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
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

	@Autowired
	private CrRoleAssetNodeMapper crRoleAssetNodeMapper;

	@Autowired
	private CrRoleAssetNodeService crRoleAssetNodeService;

	@Override
	public BusinessTemplate.BusinessType getBusinessType() {
		return BusinessTemplate.BusinessType.RED_VS_BLUE;
	}

	@Override
	public void pushMissionToSituation(String missionId, Integer score) {
		List<Integer> redRoleType = Arrays.asList(ActionTypeZHL.ZHL_ATTACK3.getCode(), ActionTypeZHL.ZHL_ATTACK4.getCode(),
				ActionTypeZHL.ZHL_ATTACK5.getCode(), ActionTypeZHL.ZHL_ATTACK6.getCode());
		List<Integer> blueRoleType = Arrays.asList(ActionTypeZHL.ZHL_DEFENSE4.getCode(), ActionTypeZHL.ZHL_DEFENSE5.getCode(),
				ActionTypeZHL.ZHL_DEFENSE6.getCode());
		Mission mission = missionMapper.selectById(missionId);
		if (mission == null) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
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
		List<CrLabelGroup> crLabelGroupList = rbRoomService.missionEventDevice(mission.getRoomId());
		Map<String, String> elementMap = StreamApiUtils.toMap(crLabelGroupList, CrLabelGroup::getValue, CrLabelGroup::getLabel);
		List<String> targetName = StreamApiUtils.map(targetIds, elementMap::get);
		List<CrRoleAssetNode> crRoleAssetNodeEditList = new ArrayList<>();
		for (String targetId: targetIds) {
			LambdaQueryWrapper<CrRoleAssetNode> crRoleAssetNodeLambdaQueryWrapper = new LambdaQueryWrapper<>();
			crRoleAssetNodeLambdaQueryWrapper.eq(CrRoleAssetNode::getDeviceId, targetId);
			crRoleAssetNodeLambdaQueryWrapper.eq(CrRoleAssetNode::getRoomId, mission.getRoomId());
			List<CrRoleAssetNode> crRoleAssetNodeList = crRoleAssetNodeMapper.selectList(crRoleAssetNodeLambdaQueryWrapper);
			if (crRoleAssetNodeList.size() != 1) {
				roleType = "";
			} else if (redRoleType.contains(mission.getEventType())) {
				roleType = "red";
			} else if (blueRoleType.contains(mission.getEventType())) {
				roleType = "blue";
			}
			if (crRoleAssetNodeList.size() > 0) {
				CrRoleAssetNode crRoleAssetNode = crRoleAssetNodeList.get(0);
				if ("red".equals(roleType)) {
					crRoleAssetNode.setRoleType(CrRole.RbRoleGroup.RED.getCode());
				} else if ("blue".equals(roleType)) {
					crRoleAssetNode.setRoleType(CrRole.RbRoleGroup.BLUE.getCode());
				}
				crRoleAssetNode.setId(null);
				crRoleAssetNode.setResourceId(IDGeneratorUtil.getID());
				crRoleAssetNodeEditList.add(crRoleAssetNode);
			}
			if (wsMissionDynamicList.size() < 1 && mission.getEventType() < 7){
				//仅首个攻击有战报
				StringBuilder description = new StringBuilder("红队队员【" + userName + "】对【")
						.append(StringUtils.join(targetName, ","))
						.append("】发起【")
						.append(EnumUtils.getEnumDescByCode(mission.getEventType(), ActionTypeZHL.class))
						.append("】攻击，成功获得")
						.append(score)
						.append("分。");
				wsMissionDynamicList.add(new WsMissionDynamic(description.toString(), roleType, sceneId, targetId, mission.getEventType(), false));
			} else if (wsMissionDynamicList.size() < 1 && mission.getEventType() > 6){
				//仅首个防御有战报
				StringBuilder description = new StringBuilder("蓝队队员【" + userName + "】对【")
						.append(StringUtils.join(targetName, ","))
						.append("】进行【")
						.append(EnumUtils.getEnumDescByCode(mission.getEventType(), ActionTypeZHL.class))
						.append("】防护，成功获得")
						.append(score)
						.append("分。");
				wsMissionDynamicList.add(new WsMissionDynamic(description.toString(), roleType, sceneId, targetId, mission.getEventType(), false));
			}else {
				wsMissionDynamicList.add(new WsMissionDynamic("", roleType, sceneId, targetId, mission.getEventType(), false));
			}
		}
		crRoleAssetNodeService.saveBatch(crRoleAssetNodeEditList);
		// 待恢复 crRoleAssetNodeService.updateBatchById(crRoleAssetNodeEditList);
		dynamicSchemeService.pushMissionDynamicToRoom(wsMissionDynamicList, sceneId);
	}
}
