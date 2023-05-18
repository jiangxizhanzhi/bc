package com.cyberpeace.cr.crbase.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.crbase.dao.entity.MissionTemplate;
import com.cyberpeace.cr.crbase.service.CrCommonService;
import com.cyberpeace.cr.crbase.service.MissionTemplateService;
import com.cyberpeace.cr.crbase.service.context.role.*;
import com.cyberpeace.cr.crbase.service.context.scene.MonitorSession;
import com.cyberpeace.cr.crbase.utils.CrCommonUtil;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.cs.service.context.scene.Port;
import com.cyberpeace.cs.service.context.scene.SceneData;
import com.cyberpeace.cs.service.context.scene.SceneElement;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class CrCommonServiceImpl implements CrCommonService {

	@Autowired
	private LocalSceneService localSceneService;

	@Autowired
	private RemoteUserService remoteUserService;

	@Autowired
	private MissionTemplateService missionTemplateService;

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
		List<MonitorSession> sessionList = sessions.toJavaList(MonitorSession.class);
		// 3. 获取用户名、用户头像、ip
		JSONObject sceneData = localSceneService.getSceneData(localScene,
				UserInfoContext.getUserContext().getUserId());
		SceneData data = sceneData.toJavaObject(SceneData.class);
		Map<String, String> ipMap = Maps.newHashMap();
		for (SceneElement element : data.getTopology().getElements()) {
			for (Port port : element.getPorts()) {
				ipMap.put(element.getId(), port.getIp());
				break;
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
			session.setUsername(userInfo.getUserName());
			session.setAvatar(userInfo.getAvatarUrl());
			session.setIp(ipMap.get(session.getNodeId()));
		}

		return sessionList;
	}

	@Override
	public Boolean isStopped(JSONObject sceneData) {
		SceneData data = sceneData.toJavaObject(SceneData.class);
		return data.isStopped();
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
			log.error("Mission id duplicate  or part of missions miss! missionTemplateIds = [{}]", StringUtils.join(missionTemplateIds, ","));
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
}
