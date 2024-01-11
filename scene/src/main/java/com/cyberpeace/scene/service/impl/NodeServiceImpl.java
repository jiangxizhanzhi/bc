package com.cyberpeace.scene.service.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.scene.constant.enums.ImageTypeEnum;
import com.cyberpeace.scene.constant.enums.SceneEnum;
import com.cyberpeace.scene.dao.entity.NodeEntity;
import com.cyberpeace.scene.dao.mapper.NodeMapper;
import com.cyberpeace.scene.service.ContainerService;
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
    private ContainerService containerService;

    @Autowired
    private HostService hostService;

    @Override
    public void delete(String sceneId) {
        List<NodeEntity> nodes = nodeMapper.selectBySid(sceneId);
        if (!CollectionUtils.isEmpty(nodes)){
            for (NodeEntity node : nodes){
                //删除容器
                if (Objects.equals(ImageTypeEnum.ELEMENT_TYPE_CONTAINER.getCode(), node.getType())){
                    containerService.delete(node.getInstanceId());
                }
                //删除虚拟机
                if (Objects.equals(ImageTypeEnum.ELEMENT_TYPE_HOST.getCode(), node.getType())){
                    hostService.delete(node.getInstanceId());
                }
                nodeMapper.updateStatus(node.getResourceId(), SceneEnum.DELETED.getCode());
                log.info("delete node : {}" , node.getInstanceId());
            }
        }
    }
}
