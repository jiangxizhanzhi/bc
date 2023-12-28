package com.cyberpeace.scene.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.EnumUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.scene.constant.enums.ImageTypeEnum;
import com.cyberpeace.scene.dao.entity.TopologyEntity;
import com.cyberpeace.scene.dao.mapper.TopologyMapper;
import com.cyberpeace.scene.service.bo.Topology;
import com.cyberpeace.scene.service.TopologyService;
import com.cyberpeace.scene.service.bo.TopologyElement;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author :   SN
 * @date :   2023/12/20 16:24
 * @desc :
 **/
@Service
public class TopologyServiceImpl extends ServiceImpl<TopologyMapper, TopologyEntity> implements TopologyService {

    @Override
    public TopologyEntity getTopology(String topologyId) {
        LambdaQueryWrapper<TopologyEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TopologyEntity::getTopologyId, topologyId);
        return getOne(wrapper);
    }

    @Override
    public Topology formatTopology(String topologyId) {
        TopologyEntity entity = getTopology(topologyId);
        if (entity != null){
            String topologyInfo = entity.getTopology();
            Topology topology = JsonUtil.toJsonObject(topologyInfo, Topology.class);
            return topology;
        }
        return null;
    }

    @Override
    public Map<String, TopologyElement> getTopologyElementMap(Topology topology) {
        Map<String, TopologyElement> map = new HashMap<>();
        if (Objects.nonNull(topology)){
            for (TopologyElement element : topology.getElements()){
                ImageTypeEnum imageType = EnumUtils.getEnumByCode(element.getType(), ImageTypeEnum.class);
                if (Objects.nonNull(imageType)){
                    map.put(imageType.getName(), element);
                }
            }
            return map;
        }
        return null;
    }
}
