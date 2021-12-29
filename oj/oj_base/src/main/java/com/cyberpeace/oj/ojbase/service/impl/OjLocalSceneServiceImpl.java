package com.cyberpeace.oj.ojbase.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.PatternConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.exception.InvalidParamException;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cs.constant.SceneEvent;
import com.cyberpeace.cs.constant.SceneEventStatus;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.dao.mapper.LocalSceneMapper;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.integratation.service.RemoteSceneService;
import com.cyberpeace.integratation.service.RemoteTopoService;
import com.cyberpeace.integratation.service.SystemConfigService;
import com.cyberpeace.integratation.service.impl.MpEnvService;
import com.cyberpeace.oj.ojbase.constant.JsonKeyConstant;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import com.cyberpeace.oj.ojbase.constant.OjEvent;
import com.cyberpeace.oj.ojbase.constant.SceneShare;
import com.cyberpeace.oj.ojbase.service.context.InitOjSceneContext;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.oj.ojbase.dao.entity.OjLocalScene;
import com.cyberpeace.oj.ojbase.dao.mapper.OjLocalSceneMapper;
import com.cyberpeace.oj.ojbase.service.OjLocalSceneService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class OjLocalSceneServiceImpl extends ServiceImpl<OjLocalSceneMapper, OjLocalScene> implements OjLocalSceneService {

	private String FLAG_TEMPLATE;

	@PostConstruct
	public void init() {
		String templateData = ConfigUtil.getContextProperty("oj.flag.template.data");
		String flagPrefix = systemConfigService.getSystemConfig("flag_prefix");
		FLAG_TEMPLATE = String.format(templateData, flagPrefix, "%s");
	}

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
	private LocalSceneService localSceneService;

	@Override
	public OjLocalScene selectOjLocalScene(String businessId, Integer shared,
			AuthContext authContext) {

		if (shared.intValue() == SceneShare.SHARED.getCode().intValue()) {
			return ojLocalSceneMapper.selectPublicScene(businessId, CommonConstant.TRUE,
					authContext.getIsWeb());
		} else {
			return ojLocalSceneMapper.selectPrivateScene(authContext.getUserId(), businessId,
					authContext.getIsWeb());
		}

	}

	@Override
	public OjLocalScene initOjLocalScene(InitOjSceneContext sceneContext) {

		OjLocalScene ojLocalScene = BeanUtils.copy(sceneContext, OjLocalScene.class);
		ojLocalScene.setResourceId(IDGeneratorUtil.getID());

		// 共享设置
		ojLocalScene.setShared(
				sceneContext.getSharedType().intValue() == SceneShare.SHARED.getCode().intValue());

		// flag设置
		if (sceneContext.getDynamicFlag()) {
			String flag = String.format(FLAG_TEMPLATE, IDGeneratorUtil.getID());
			ojLocalScene.setFlags(JsonUtil.arrayToString(Arrays.asList(flag)));
		} else {
			ojLocalScene.setFlags(JsonUtil.toJsonString(sceneContext.getFlags()));
		}

		return ojLocalScene;
	}

	@Override
	public JSONObject setDynamicFlag(String flags, JSONObject topo) {

		if (null == topo || topo.isEmpty()) {
			return topo;
		}

		JSONObject sceneConfig = topo.getJSONObject(JsonKeyConstant.SCENE_CONFIG);
		if (sceneConfig.isEmpty()) {
			return topo;
		}

		JSONObject topology = sceneConfig.getJSONObject(JsonKeyConstant.TOPOLOGY);
		if (topology.isEmpty()) {
			return topo;
		}

		JSONArray elements = topology.getJSONArray(JsonKeyConstant.ELEMENTS);
		if (elements.size() <= 0) {
			return topo;
		}

		for (Object element : elements) {
			JSONObject node = (JSONObject) element;
			List<String> initCommands = JsonUtil
					.toJsonObjectArray(node.getString(JsonKeyConstant.INIT_COMMANDS), String.class);
			if (CollectionUtils.isEmpty(initCommands)) {
				continue;
			}

			for (int i = 0; i < initCommands.size(); i++) {
				List<String> matchStr = LocalStringUtil.getMatchStr(initCommands.get(i),
						PatternConstant.DYNAMIC_FLAG);
				String replaceCommand = LocalStringUtil.replaceAll(initCommands.get(i), matchStr,
						JsonUtil.toJsonObjectArray(flags, String.class));
				initCommands.set(i, replaceCommand);
			}
		}

		return topo;
	}

	@Override
	public Boolean deleteLocalScene(String ojLocalSceneId) {
		OjLocalScene ojLocalScene = ojLocalSceneMapper.selectById(ojLocalSceneId);
		if (ojLocalScene == null) {
			return true;
		}

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				ojLocalSceneMapper.deleteById(ojLocalScene.getResourceId());
				localSceneMapper.deleteById(ojLocalScene.getLocalSceneId());
			}
		});

		return true;
	}

	@Override
	public Boolean deleteLocalAndRemoteScene(OjLocalScene ojLocalScene) {

		if (null == ojLocalScene) {
			return true;
		}
		// 删除中台场景
		LocalScene localScene = localSceneMapper.selectById(ojLocalScene.getLocalSceneId());
		localSceneService.deleteRemoteScene(localScene);

		// 删除本地场景
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				localSceneMapper.deleteById(localScene.getResourceId());
				ojLocalSceneMapper.deleteById(ojLocalScene.getResourceId());
			}
		});

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
				localScene.setSceneStatus(LocalScene.SceneStatusEnum.START.getCode());

				// 更新localScene
				if (localScene.getBusinessCode().intValue() != LocalScene.BusinessCode.CLASSROOM
						.getCode().intValue()) {
					localScene.setEndTime(
							TimeUtil.addMins(localScene.getStartTime(), localScene.getSceneTtl()));

					// 设置倒计时
					countDown.put(JsonKeyConstant.REMAIN_TIME,
							localScene.getSceneTtl() * CommonConstant.SIXTY);
					startMessage.put(JsonKeyConstant.DATA, countDown);
				}

				localSceneMapper.updateById(localScene);
			} else {

				// 计算倒计时
				if (localScene.getBusinessCode().intValue() != LocalScene.BusinessCode.CLASSROOM
						.getCode().intValue()) {
					countDown.put(JsonKeyConstant.REMAIN_TIME,
							TimeUtil.getIntervalSeconds(new Date(), localScene.getEndTime()));
					startMessage.put(JsonKeyConstant.DATA, countDown);
				}
			}
			return startMessage;
		}

		return null;
	}

	@Override
	public void checkSceneStartCount(String userId, Integer createCount) {

		String limitCount = systemConfigService.getSystemConfig("env_per_person");
		Integer startCount = ojLocalSceneMapper.selectByUser(userId);

		boolean flag = StringUtils.isNotBlank(limitCount)
				&& startCount.intValue() > Integer.parseInt(limitCount);

		if (null != createCount || createCount.intValue() != CommonConstant.ZERO.intValue()) {
			flag |= StringUtils.isNotBlank(limitCount)
					&& (startCount + createCount) > Integer.parseInt(limitCount);
		}

		if (flag) {
			log.error(
					"checkSceneStartCount: Scene start greater limit count! start_scene=[{}], limit_scene=[{}]",
					startCount, limitCount);
			throw new InvalidParamException("场景数量启动启动过多！");
		}

	}

	@Override
	public JSONObject getSceneData(OjLocalScene ojLocalScene) {
		LocalScene localScene = localSceneMapper.selectById(ojLocalScene.getLocalSceneId());
		JSONObject sceneData = remoteSceneService.getById(localScene.getMpSceneId(),
				localScene.getUserId());
		// 设置类型
		sceneData.put(JsonKeyConstant.REFERENCE_TYPE, localScene.getBusinessCode());
		sceneData.put(JsonKeyConstant.SCENE_STATUS, localScene.getSceneStatus());

		// 设置场景共享类型与倒计时
		if (localScene.getBusinessCode().intValue() == LocalScene.BusinessCode.CLASSROOM
				.getCode()) {

			// 课堂不需要倒计时
			sceneData.put(JsonKeyConstant.SCENE_SHARE_MODE, SceneShare.GROUP.getCode());
		} else {
			sceneData.put(JsonKeyConstant.SCENE_SHARE_MODE,
					ojLocalScene.getShared() ? SceneShare.SHARED.getCode()
							: SceneShare.PRIVATE.getCode());

			if (null == localScene.getStartTime() || null == localScene.getEndTime()) {
				return sceneData;
			}

			long remain = localScene.getSceneStatus().intValue() == LocalScene.SceneStatusEnum.PAUSE
					.getCode().intValue() && null != localScene.getPauseTime()
							? TimeUtil.getIntervalSeconds(localScene.getPauseTime(),
									localScene.getEndTime())
							: TimeUtil.getIntervalSeconds(new Date(), localScene.getEndTime());
			sceneData.put(JsonKeyConstant.REMAIN_TIME, remain);
		}

		return sceneData;
	}
}
