package com.cyberpeace.scene.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.scene.dao.entity.TopologyEntity;
import com.cyberpeace.scene.dao.mapper.TopologyMapper;
import com.cyberpeace.scene.service.bo.Topology;
import com.cyberpeace.scene.service.TopologyService;
import org.springframework.stereotype.Service;

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
}
