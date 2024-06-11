package com.cyberpeace.oj.ojbase.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.JsonKeyConstant;
import com.cyberpeace.common.constant.PatternConstant;
import com.cyberpeace.common.enums.BusinessCode;
import com.cyberpeace.common.enums.SceneEvent;
import com.cyberpeace.common.enums.SceneEventStatus;
import com.cyberpeace.common.enums.SceneStatusEnum;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.dao.mapper.LocalSceneMapper;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.cs.service.context.TopoSwitchRecordingContext;
import com.cyberpeace.integratation.service.RemoteSceneService;
import com.cyberpeace.integratation.service.RemoteTopoService;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.integratation.service.SystemConfigService;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import com.cyberpeace.oj.ojbase.constant.OjEvent;
import com.cyberpeace.oj.ojbase.constant.SceneShare;
import com.cyberpeace.oj.ojbase.dao.entity.OjLocalScene;
import com.cyberpeace.oj.ojbase.dao.mapper.OjLocalSceneMapper;
import com.cyberpeace.oj.ojbase.service.OjLocalSceneService;
import com.cyberpeace.oj.ojbase.service.bo.MonitorInfoBo;
import com.cyberpeace.oj.ojbase.service.bo.SceneMonitorBo;
import com.cyberpeace.oj.ojbase.service.context.InitOjSceneContext;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OjLocalSceneServiceImpl extends ServiceImpl<OjLocalSceneMapper, OjLocalScene> implements OjLocalSceneService {

	@Autowired
	private OjLocalSceneMapper ojLocalSceneMapper;

	@Autowired
	private SystemConfigService systemConfigService;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private LocalSceneMapper localSceneMapper;

	@Autowired
	private RemoteSceneService remoteSceneService;

	@Autowired
	private RemoteTopoService remoteTopoService;

	@Autowired
	private LocalSceneService localSceneService;

	@Autowired
	private RemoteUserService remoteUserService;

	@Override
	public OjLocalScene selectOjLocalScene(String businessId, Integer shared,
			AuthContext authContext) {
		// 无共享类型 默认为无场景
		if (null == shared) {
			return null;
		}

		if (shared.intValue() == SceneShare.SHARED.getCode().intValue()) {
			return ojLocalSceneMapper.selectPublicScene(businessId, CommonConstant.TRUE,
					authContext.getIsWeb());
		} else if (shared.intValue() == SceneShare.PRIVATE.getCode().intValue()) {
			return ojLocalSceneMapper.selectPrivateScene(authContext.getUserId(), businessId,
					authContext.getIsWeb());
		} else if (shared.intValue() == SceneShare.GROUP.getCode().intValue()) {
			return ojLocalSceneMapper.selectGroupScene(businessId);
		}

		return null;

	}

	@Override
	public OjLocalScene initOjLocalScene(InitOjSceneContext sceneContext) {

		OjLocalScene ojLocalScene = BeanUtils.copy(sceneContext, OjLocalScene.class);
		ojLocalScene.setResourceId(IDGeneratorUtil.getID());

		// 共享设置
		ojLocalScene.setShared(
				sceneContext.getSharedType().intValue() == SceneShare.SHARED.getCode().intValue());

		// flag设置 静态flag设置为操作题答案 动态flag设置在修改topo时
		if (sceneContext.getDynamicFlag().intValue() == CommonConstant.ZERO) {
			ojLocalScene.setFlags(JsonUtil.toJsonString(sceneContext.getFlags()));
		}

		return ojLocalScene;
	}

	/**
	 * 获取固定数量的flag
	 * @param count 数量
	 * @return
	 */
	private List<String> getDynamicFlags(int count) {

		List<String> flags = Lists.newArrayList();

		String templateData = ConfigUtil.getContextProperty("oj.flag.template.data");
		for (int i = 0; i < count; i++) {
			String flagPrefix = systemConfigService.getSystemConfig("flag_prefix");
			String FLAG_LENGTH = systemConfigService.getSystemConfig("flag_length");

			String flag = String.format(templateData, flagPrefix,
					LocalStringUtil.getRandomStr(Integer.parseInt(FLAG_LENGTH)));
			flags.add(flag);
		}

		return flags;
	}

	@Override
	public JSONObject setDynamicFlag(OjLocalScene ojLocalScene, JSONObject topo) {

		if (null == topo || topo.isEmpty()) {
			return topo;
		}

		JSONObject sceneConfig = topo.getJSONObject(JsonKeyConstant.SCENE_CONFIG);
		if (null == sceneConfig || sceneConfig.isEmpty()) {
			return topo;
		}

		JSONObject topology = sceneConfig.getJSONObject(JsonKeyConstant.TOPOLOGY);
		if (null == topology || topology.isEmpty()) {
			return topo;
		}

		JSONArray elements = topology.getJSONArray(JsonKeyConstant.ELEMENTS);
		if (null == elements || elements.isEmpty()) {
			return topo;
		}

		List<String> flags = Lists.newArrayList();

		for (Object element : elements) {
			JSONObject node = (JSONObject) element;
			List<String> initCommands = JsonUtil
					.toJsonObjectArray(node.getString(JsonKeyConstant.INIT_COMMANDS), String.class);

			if (CollectionUtils.isNotEmpty(initCommands)) {

				// init_command命令注入
				for (int i = 0; i < initCommands.size(); i++) {
					String replaceCommand = replaceDynamicFlag(initCommands.get(i), flags);
					if (StringUtils.isNotBlank(replaceCommand)) {
						initCommands.set(i, replaceCommand);
					}
				}
				node.put(JsonKeyConstant.INIT_COMMANDS, initCommands);
			}

			// node_command命令注入
			String nodeCommand = node.getString(JsonKeyConstant.NODE_COMMAND);
			if (StringUtils.isNotBlank(nodeCommand)) {
				String handlerCommand = replaceDynamicFlag(nodeCommand, flags);
				node.put(JsonKeyConstant.NODE_COMMAND, handlerCommand);
			}
		}

		// 设置答案
		ojLocalScene.setFlags(JsonUtil.arrayToString(flags));

		return topo;
	}

	/**
	 * 动态flag命令替换
	 *
	 * @param dynamicFlagCommand
	 * @param flags
	 * @return
	 */
	private String replaceDynamicFlag(String dynamicFlagCommand, List<String> flags) {

		List<String> matchStr = LocalStringUtil.getMatchStr(dynamicFlagCommand,
				PatternConstant.DYNAMIC_FLAG);

		if (CollectionUtils.isEmpty(matchStr)) {
			return CommonConstant.EMPTY_STRING;
		}

		// 补全flag与init_command长度
		List<String> flagList = getDynamicFlags(matchStr.size());
		String replaceCommand = LocalStringUtil.replaceAll(dynamicFlagCommand, matchStr, flagList);
		flags.addAll(flagList);
		return replaceCommand;
	}

	@Override
	public OjLocalScene deleteLocalScene(String ojLocalSceneId) {
		OjLocalScene ojLocalScene = ojLocalSceneMapper.selectById(ojLocalSceneId);
		if (ojLocalScene == null) {
			return null;
		}

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				ojLocalSceneMapper.deleteById(ojLocalScene.getResourceId());
				localSceneMapper.deleteById(ojLocalScene.getLocalSceneId());
			}
		});

		return ojLocalScene;
	}

	@Override
	public Boolean deleteLocalAndRemoteScene(OjLocalScene ojLocalScene) {

		if (null != ojLocalScene) {
			// 删除中台场景
			LocalScene localScene = localSceneMapper.selectById(ojLocalScene.getLocalSceneId());
			localSceneService.deleteRemoteScene(localScene);

			// 删除本地场景
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {

				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					ojLocalSceneMapper.deleteById(ojLocalScene.getResourceId());
					localSceneMapper.deleteById(localScene.getResourceId());
				}
			});
		}

		return true;
	}

	@Override
	public JSONObject getStartSceneMessage(String message, OjLocalScene ojLocalScene) {

		// 场景启动完成更新数据库
		JSONObject dataObj = JsonUtil.toJsonObject(message, JSONObject.class);
		JSONObject scene = dataObj.getJSONObject(JsonKeyConstant.DATA)
				.getJSONObject(JsonKeyConstant.DATA).getJSONObject(JsonKeyConstant.SCENE_DATA);

		if (null != scene
				&& scene.getIntValue(JsonKeyConstant.SCENE_EVENT) == SceneEvent.CREATE.getCode()
						.intValue()
				&& scene.getIntValue(JsonKeyConstant.SCENE_EVENT_STATUS) == SceneEventStatus.FINISH
						.getCode()) {

			// 组装推送消息
			JSONObject startMessage = new JSONObject();
			startMessage.put(JsonKeyConstant.CODE, OjEvent.SCENE_CREATE_FINISH.getCode());

			JSONObject countDown = new JSONObject();
			LocalScene localScene = localSceneMapper.selectById(ojLocalScene.getLocalSceneId());

			// 可能存在重复推送启动消息 保证仅更新一次
			if (null == localScene.getStartTime()) {
				// 更新数据
				localScene.setStartTime(new Date());
				localScene.setSceneStatus(SceneStatusEnum.START.getCode());

				// 场景存活时间为空则代表不设置场景结束时间 目前仅存在于课堂设置场景延时则无倒计时
				if (null != localScene.getSceneTtl()
						&& localScene.getSceneTtl() > CommonConstant.ZERO) {
					localScene.setEndTime(
							TimeUtil.addMins(localScene.getStartTime(), localScene.getSceneTtl()));
				}

				// 设置倒计时
				countDown.put(JsonKeyConstant.REMAIN_TIME,
						localScene.getSceneTtl() * CommonConstant.SIXTY);
				startMessage.put(JsonKeyConstant.DATA, countDown);

				localSceneMapper.updateById(localScene);
			} else {

				// 计算倒计时
				if (null != localScene.getEndTime()) {
					countDown.put(JsonKeyConstant.REMAIN_TIME,
							TimeUtil.getIntervalSeconds(new Date(), localScene.getEndTime()));
					startMessage.put(JsonKeyConstant.DATA, countDown);
				}
			}
			return startMessage;
		}

		return new JSONObject();
	}

	@Override
	public void checkSceneStartCount(String userId, Integer createCount) {

		String limitCount = systemConfigService.getSystemConfig("env_per_person");
		Integer startCount = ojLocalSceneMapper.selectByUser(userId);

		boolean flag = StringUtils.isNotBlank(limitCount)
				&& startCount.intValue() > Integer.parseInt(limitCount);

		if (null != createCount && createCount.intValue() != CommonConstant.ZERO.intValue()) {
			flag |= StringUtils.isNotBlank(limitCount)
					&& (startCount + createCount) > Integer.parseInt(limitCount);
		}

		if (flag) {
			log.error(
					"checkSceneStartCount: Scene start greater limit count! start_scene=[{}], limit_scene=[{}]",
					startCount, limitCount);
			throw new BusinessException(OjBusinessErrorCode.SCENE_RUNNING_TOO_MUCH, limitCount);
		}

	}

	@Override
	public JSONObject getSceneData(OjLocalScene ojLocalScene) {
		LocalScene localScene = localSceneMapper.selectById(ojLocalScene.getLocalSceneId());
		if (null == localScene) {
			return null;
		}
		JSONObject sceneData = remoteSceneService.getById(localScene.getMpSceneId(),
				localScene.getUserId());
		// 设置类型
		sceneData.put(JsonKeyConstant.REFERENCE_TYPE, localScene.getBusinessCode());
		sceneData.put(JsonKeyConstant.STATUS, localScene.getSceneStatus());

		// 设置场景共享类型
		Integer share  = null;
		if (localScene.getBusinessCode().intValue() == BusinessCode.CLASSROOM.getCode()) {
			share = SceneShare.GROUP.getCode();
		} else if (Boolean.TRUE.equals(ojLocalScene.getShared())) {
			share = SceneShare.SHARED.getCode();
		} else {
			share = SceneShare.PRIVATE.getCode();
		}
		sceneData.put(JsonKeyConstant.SCENE_SHARE_MODE, share);

		// 开始时间和结束时间
		if (null == localScene.getStartTime() || null == localScene.getEndTime()
				|| null == localScene.getSceneTtl()) {
			return sceneData;
		}

		long remain = localScene.getSceneStatus().intValue() == SceneStatusEnum.PAUSE
				.getCode().intValue() && null != localScene.getPauseTime()
						? TimeUtil.getIntervalSeconds(localScene.getPauseTime(),
								localScene.getEndTime())
						: TimeUtil.getIntervalSeconds(new Date(), localScene.getEndTime());
		sceneData.put(JsonKeyConstant.REMAIN_TIME, remain);

		return sceneData;
	}

	@Override
	public List<SceneMonitorBo> selectMonitorList(List<OjLocalScene> ojLocalScenes) {

		List<String> localSceneIds = StreamApiUtils.map(ojLocalScenes,
				OjLocalScene::getLocalSceneId);

		// 查询具体场景信息
		List<LocalScene> localScenes = localSceneMapper.selectBatchIds(localSceneIds);
		List<String> sceneIds = StreamApiUtils.map(localScenes, LocalScene::getMpSceneId);

		// 查询会话信息
		JSONArray nodeSessions = remoteSceneService.getNodeSessions(sceneIds);

		// 会话信息处理
		List<SceneMonitorBo> lessonMonitorBos = Lists.newArrayList();

		Map<String, List<Object>> userSessions = StreamApiUtils.groupBy(nodeSessions,
				nodeSession -> ((JSONObject) nodeSession).getString(JsonKeyConstant.USER_KEY));
		userSessions.forEach((user, sessions) -> {

			// 用户信息查询与设置
			UserInfo userInfo = remoteUserService.getById(user);
			SceneMonitorBo monitorBo = new SceneMonitorBo();
			monitorBo.setUserId(user);
			monitorBo.setUserName(userInfo.getUserName());

			// 会话信息设置
			List<MonitorInfoBo> infoBoList = StreamApiUtils.map(sessions, session -> {
				JSONObject sessionObj = (JSONObject) session;
				MonitorInfoBo monitorInfoBo = new MonitorInfoBo();
				monitorInfoBo.setMonitorSessions(sessionObj);
				monitorInfoBo.setServer(sessionObj.getString(JsonKeyConstant.NODE_NAME));
				monitorInfoBo.setUserName(userInfo.getUserName());
				return monitorInfoBo;
			});

			monitorBo.setMonitorInfo(infoBoList);
			lessonMonitorBos.add(monitorBo);
		});

		return lessonMonitorBos;
	}

	@Override
	public JSONObject switchScreenRecord(String topoId, Boolean enableRecording) {
		TopoSwitchRecordingContext context = new TopoSwitchRecordingContext(topoId, enableRecording);
		return remoteTopoService.sync(JsonUtil.java2JsonObject(context));
	}
}
