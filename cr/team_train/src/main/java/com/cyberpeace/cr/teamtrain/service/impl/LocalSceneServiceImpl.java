package com.cyberpeace.cr.teamtrain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.cr.teamtrain.dao.entity.LocalScene;
import com.cyberpeace.cr.teamtrain.dao.mapper.LocalSceneMapper;
import com.cyberpeace.cr.teamtrain.service.LocalSceneService;

/**
 *
 */
@Service
public class LocalSceneServiceImpl extends ServiceImpl<LocalSceneMapper, LocalScene> implements LocalSceneService {

	@Autowired
	LocalSceneMapper localSceneMapper;

	@Override
	public LocalScene getLocalSceneByBusinessInstanceId(String resourceId) {
		return localSceneMapper.getLocalSceneByBusinessInstanceId(resourceId);
	}
}
