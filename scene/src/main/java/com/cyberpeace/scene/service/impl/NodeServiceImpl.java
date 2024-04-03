package com.cyberpeace.scene.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.scene.constant.enums.ImageRoleEnum;
import com.cyberpeace.scene.dao.entity.NodeEntity;
import com.cyberpeace.scene.dao.mapper.NodeMapper;
import com.cyberpeace.scene.service.HostService;
import com.cyberpeace.scene.service.NodeService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author :   SN
 * @date :   2023/12/28 11:21
 * @desc :
 **/
@Slf4j
@Service
public class NodeServiceImpl extends ServiceImpl<NodeMapper, NodeEntity> implements NodeService {

    @Autowired
    private NodeMapper nodeMapper;

    @Autowired
    private HostService hostService;

	@Override
	public void delete(String sceneId) {
		List<NodeEntity> nodes = nodeMapper.selectBySid(sceneId);
		if (nodes.isEmpty()) {
			return;
		}
		for (NodeEntity node : nodes) {
			// 勒索病毒场景不重复创建
			if (node.getType().equals(ImageRoleEnum.SERVER.getCode())) {
				continue;
			}
			if (StringUtils.isNotBlank(node.getInstanceId())) {
				// 删除虚拟机
				hostService.delete(node.getInstanceId());
			}
			nodeMapper.deleteById(node.getResourceId());
			log.info("delete node : {}", node.getInstanceId());
		}
	}

	@Override
	public void clear() {
		List<NodeEntity> nodes = nodeMapper.selectAll();
		for (NodeEntity node : nodes) {
			if (StringUtils.isNotBlank(node.getInstanceId())) {
				// 删除虚拟机
				hostService.delete(node.getInstanceId());
			}
			nodeMapper.deleteById(node.getResourceId());
		}
	}
}
