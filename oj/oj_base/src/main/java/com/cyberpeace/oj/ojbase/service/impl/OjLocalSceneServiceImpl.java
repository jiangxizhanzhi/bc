package com.cyberpeace.oj.ojbase.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.util.*;
import com.cyberpeace.integratation.service.RemoteTopoService;
import com.cyberpeace.integratation.service.SystemConfigService;
import com.cyberpeace.integratation.service.impl.MpEnvService;
import com.cyberpeace.oj.ojbase.constant.SceneShare;
import com.cyberpeace.oj.ojbase.service.context.InitOjSceneContext;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.oj.ojbase.dao.entity.OjLocalScene;
import com.cyberpeace.oj.ojbase.dao.mapper.OjLocalSceneMapper;
import com.cyberpeace.oj.ojbase.service.OjLocalSceneService;

import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
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

	@Override
	public OjLocalScene selectOjLocalScene(String businessId, Integer shared,
			AuthContext authContext) {

		if (shared.intValue() == SceneShare.SHARED.getCode().intValue()) {

			OjLocalScene ojLocalScene = ojLocalSceneMapper.selectPrivateScene(
					authContext.getUserId(), businessId, authContext.getIsWeb());
			if (null == ojLocalScene) {
				return ojLocalSceneMapper.selectPublicScene(businessId, CommonConstant.TRUE,
						authContext.getIsWeb());
			}
			return ojLocalScene;
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

		String pattern = "\\[FLAG\\[[0-9]+\\]\\]";

		for (Object element : elements) {
			JSONObject node = (JSONObject) element;
			List<String> initCommands = JsonUtil.toJsonObjectArray(node.getString("init_commands"),
					String.class);
			if (CollectionUtils.isEmpty(initCommands)) {
				return topo;
			}

			for (int i = 0; i < initCommands.size(); i++) {
				List<String> matchStr = LocalStringUtil.getMatchStr(initCommands.get(i), pattern);
				String replaceCommand = LocalStringUtil.replaceAll(initCommands.get(i), matchStr,
						JsonUtil.toJsonObjectArray(flags, String.class));
				initCommands.set(i, replaceCommand);
			}
		}

		return topo;
	}
}
