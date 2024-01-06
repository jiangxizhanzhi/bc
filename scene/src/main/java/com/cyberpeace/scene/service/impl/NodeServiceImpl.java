package com.cyberpeace.scene.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.MathUtils;
import com.cyberpeace.scene.constant.enums.ImageTypeEnum;
import com.cyberpeace.scene.constant.enums.SceneEnum;
import com.cyberpeace.scene.constant.enums.StatusEnum;
import com.cyberpeace.scene.dao.entity.NodeEntity;
import com.cyberpeace.scene.dao.mapper.NodeMapper;
import com.cyberpeace.scene.service.ContainerService;
import com.cyberpeace.scene.service.HostService;
import com.cyberpeace.scene.service.NodeService;
import com.cyberpeace.scene.service.bo.ContainerItem;
import com.cyberpeace.scene.service.bo.res.CreateInstanceRes;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

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

    @Autowired
    private TransactionTemplate template;

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
            }
        }
    }

    @Override
    public boolean getStage(String sceneId) {
        List<NodeEntity> nodes = nodeMapper.selectBySid(sceneId);
        int count = 0;
        if (!CollectionUtils.isEmpty(nodes)){
            for (NodeEntity node : nodes){
                Integer nodeStatus = SceneEnum.CREATE.getCode();
                String ip = null;
                if (Objects.equals(ImageTypeEnum.ELEMENT_TYPE_CONTAINER.getCode(), node.getType())){
                    ContainerItem instance = containerService.getInstance(node.getInstanceId());
                    if (Objects.nonNull(instance)){
                        if (StringUtils.equals(StatusEnum.ACTIVE.getName(), instance.getStatus())){
                            nodeStatus = SceneEnum.ACTIVE.getCode();
                            JSONObject addresses = instance.getAddresses();
                            ip = getIp(addresses);
                            count++;
                        }else if (StringUtils.equals(StatusEnum.ERROR.getName(), instance.getStatus())){
                            nodeStatus = SceneEnum.ERROR.getCode();
                            count++;
                        }
                    }
                }
                if (Objects.equals(ImageTypeEnum.ELEMENT_TYPE_HOST.getCode(), node.getType())){
                    CreateInstanceRes instance = hostService.getInstance(node.getInstanceId());
                    if (Objects.nonNull(instance)){
                        if (StringUtils.equals(StatusEnum.ACTIVE.getName(), instance.getVmState())){
                            nodeStatus = SceneEnum.ACTIVE.getCode();
                            JSONObject addresses = instance.getAddresses();
                            ip = getIp(addresses);
                            count++;
                        }else if (StringUtils.equals(StatusEnum.ERROR.getName(), instance.getVmState())){
                            nodeStatus = SceneEnum.ERROR.getCode();
                            count++;
                        }
                    }
                }
                node.setIp(ip);
                node.setStatus(nodeStatus);
                updateById(node);
            }
        }
        return Objects.equals(nodes.size(), count);
    }

    private String getIp(JSONObject addresses){
        String ip = null;
        if (Objects.isNull(addresses)){
            return ip;
        }
        Set<Map.Entry<String, Object>> entries = addresses.entrySet();
        for (Map.Entry<String, Object> entry : entries){
            JSONObject address = JSONObject.parseObject(entry.getValue().toString());
            if (address.containsKey("ip_address")){
                ip = address.getString("ip_address");
            }
        }
        return ip;
    }
}
