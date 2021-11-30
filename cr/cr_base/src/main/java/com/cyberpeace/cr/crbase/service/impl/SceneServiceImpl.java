package com.cyberpeace.cr.crbase.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.cr.crbase.dao.entity.CrRoleNode;
import com.cyberpeace.cr.crbase.dao.entity.CrRoleNodesData;
import com.cyberpeace.cr.crbase.service.SceneService;
import com.cyberpeace.cs.dao.entity.LocalScene;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SceneServiceImpl implements SceneService {

	@Override
	public void filterVisibleTopo(JSONObject topoData, CrRoleNodesData nodes) {
		if (nodes == null) {
			return;
		}

		List<String> visibleNodeIds = nodes.getVisibleNodes().stream().map(CrRoleNode::getId)
				.collect(Collectors.toList());
		JSONArray elements = topoData.getJSONObject("topology").getJSONArray("elements");
		int i = 0;
		while (i < elements.size()) {
			if (!visibleNodeIds.contains(elements.getJSONObject(i).getString("id"))) {
				elements.remove(i);
			} else {
				i++;
			}
		}
	}

	@Override
	public LocalScene createLocalScene(Integer businessCode, String roomId, String topoId) {

		LocalScene localScene = new LocalScene();
		localScene.setBusinessCode(businessCode);
		localScene.setBusinessInstanceId(roomId);
		localScene.setTopoId(topoId);
		localScene.setResourceId(IDGeneratorUtil.getID());
		localScene.setSceneStatus(LocalScene.SceneStatusEnum.START.getCode());
		localScene.setDestoryMode(LocalScene.DestroyMode.MANUAL.getCode());
		localScene.setStartTime(new Date());

		return localScene;
	}
}
