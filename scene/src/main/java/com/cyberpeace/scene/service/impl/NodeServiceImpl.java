package com.cyberpeace.scene.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.MathUtils;
import com.cyberpeace.scene.constant.enums.ImageTypeEnum;
import com.cyberpeace.scene.constant.enums.StatusEnum;
import com.cyberpeace.scene.dao.entity.NodeEntity;
import com.cyberpeace.scene.dao.mapper.NodeMapper;
import com.cyberpeace.scene.service.ContainerService;
import com.cyberpeace.scene.service.HostService;
import com.cyberpeace.scene.service.NodeService;
import com.cyberpeace.scene.service.bo.ContainerItem;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

/**
 * @author :   SN
 * @date :   2023/12/28 11:21
 * @desc :
 **/
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
                nodeMapper.deleteById(node.getId());
            }
        }
    }

    @Override
    public Integer getStage(String sceneId) {
        List<NodeEntity> nodes = nodeMapper.selectBySid(sceneId);
        int count = 0;
        if (!CollectionUtils.isEmpty(nodes)){
            for (NodeEntity node : nodes){
                String status = null;
                if (Objects.equals(ImageTypeEnum.ELEMENT_TYPE_CONTAINER.getCode(), node.getType())){
                    status = containerService.getStatus(node.getInstanceId());
                }
                if (Objects.equals(ImageTypeEnum.ELEMENT_TYPE_HOST.getCode(), node.getType())){
                    status = hostService.getStatus(node.getInstanceId());
                }
                if (StringUtils.equals(StatusEnum.ACTIVE.getName(), status)){
                    count++;
                }
                nodeMapper.updateStatus(node.getResourceId(), StatusEnum.getCode(status));
            }
            Double divide = MathUtils.divide((double) nodes.size(), (double) count, 2);
            return divide.intValue() * 100;
        }
        return 0;
    }
}
