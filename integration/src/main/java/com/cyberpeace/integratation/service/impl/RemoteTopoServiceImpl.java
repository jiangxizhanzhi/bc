package com.cyberpeace.integratation.service.impl;

import org.springframework.stereotype.Service;

import com.cyberpeace.integratation.model.SceneConfig;
import com.cyberpeace.integratation.service.RemoteTopoService;

@Service
public class RemoteTopoServiceImpl implements RemoteTopoService {

	@Override
	public String create(SceneConfig sceneConfig) {
		return "todo";
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
