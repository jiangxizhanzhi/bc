package com.cyberpeace.oj.ojbase.service.impl;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.oj.ojbase.constant.SceneShare;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.oj.ojbase.dao.entity.OjLocalScene;
import com.cyberpeace.oj.ojbase.dao.mapper.OjLocalSceneMapper;
import com.cyberpeace.oj.ojbase.service.OjLocalSceneService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OjLocalSceneServiceImpl extends ServiceImpl<OjLocalSceneMapper, OjLocalScene> implements OjLocalSceneService {

	@Autowired
	private OjLocalSceneMapper ojLocalSceneMapper;

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
}
