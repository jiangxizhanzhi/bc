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
import com.cyberpeace.integratation.service.RemoteTopoService;
import com.cyberpeace.integratation.service.SystemConfigService;
import com.cyberpeace.integratation.service.impl.MpEnvService;
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
	private RemoteTopoService remoteTopoService;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private LocalSceneMapper localSceneMapper;

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
			ojLocalScene.setFlags(String.format(FLAG_TEMPLATE, IDGeneratorUtil.getID()));
		} else {
			ojLocalScene.setFlags(JsonUtil.toJsonString(sceneContext.getFlags()));
		}

		return ojLocalScene;
	}

	@Override
	public JSONObject setDynamicFlag(String flags, String topoId) {

		JSONObject topo = remoteTopoService.getTopoById(topoId);
		if (null == topo || topo.isEmpty()) {
			return topo;
		}

		JSONObject sceneConfig = topo.getJSONObject("scene_config");
		if (sceneConfig.isEmpty()) {
			return topo;
		}

		JSONObject topology = sceneConfig.getJSONObject("topology");
		if (topology.isEmpty()) {
			return topo;
		}

		JSONArray elements = sceneConfig.getJSONArray("elements");
		if (elements.size() <= 0) {
			return topo;
		}

		for (Object element : elements) {
			JSONObject node = (JSONObject) element;
			List<String> initCommands = JsonUtil.toJsonObjectArray(node.getString("init_commands"),
					String.class);
			if (CollectionUtils.isEmpty(initCommands)) {
				return topo;
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
	public void deleteScene(String ojLocalSceneId) {
		OjLocalScene ojLocalScene = ojLocalSceneMapper.selectById(ojLocalSceneId);
		if (ojLocalScene == null) {
			return;
		}

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				ojLocalSceneMapper.deleteById(ojLocalScene.getResourceId());
				localSceneMapper.deleteById(ojLocalScene.getLocalSceneId());
			}
		});
	}

	@Override
	public JSONObject getStartSceneMessage(String message, OjLocalScene ojLocalScene) {

		// 场景启动完成更新数据库
		JSONObject dataObj = JsonUtil.toJsonObject(message, JSONObject.class);
		JSONObject scene = dataObj.getJSONObject("data").getJSONObject("data")
				.getJSONObject("scene");
		if (null != scene && !scene.isEmpty()
				&& scene.getIntValue("event") == SceneEvent.CREATE.getCode().intValue()
				&& scene.getIntValue("event_status") == SceneEventStatus.FINISH.getCode()) {

			// 更新localScene数据
			LocalScene localScene = localSceneMapper.selectById(ojLocalScene.getLocalSceneId());
			localScene.setSceneStatus(LocalScene.SceneStatusEnum.START.getCode());
			localScene.setDestroyTime(TimeUtil.addMins(new Date(), localScene.getSceneTtl()));
			localScene.setStartTime(new Date());
			localSceneMapper.updateById(localScene);

			// 推送倒计时
			JSONObject json = new JSONObject();
			json.put("code", OjEvent.SCENE_CREATE_FINISH.getCode());
			JSONObject countDown = new JSONObject();
			countDown.put("remaining_time", localScene.getSceneTtl() * CommonConstant.SIXTY);
			json.put("data", countDown);

			return json;

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
}
