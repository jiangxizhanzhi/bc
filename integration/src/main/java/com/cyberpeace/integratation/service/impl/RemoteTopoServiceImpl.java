package com.cyberpeace.integratation.service.impl;

import com.cyberpeace.common.util.IDGeneratorUtil;
import org.springframework.stereotype.Service;

import com.cyberpeace.integratation.model.SceneConfig;
import com.cyberpeace.integratation.service.RemoteTopoService;

@Service
public class RemoteTopoServiceImpl implements RemoteTopoService {

	@Override
	public String create(SceneConfig sceneConfig) {
		return IDGeneratorUtil.getID();
		// todo
	}

	@Override
	public SceneConfig getById(String sceneConfigId) {
		return null;
		// todo
	}

	@Override
	public String copyTopo(String originTopoId) {
		// TODO
		return null;
	}
}
