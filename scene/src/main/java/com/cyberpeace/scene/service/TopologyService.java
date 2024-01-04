package com.cyberpeace.scene.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.scene.dao.entity.TopologyEntity;
import com.cyberpeace.scene.service.bo.Topology;
import com.cyberpeace.scene.service.bo.TopologyElement;

import java.util.List;
import java.util.Map;

/**
 * @author :   辛发圣
 * @date :   2023/12/20 16:22
 * @desc :
 **/
public interface TopologyService extends IService<TopologyEntity> {

    /**
     * 根据拓扑id查询拓扑
     *
     * @param topologyId 拓扑id
     * @return 拓扑信息
     */
    TopologyEntity getTopology(String topologyId);

    /**
     * 格式化拓扑信息
     * @param topologyId    拓扑id
     * @return  格式化信息
     */
    Topology formatTopology(String topologyId);

    /**
     * 获取拓扑元素信息
     * @param topology  拓扑信息
     * @return  拓扑元素信息
     */
    Map<String, List<TopologyElement>> getTopologyElementMap(Topology topology);
}
