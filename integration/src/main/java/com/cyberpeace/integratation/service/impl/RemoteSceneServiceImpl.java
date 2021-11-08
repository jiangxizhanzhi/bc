package com.cyberpeace.integratation.service.impl;

import org.springframework.stereotype.Service;

import com.cyberpeace.integratation.model.scene.TopoDigest;
import com.cyberpeace.integratation.service.RemoteSceneService;

@Service
public class RemoteSceneServiceImpl implements RemoteSceneService {

	@Override
	public TopoDigest getTopoDigest(String topoId) {
		// TODO
		return new TopoDigest();
	}
}
