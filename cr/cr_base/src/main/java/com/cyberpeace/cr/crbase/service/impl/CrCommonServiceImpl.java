package com.cyberpeace.cr.crbase.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.crbase.constant.MissionWsCode;
import com.cyberpeace.cr.crbase.dao.entity.CrMark;
import com.cyberpeace.cr.crbase.dao.entity.MissionTemplate;
import com.cyberpeace.cr.crbase.dao.mapper.CrMarkMapper;
import com.cyberpeace.cr.crbase.service.CrCommonService;
import com.cyberpeace.cr.crbase.service.CrTaskService;
import com.cyberpeace.cr.crbase.service.MissionTemplateService;
import com.cyberpeace.cr.crbase.service.context.mark.MarkAddReq;
import com.cyberpeace.cr.crbase.service.context.role.*;
import com.cyberpeace.cr.crbase.service.context.scene.MonitorSession;
import com.cyberpeace.cr.crbase.utils.CrCommonUtil;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.integratation.model.mp.MpHostState;
import com.cyberpeace.integratation.model.scene.SceneNodeListReq;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CrCommonServiceImpl implements CrCommonService {

	@Autowired
	private LocalSceneService localSceneService;

	@Autowired
	private RemoteUserService remoteUserService;

	@Autowired
	private MissionTemplateService missionTemplateService;

	@Autowired
	private CrMarkMapper crMarkMapper;

	@Autowired
	private CrTaskService crTaskService;

	@Override
	public List<MonitorSession> getMonitorSessions(String localSceneId, String studentId) {
		// 1. 查询localScene
		LocalScene localScene = localSceneService.getById(localSceneId);
		if (localScene == null) {
			log.error("Scene not exist! localSceneId = [{}]", localSceneId);
			throw new BusinessException(CrBusinessErrorCode.LOCAL_SCENE_NOT_EXIST);
		}
		// 2. 获取中台会话数据
		JSONArray sessions = localSceneService.getMonitorSessions(localScene, studentId);
		if (sessions.isEmpty()) {
			return Lists.newArrayList();
		}
		List<MonitorSession> sessionList = sessions.toJavaList(MonitorSession.class);
		List<String> nodeIds = sessionList.stream().map(MonitorSession::getNodeId).distinct()
				.collect(Collectors.toList());
		// 2.1 获取节点状态信息
		List<MpHostState> stateList = localSceneService.getHostStateList(localScene.getMpSceneId(),
				nodeIds);
		Map<String, MpHostState> stateMap = StreamApiUtils.toMap(stateList,
				MpHostState::getElementId, state -> state);
		// 3. 获取用户名、用户头像、ip
		// 获取ip
		SceneNodeListReq req = new SceneNodeListReq();
		req.setId(localScene.getMpSceneId());
		List<JSONObject> assetList = localSceneService.getAssetList(req);
		Map<String, String> ipMap = Maps.newHashMap();
		for (JSONObject object : assetList) {
			String id = object.getString("id");
			JSONArray ports = object.getJSONArray("ports");
			if (!ports.isEmpty()) {
				String ip = ports.getJSONObject(0).getString("ip");
				ipMap.put(id, ip);
			}
		}
		// 3.1 批量获取用户信息
		List<String> userIds = Lists.newArrayList();
		for (MonitorSession session : sessionList) {
			userIds.add(session.getUser());
		}
		Map<String, UserInfo> userInfoMap = remoteUserService.getIdUserInfoMap(userIds);

		for (MonitorSession session : sessionList) {
			UserInfo userInfo = userInfoMap.get(session.getUser());
			if (userInfo == null) {
				continue;
			}
			session.setUsername(userInfo.getName());
			session.setAvatar(userInfo.getAvatarUrl());
			session.setIp(ipMap.get(session.getNodeId()));
			// cpu, memory
			MpHostState state = stateMap.get(session.getNodeId());
			if (null != state) {
				session.setCpuPercent(state.getCpuPercent());
				session.setMemoryPercent(state.getMemoryPercent());
			}
		}

		return sessionList;
	}

	@Override
	public List<String> validateRoleConfig(List<CrRoleConfig> roleConfigs) {

		List<String> roleNames = Lists.newArrayList();
		List<String> missionTemplateIds = Lists.newArrayList();
		for (CrRoleConfig config : roleConfigs) {
			roleNames.add(config.getName());
			missionTemplateIds.addAll(validateMissionsScheme(config.getMissionsScheme()));
		}
		// 校验角色名称是否重复
		CrCommonValidator.checkStringListDuplicate(roleNames,
				CrBusinessErrorCode.ROLE_NAME_DUPLICATE);
		// 校验任务模板id
		CrCommonValidator.checkStringListDuplicate(missionTemplateIds,
				CrBusinessErrorCode.MISSION_LESS);

		return missionTemplateIds;
	}

	@Override
	public List<String> validateMissionsScheme(CrRoleConfigMissionsScheme missionsScheme) {
		List<String> missionTemplateIds = CrCommonUtil.getMissionTemplateIds(missionsScheme);
		// 任务模板必须包含id
		missionTemplateIds.forEach(missionTemplateId -> CrCommonValidator
				.checkStringExist(missionTemplateId, CrBusinessErrorCode.EMPTY_MISSION_TEMPLATE));
		// 获取任务map
		Map<String, MissionTemplate> missionTemplateMap = missionTemplateService
				.getMappingByIds(missionTemplateIds);
		// 校验任务id数量和查询到的任务数量是否匹配
		if (missionTemplateIds.size() != missionTemplateMap.size()) {
			log.error("Mission id duplicate  or part of missions miss! missionTemplateIds = [{}]",
					StringUtils.join(missionTemplateIds, ","));
			throw new BusinessException(CrBusinessErrorCode.MISSION_LESS);
		}

		List<String> stageNames = Lists.newArrayList();
		for (CrRoleConfigSchemeStage stage : missionsScheme.getSchemeData()) {
			// 校验stage name 长度
			CrCommonValidator.validateLength(stage.getStage(), CrCommonConstants.MIN_STAGE_NAME_LEN,
					CrCommonConstants.MAX_STAGE_NAME_LEN, CrBusinessErrorCode.INVALID_STAGE_NAME);
			stageNames.add(stage.getStage());
			// 校验 阶段内任务不可为空
			CrCommonValidator.validateListIfEmpty(stage.getMissions(),
					CrBusinessErrorCode.EMPTY_MISSION_STAGE);
			List<String> missionTitles = Lists.newArrayList();
			for (CrRoleConfigMission missionTemplate : stage.getMissions()) {
				missionTitles.add(missionTemplate.getTitle());
			}
			// 校验阶段内任务重名
			CrCommonValidator.checkStringListDuplicate(missionTitles,
					CrBusinessErrorCode.DUPLICATED_MISSION_NAME);
		}
		// 校验阶段重名
		CrCommonValidator.checkStringListDuplicate(stageNames,
				CrBusinessErrorCode.DUPLICATED_STAGE_NAME);

		return missionTemplateIds;
	}

	@Override
	public void validateRbRoleConfig(CrRoleConfigRb roleConfigRb) {
		List<String> missionTemplateIds = Lists.newArrayList();
		// validate red roles
		missionTemplateIds.addAll(validateRoleConfig(roleConfigRb.getRedRoles()));
		// validate blue roles
		missionTemplateIds.addAll(validateRoleConfig(roleConfigRb.getBlueRoles()));
		// validate mission template ids
		CrCommonValidator.checkStringListDuplicate(missionTemplateIds,
				CrBusinessErrorCode.MISSION_LESS);
	}

	@Override
	public void addMark(MarkAddReq req, Integer code) {
		req.validate();
		CrMark mark = new CrMark();
		mark.setName(req.getName());
		mark.setDescription(req.getDescription());
		mark.setRoomId(req.getRoomId());
		mark.setUserId(UserInfoContext.getUserContext().getUserId());
		mark.setType(code);
		mark.setEvidence(JsonUtil.arrayToString(req.getEvidence()));

		crMarkMapper.insert(mark);
		this.pushMarkWs(req.getRoomId(), MissionWsCode.CR_MARK_CREATE);
	}

	@Override
	public List<CrMark> getMarkByRoomId(String roomId) {
		return crMarkMapper.selectByRoomId(roomId);
	}

    @Override
    public List<CrMark> getMarkByRoomIdAndUserId(String roomId, String userId) {
		return crMarkMapper.selectByRoomIdAndUserId(roomId, userId);
    }

	/**
	 * push标记ws
	 * 暂时借用mission 的ws
	 */
	private void pushMarkWs(String businessId, Integer code){
		crTaskService.pushMissionWS(businessId, code);
	}
}
