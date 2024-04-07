package com.cyberpeace.scene.service.impl;

import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.scene.constant.enums.InteractiveScenarioEnum;
import com.cyberpeace.scene.dao.entity.NetworkEntity;
import com.cyberpeace.scene.dao.entity.SceneEntity;
import com.cyberpeace.scene.dao.mapper.NetworkMapper;
import com.cyberpeace.scene.dao.mapper.SceneMapper;
import com.cyberpeace.scene.service.NetworkService;
import com.cyberpeace.scene.service.NodeService;
import com.cyberpeace.scene.service.SceneService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author :   SN
 * @date :   2023/12/20 15:21
 * @desc :
 **/
@Slf4j
@Service
public class SceneServiceImpl extends ServiceImpl<SceneMapper, SceneEntity> implements SceneService {

	@Autowired
	private SceneMapper sceneMapper;

	@Autowired
	private NetworkService networkService;

	@Autowired
	private NetworkMapper networkMapper;

	@Autowired
	private NodeService nodeService;

	@Override
	public void destroyScene(String sceneId) {
		SceneEntity scene = sceneMapper.selectByResourceId(sceneId);
		if (Objects.isNull(scene)) {
			return;
		}
		try {
			// 删除节点
			nodeService.delete(sceneId);
			// 勒索病毒场景不重复创建
			if (scene.getScenario().equals(InteractiveScenarioEnum.RANSOMWARE.getCode())) {
				return;
			}
			// 删除网络
			List<NetworkEntity> networks = networkMapper.selectBySceneId(sceneId);
			if (!CollectionUtils.isEmpty(networks)) {
				networks.forEach(iter -> {
					if (StringUtils.isNotBlank(iter.getUuid())) {
						networkService.delete(iter.getUuid());
					}
					networkMapper.deleteByResourceId(iter.getResourceId());
				});
			}
			sceneMapper.deleteByResourceId(sceneId);
		} catch (Exception e) {
			log.error("delete scene:[{}], error:{}", sceneId, e);
			throw new BusinessException(ErrorCode.SCENE_DESTROY_FAILED);
		}
	}


}
